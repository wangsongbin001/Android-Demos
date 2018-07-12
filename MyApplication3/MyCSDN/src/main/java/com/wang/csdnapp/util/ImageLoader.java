package com.wang.csdnapp.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.wang.csdnapp.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import libcore.io.DiskLruCache;

/**
 * Created by SongbinWang on 2017/3/3.
 */

public class ImageLoader {
    private static final String tag = "ImageLoader";
    //50MB， 默认磁盘缓存的大小
    private final static long DISK_CACHE_SIZE = 1024 * 1024 * 50;
    //设置了一个数据节点，只有一个数据
    private static int DISK_CACHE_INDEX = 0;
    //内存缓存
    private LruCache<String, Bitmap> mMemoryCache;
    //磁盘缓存
    private DiskLruCache mDiskLruCache;

    private Context mContext;
    //磁盘缓存是否创建
    private boolean mIsDiskLruCacheCreated = false;

    private ImageResizer mImageResizer = new ImageResizer();

    private static ImageLoader mImageLoader;

    private static final int TAG_URL_KEY = R.integer.iamgeloader_tag_key;

    private static final int MESSAGE_POST_RESULT = 1;

    private static final int TO_BUFFER_SIZE = 8 * 1024;

    //获取cup核数
    private static final int CUP_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CODE_POOL_SIZE = CUP_COUNT + 1;
    private static final int MAXINUM_POOL_SIZE = CUP_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10l;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ImageLoader - " + mCount.getAndIncrement());
        }
    };
    /**
     * 自定义线程池
     */
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CODE_POOL_SIZE, MAXINUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            sThreadFactory
    );

    /**
     * UI线程的Handler
     */
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.imageView;
            String url = (String) imageView.getTag(TAG_URL_KEY);
            if (result.url.equals(url)) {
                imageView.setImageBitmap(result.bitmap);
            } else {
                Log.w(tag, "url is different from tag ");
            }
        }
    };

    /**
     * ImageLoader的构造方法
     *
     * @param context
     */
    ImageLoader(Context context) {
        mContext = context;
        //当前应用的可用最大内存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        //初始化
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //计算Bitmap的大小，获取字节数，转换成kb
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
        //初始化mDiskLruCache
        //1,定义磁盘缓存路径
        File diskCacheDir = getDiskCacheDir(mContext, "bitmap");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        //查看空间是否充足
        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ImageLoader() {

    }

    /**
     * 单例模式
     *
     * @param context
     * @return
     */
    public static ImageLoader getInstance(Context context) {
        if (mImageLoader == null) {
            synchronized (ImageLoader.class) {
                if (mImageLoader == null) {
                    mImageLoader = new ImageLoader(context);
                }
            }
        }
        return mImageLoader;
    }

    /**
     * 获取磁盘缓存的路径
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        //获取磁盘缓存路径
        boolean externalStorageAvailable =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                        && !Environment.isExternalStorageRemovable();
        String cachePath = "";
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取磁盘的可用空间
     *
     * @param path
     * @return
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private long getUsableSpace(File path) {
        //根据sdk版本调用不同的api
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        StatFs statFs = new StatFs(path.getPath());
        return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
    }

    /**
     * 添加Bitmap到内存缓存
     *
     * @param key
     * @param bitmap
     */
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        Log.w(tag, "addBitmapToMemoryCache ");
        if (mMemoryCache.get(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 从内存缓存中，获取Bitmap
     *
     * @param key
     * @return
     */
    private Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 采用MD5加密
     *
     * @param url
     * @return
     */
    public String hashKeyFromUrl(String url) {
        String cacheKey = "";
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public void bindBitmap(String url, ImageView imageView) {
        bindBitmap(url, imageView, 0, 0);
    }

    public void bindBitmap(final String url, final ImageView imageView, final int reqWidth, final int reqHeight) {

        imageView.setTag(TAG_URL_KEY, url);
        //先从缓存中获取
        Bitmap bitmap = loadBitmapFromMemoryCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        //从网路中获取,
        final Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(url, reqWidth, reqHeight);
                if (bitmap != null) {
                    LoaderResult result = new LoaderResult(imageView, url, bitmap);
                    Message msg = mMainHandler.obtainMessage();
                    msg.obj = result;
                    msg.what = MESSAGE_POST_RESULT;
                    mMainHandler.sendMessage(msg);
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    private Bitmap loadBitmap(String url, int reqWidth, int reqHeight) {
        Bitmap bitmap = loadBitmapFromMemoryCache(url);
        if (bitmap != null) {
            Log.w(tag, "loadBitmapFromMemoryCache " + url);
            return bitmap;
        }
        try {
            bitmap = loadBitmapFromDiskLruCache(url, reqWidth, reqHeight);
            if (bitmap != null) {
                Log.w(tag, "loadBitmapFromDiskLruCache " + url);
                return bitmap;
            }
            bitmap = loadBitmapFromHttp(url, reqWidth, reqHeight);
            Log.w(tag, "loadBitmapFromHttp " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap == null && !mIsDiskLruCacheCreated) {
            Log.w(tag, "enconter error, dislrucache is not created");
            return downloadBitmapFromUrl(url);
        }
        return bitmap;
    }

    private Bitmap loadBitmapFromMemoryCache(String url) {
        String key = hashKeyFromUrl(url);//有个加密算法
        return getBitmapFromMemoryCache(key);
    }

    /**
     * 从磁盘中读取Bitmap，并将其添加到内存缓存
     *
     * @param url
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap loadBitmapFromDiskLruCache(String url, int reqWidth, int reqHeight)
            throws IOException {
        Log.w(tag, "loadBitmapFromDiskLruCache");
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.w(tag, "load bitmap from ui thread, it is not recommended");
        }
        if (mDiskLruCache == null) {
            return null;
        }

        Bitmap bitmap = null;
        String key = hashKeyFromUrl(url);

        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (snapshot != null) {
            FileInputStream inputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor descriptor = inputStream.getFD();
            bitmap = mImageResizer.decodeSampledBitmapFromFileDescriptor(descriptor, reqWidth, reqHeight);
            if (bitmap != null) {
                addBitmapToMemoryCache(key, bitmap);
            }
        }
        return bitmap;
    }

    /**
     * 读取网络资源并将它写入磁盘缓存
     * @param url
     * @param reqWidth
     * @param reqHeight
     * @return
     * @throws IOException
     */
    private Bitmap loadBitmapFromHttp(String url, int reqWidth, int reqHeight) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can not visit network from ui thread");
        }
        if (mDiskLruCache == null) {
            return null;
        }
        String key = hashKeyFromUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if (downloadUrlToStream(url, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadBitmapFromDiskLruCache(url, reqWidth, reqHeight);
    }

    private Bitmap downloadBitmapFromUrl(String urlString) {
        Bitmap bitmap = null;
        HttpURLConnection mUrlConnection = null;
        BufferedInputStream in = null;
        try {
            URL url = new URL(urlString);
            mUrlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(mUrlConnection.getInputStream());
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mUrlConnection != null) {
                mUrlConnection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    public boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        Log.w(tag, "downloadUrlToStream " + urlString);
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);

            int len;
            while ((len = in.read()) != -1) {
                out.write(len);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    private class LoaderResult {
        public ImageView imageView;
        public String url;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String url, Bitmap bitmap) {
            this.imageView = imageView;
            this.url = url;
            this.bitmap = bitmap;
        }
    }

}
