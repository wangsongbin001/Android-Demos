package com.wang.csdnapp.dao;

import android.content.Context;
import android.os.Environment;

import com.song.spider.bean.News;
import com.song.spider.bean.NewsItem;
import com.song.spider.csdn.URLUtil;
import com.wang.csdnapp.util.LogUtil;

import org.apache.http.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by songbinwang on 2016/10/31.
 */

public class NewsItemDao {

    private static final String tag = "NewsItemDao";
    private int cacheMinute = 2;
    private String diskCache;

    public NewsItemDao(Context context) {
        //生成缓存目录
        File fileCache = getDiskCacheDir(context, "file");
        if(!fileCache.exists()){
            fileCache.mkdirs();
        }
        diskCache = fileCache.getAbsolutePath();
    }

    public List<NewsItem> getNewsItems(int newsType, int currentPage) {

        //获取唯一的url
        String url = URLUtil.generateUrl(newsType, currentPage);
        //根据url生成唯一的key值
        String key = hashKeyFromUrl(url);
        File file = new File(diskCache, key);
        //有磁盘缓存，并且没有过缓存时间，则读取磁盘文件，否侧删除
        if(file.exists() && (file.lastModified() + cacheMinute * 60000 - System.currentTimeMillis() > 0)){
            LogUtil.i(tag, "load from cache");
            return getNewsItems(newsType, file);
        }else if(file.exists()){
            LogUtil.i(tag, "delete");
            file.delete();
        }
        file = new File(diskCache, key);
        //将网络的Html文件写入本地缓存的文件
        if(writeHtmlToFile(url, file)){
            return getNewsItems(newsType, file);
        }
        return null;
    }

    /**
     * 解析文章内容
     * @param url
     * @return
     */
    public List<News> getNews(String url){
        LogUtil.i(tag, "aircle url:" + url);
        //根据url,生成key
        String key = hashKeyFromUrl(url);
        //生成对应的file
        File file = new File(diskCache, key);
        //检查是否过时
        if(file.exists() && (file.lastModified() + cacheMinute * 60000 - System.currentTimeMillis() > 0)){
            LogUtil.i(tag, "load from cache");
            //从本地获取
            return getNewsFromLocal(file);
        }else if(file.exists()){
            LogUtil.i(tag, "cache is timeout");
            file.delete();
        }
        file = new File(diskCache, key);
        //将网络的Html文件写入本地缓存的文件
        if(writeHtmlToFile(url, file)){
            return getNewsFromLocal(file);
        }
        return null;
    }

    /**
     * 将网络内容缓存到本地
     * @param url
     * @param localFile
     * @return
     */
    private boolean writeHtmlToFile(String url, File localFile){
        LogUtil.i(tag, "writeHtmlToFile");
        HttpURLConnection conn = null;
        InputStream in = null;
        FileOutputStream fos = null;
        try {
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("POST");
            //超时极限30秒
            conn.setConnectTimeout(30 * 1000);
            conn.setReadTimeout(30 * 1000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //不使用网络存
            conn.setUseCaches(false);
            //设置是否自动执行重定向
            conn.setInstanceFollowRedirects(true);

            conn.connect();
            //连接成功！
            LogUtil.i(tag, "errorCode:" + conn.getResponseCode());
            if(HttpStatus.SC_OK == conn.getResponseCode()){
                in = conn.getInputStream();
                fos = new FileOutputStream(localFile);
                byte[] temp = new byte[1024];
                int len;
                while((len = in.read(temp)) != -1){
                    fos.write(temp, 0, len);
                }
            }else{
                return false;
            }
        } catch (Exception e) {
            LogUtil.i(tag, "e0:" + e.getMessage());
            e.printStackTrace();
            return false;
        }finally {
            try{
                if(fos != null){
                    fos.flush();
                    fos.close();
                }
            }catch (Exception e){
                LogUtil.i(tag, "e1:" + e.getMessage());
            }
            try{
                if(conn != null){
                    conn.disconnect();
                }
            }catch (Exception e){
                LogUtil.i(tag, "e2:" + e.getMessage());
            }
            try{
                if(in != null){
                    in.close();
                }
            }catch (Exception e){
                LogUtil.i(tag, "e3:" + e.getMessage());
            }
        }
        return true;
    }

    /**
     * 解析本地文件中Html
     * @param newsType
     * @param file
     * @return
     */
    private List<NewsItem> getNewsItems(int newsType, File file){
        ArrayList newsItems = null;
        try {
            Document doc = Jsoup.parse(file, "UTF-8", "");
            Element content = doc.select(".content").first();
            Elements es = content.select(".unit");
            newsItems = new ArrayList();
            Iterator var9 = es.iterator();

            while(var9.hasNext()) {
                Element element = (Element)var9.next();
                NewsItem item = new NewsItem();
                item.setNewsType(newsType);
                Element e1 = element.select("a[target=_blank]").first();
                item.setTitle(e1.text());
                item.setLink(e1.attr("href"));
                Element e2 = element.select("img[src]").first();
                if(e2 != null) {
                    item.setImgUrl(e2.attr("src"));
                }

                Element e3 = element.select("span.ago").first();
                item.setDate(e3.text());
                Element e4 = element.select("dd").first();
                item.setContent(e4.text());
                newsItems.add(item);
            }
        } catch (Exception e) {
            LogUtil.i(tag, "e:" + e.getMessage());
        }
        return newsItems;
    }

    /**
     * 从本地文件中，解析文章内容
     * @param file
     * @return
     */
    private List<News> getNewsFromLocal(File file){
        ArrayList<News> newses = null;
        try {
            newses = new ArrayList();
            Document e = Jsoup.parse(file, "UTF-8", "");
            Element detail = e.select(".detail").first();
            Element titleE = detail.select("h1.title").first();
            News news = new News();
            news.setTitle(titleE.text());
            news.setType(1);
            newses.add(news);
            Element summaryE = detail.select("div.summary").first();
            news = new News();
            news.setSummary(summaryE.text());
            news.setType(2);
            newses.add(news);
            Element contentE = detail.select("div.con.news_content").first();
            Elements es = contentE.select("p");
            Iterator var11 = es.iterator();

            while(var11.hasNext()) {
                Element child = (Element)var11.next();
                if(child.select("img[src]").first() != null) {
                    news = new News();
                    news.setImgLink(child.select("img[src]").first().attr("src"));
                    news.setType(4);
                    newses.add(news);
                }

                if(!"".equals(child.text())) {
                    news = new News();
                    news.setType(3);

                    try {
                        if(child.children().size() == 1 && "b".equals(child.children().first().tagName())) {
                            news.setType(5);
                        }
                    } catch (Exception var13) {
                        ;
                    }

                    news.setContent(child.text());
                    newses.add(news);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newses;
    }

    /**
     * 通过MD5让url生成对应唯一key
     *
     * @param url
     * @return
     */
    private String hashKeyFromUrl(String url) {
        String key = "";
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            key = byteToString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            key = String.valueOf(url.hashCode());
        }
        return key;
    }

    private String byteToString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(0xff & b[i]);
            if(hex.length() == 1){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 生成缓存目录
     * @param context
     * @param uniqueName
     * @return
     */
    private File getDiskCacheDir(Context context, String uniqueName){
        boolean isAvailable =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && !Environment.isExternalStorageRemovable();
        String cachePath = "";
        if(isAvailable){
            cachePath = context.getExternalCacheDir().getPath();
        }else{
            cachePath = context.getCacheDir().getPath();
        }
        cachePath = cachePath + File.separator + uniqueName;
        return new File(cachePath);
    }
}
