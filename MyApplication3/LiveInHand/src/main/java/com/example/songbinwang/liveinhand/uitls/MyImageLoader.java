package com.example.songbinwang.liveinhand.uitls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class MyImageLoader {

    private static MyImageLoader mInstance;
    private LruCache<String, Bitmap> mLruCache;
    private ExecutorService mThreadPool;
    private static final int DEFAULT_THREAD_COUNT =1;
    private Type mType = Type.LIFO;

    private LinkedList<Runnable> mTaskQueue;
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;

    private Handler mUIHandler;
    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
    private Semaphore mSemaphoreThreadPool;
    private final int TARGET_WIDTH = 130;
    private final int TARGET_HEIGHT = 130;

    public enum Type{
        FIFO,LIFO
    }

    private MyImageLoader(int threadCount){
        init(threadCount);
    }

    private void init(int threadCount) {
        mPoolThread = new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        mThreadPool.execute(getTask());

                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                mSemaphorePoolThreadHandler.release();
                Looper.loop();

            }
        };
        mPoolThread.start();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory/4;
        mLruCache = new LruCache<String, Bitmap>(cacheMemory){
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes()*value.getHeight();
        }
        };

        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mSemaphoreThreadPool = new Semaphore(threadCount);
    }

    private Runnable getTask() {
        if(mType == Type.FIFO){
            return mTaskQueue.removeFirst();
        }else if(mType ==Type.LIFO){
            return mTaskQueue.removeLast();
        }
        return null;
    }

    public static MyImageLoader getInstance(){
        if(null == mInstance){
            synchronized (MyImageLoader.class) {
                if(null == mInstance){
                    mInstance = new MyImageLoader(3);
                }
            }
        }
        return mInstance;

    }

    public void loadImage(final String path,final ImageView imageView,int defaultImage){
        if(!path.equals(imageView.getTag())){
            imageView.setTag(path);
        }
        if(null == mUIHandler){
            mUIHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    Bitmap bitmap = holder.bitmap;
                    ImageView imageView = holder.imageview;
                    String path = holder.path;
                    if(imageView.getTag().toString().equals(path)){
                        imageView.setImageBitmap(bitmap);
                    }

                }
            };
        }

        Bitmap bitmap = getBitmapFromLruCache(path);

        if(bitmap != null){
            refreshBitmap(path, imageView, bitmap);
        }else{
            imageView.setImageResource(defaultImage);
            addTask(new Runnable() {

                @Override
                public void run() {
//                ImageSize imageSize = getImageViewSize(imageView);
//                Bitmap bitmap = decodeSampledBitmapFromPath(path,imageSize.width,imageSize.height);
                  //In order to save memory we compressed images as much as possible
                  Bitmap bitmap = decodeSampledBitmapFromPath(path,TARGET_WIDTH,TARGET_HEIGHT);
                  addBitmapToLruCache(path,bitmap);
                  refreshBitmap(path, imageView, bitmap);
                  mSemaphoreThreadPool.release();
                }

            });
        }

    }

    private void refreshBitmap(final String path,
            final ImageView imageView, Bitmap bitmap) {
        Message message = Message.obtain();
        ImgBeanHolder holder = new ImgBeanHolder();
        holder.bitmap = bitmap;
        holder.imageview = imageView;
        holder.path = path;
        message.obj = holder;

        mUIHandler.sendMessage(message);
    }

    protected void addBitmapToLruCache(String path, Bitmap bitmap) {
        if(getBitmapFromLruCache(path) ==null){
            if(null != bitmap){
                mLruCache.put(path, bitmap);
            }
        }
    }

    protected Bitmap decodeSampledBitmapFromPath(String path, int width,int height) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = caculateInSampleSize(options,width,height);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    private int caculateInSampleSize(Options options, int reWidth, int reHeight) {

        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if(width>reWidth || height >reHeight){
            int widthScale = Math.round((float) width / (float) reWidth);
            int heightScale = Math.round((float) height / (float) reHeight);

            inSampleSize = widthScale < heightScale ? widthScale : heightScale;
        }

        return inSampleSize;
    }

    private class ImageSize{
            int width;
            int height;
    }

    protected ImageSize getImageViewSize(ImageView imageView) {
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();

        ImageSize imageSize = new ImageSize();
        LayoutParams lp = imageView.getLayoutParams();
        int width = imageView.getWidth();

        if(width <=0){
            width = lp.width;
        }

        if(width <=0){
            width = imageView.getMaxWidth();
        }

        if(width <=0){
            width = displayMetrics.widthPixels;
        }

        int height = imageView.getHeight();

        if(height <=0){
            height = lp.width;
        }

        if(height <=0){
            height = imageView.getMaxHeight();
        }

        if(height <=0){
            height = displayMetrics.heightPixels;
        }

        imageSize.width = width;
        imageSize.height = height;

        return imageSize;
    }

    private synchronized void addTask(Runnable runnable) {
        mTaskQueue.add(runnable);
        try {
            if(null == mPoolThreadHandler){
                mSemaphorePoolThreadHandler.acquire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mPoolThreadHandler.sendEmptyMessage(0);
    }

    private Bitmap getBitmapFromLruCache(String key) {
        return mLruCache.get(key);
    }

    private class ImgBeanHolder{
        Bitmap bitmap;
        ImageView imageview;
        String path;
    }
}
