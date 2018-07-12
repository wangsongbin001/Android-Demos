package com.wang.csdnapp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.song.spider.bean.NewsItem;
import com.wang.csdnapp.R;
import com.wang.csdnapp.bean.SharedParam;
import com.wang.csdnapp.util.ToastUtil;
import com.wang.csdnapp.view.ShareDialog;

/**
 * Created by songbinwang on 2017/6/15.
 */

public class WebDetailShowActivity extends AppCompatActivity{

    private RelativeLayout rl;
    private WebView webView;
    private ImageView iv_back, iv_share;
    private String articleUrl;
    private ShareDialog shareDialog;
    private Context mContext;
    private NewsItem newsItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webdetailshow);
        mContext = this;
        articleUrl = "http://www.csdn.net/article/2016-10-26/2826665";

        fetchIntent();
        initViews();
        registerListeners();
    }

    /**
     * 获取上层界面的传递的参数
     */
    private void fetchIntent(){
        Intent intent = getIntent();
        newsItem = (NewsItem) intent.getSerializableExtra("item");
        articleUrl = newsItem.getLink();
    }

    /**
     * 初始化控件
     */
    private void initViews(){
        rl = (RelativeLayout) findViewById(R.id.rl);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_share = (ImageView) findViewById(R.id.iv_share);

        //webview的初始化
        webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //支持插件webSettings.setPluginsEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        //加载一个网页
        webView.loadUrl(articleUrl);
        //方式2：加载apk包中的html页面
        //webView.loadUrl("file:///android_asset/test.html");
        //方式3：加载手机本地的html页面
        //webView.loadUrl("content://com.android.htmlfileprovider/sdcard/test.html");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(webView != null){
            webView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(webView != null){
            webView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rl.removeView(webView);
        webView.destroy();

    }

    private void registerListeners(){
        iv_back.setOnClickListener(onClicker);
        iv_share.setOnClickListener(onClicker);
    }

    View.OnClickListener onClicker = new  View.OnClickListener(){
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.iv_share:
//                    ToastUtil.toast("分享");
                    if(shareDialog == null){
                        if(newsItem == null){
                            newsItem = new NewsItem();
                            newsItem.setTitle("分享标题");
                            newsItem.setContent("国内最大的生活地图,集附近搜索、路线规划,语音导航为一体," +
                                    "支持离线地图下载,跨平台多终端覆盖。订酒店、查餐厅,叫出租车只要不到3分钟!" +
                                    "不误事,少绕路,快速又方便!");
                            newsItem.setLink(articleUrl);
                        }
                        shareDialog = new ShareDialog(mContext, createParam(newsItem));
                    }
                    shareDialog.show();
                    break;
            }
        }
    };

    /**
     * 构建分享所需要的参数
     * @param item
     * @return
     */
    private SharedParam createParam(NewsItem item){
        if(item == null){
            return null;
        }
        SharedParam param = new SharedParam(SharedParam.TYPE.type_weibo,
                item.getLink(), item.getTitle(),item.getImgUrl(),item.getContent());
        return param;
    }

}
