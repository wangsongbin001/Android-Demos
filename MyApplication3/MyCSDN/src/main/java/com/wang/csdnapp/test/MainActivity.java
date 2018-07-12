package com.wang.csdnapp.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.wang.csdnapp.R;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * 照片墙主活动，使用GridView展示照片墙。
 *
 * @author guolin
 */
public class MainActivity extends Activity implements View.OnClickListener, WbShareCallback {

    private static final String TAG = "TestMainActivity";
    /**
     * 用于展示照片墙的GridView
     */
    private GridView mPhotoWall;

    /**
     * GridView的适配器
     */
    private PhotoWallAdapter mAdapter;

    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private Button btn_share, btn_stop, btn_rxjava;
    WbShareHandler shareHandler;
    private ImageView iv_content;
    private Button btn_tosecond;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        btn_share = (Button) findViewById(R.id.btn_share);
        btn_share.setOnClickListener(this);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(this);
        btn_rxjava = (Button) findViewById(R.id.btn_rxjava);
        btn_rxjava.setOnClickListener(this);

        iv_content = (ImageView) findViewById(R.id.iv_content);
        btn_tosecond = (Button) findViewById(R.id.btn_tosecond);
        btn_tosecond.setOnClickListener(this);
//        WbSdk.install(this, new AuthInfo(this, Constant.WeiBo_AppKey,
//                Constant.WeiBo_REDIRECT_URL, Constant.WeiBo_Score));

//		mImageThumbSize = getResources().getDimensionPixelSize(
//				R.dimen.image_thumbnail_size);
//		mImageThumbSpacing = getResources().getDimensionPixelSize(
//				R.dimen.image_thumbnail_spacing);
//		mPhotoWall = (GridView) findViewById(R.id.photo_wall);
//		mAdapter = new PhotoWallAdapter(this, 0, Images.imageThumbUrls,
//				mPhotoWall);
//		mPhotoWall.setAdapter(mAdapter);
//		mPhotoWall.getViewTreeObserver().addOnGlobalLayoutListener(
//				new ViewTreeObserver.OnGlobalLayoutListener() {
//
//					@Override
//					public void onGlobalLayout() {
//						final int numColumns = (int) Math.floor(mPhotoWall
//								.getWidth()
//								/ (mImageThumbSize + mImageThumbSpacing));
//						if (numColumns > 0) {
//							int columnWidth = (mPhotoWall.getWidth() / numColumns)
//									- mImageThumbSpacing;
//							mAdapter.setItemHeight(columnWidth);
//							mPhotoWall.getViewTreeObserver()
//									.removeGlobalOnLayoutListener(this);
//						}
//					}
//				});

//        btn_share = (Button) findViewById(R.id.btn_share);
//        btn_share.setOnClickListener(this);
//        shareHandler = new WbShareHandler(this);
//        shareHandler.registerApp();
//        AesUtil.init(this);
//        String username = PreferenceUtil.readString(this, "username");
//        LogUtil.i(TAG, "username:" + username);
//        try {
//            LogUtil.i(TAG, "decrypt username:" + AesUtil.decrypt(username));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //验证Base
//        MyUser user = new MyUser();
//        user.setNickname("xiaosongshu");
//        user.setPassword("000000");
//        user.setEmail("wangsongbin92@163.com");
//        LogUtil.i(TAG, "原始数据：" + user.toString());
//        UserUtil.saveUser(this, user);

        //OkHttp get
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url("https://www.baidu.com/").build();
//        try {
//            Response response = okHttpClient.newCall(request).execute();
//            if(response.isSuccessful()){
//                String result = response.body().toString();
//                LogUtil.i(TAG, "result:" + result);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //Http post json
//        final MediaType JSON  = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body1 = RequestBody.create(JSON, "json");
//        Request request1 = new Request.Builder()
//                .url("https://www.baidu.com/")
//                .post(body1)
//                .build();
//        try {
//            Response response = okHttpClient.newCall(request1).execute();
//            if(response.isSuccessful()){
//                String result = response.body().toString();
//                LogUtil.i(TAG, "result:" + result);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //OkHttp post 提交键值对
//        FormBody body2 = new FormBody.Builder()
//                .add("", "")
//                .add("", "")
//                .add("", "")
//                .build();
//        Request request2 = new Request
//                .Builder()
//                .url("")
//                .post(body2)
//                .build();

//        new DownLoadTask().execute();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    private static final OkHttpClient client = new OkHttpClient.Builder().build();

    private void get(String url) throws IOException {
        Request request1 = new Request.Builder()
                .url(url)
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();


        Response response1 = client.newCall(request1).execute();
        if (!response1.isSuccessful()) {
            throw new IOException("Unexpected code " + response1);
        }
        LogUtil.i(TAG, "response:" + response1.body().string());
        LogUtil.i(TAG, "response cache:" + response1.cacheResponse());
        LogUtil.i(TAG, "response network:" + response1.networkResponse());

        Request request2 = new Request.Builder().url(url).build();
        Response response2 = client.newCall(request1).execute();
        if (!response1.isSuccessful()) {
            throw new IOException("Unexpected code " + response2);
        }
        LogUtil.i(TAG, "response:" + response2.body().string());
        LogUtil.i(TAG, "response cache:" + response2.cacheResponse());
        LogUtil.i(TAG, "response network:" + response2.networkResponse());
    }


    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    private String post(String url, Map<String, String> params) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        Iterator<String> iterator = params.keySet().iterator();
        if (iterator.hasNext()) {
            String key = iterator.next();
            //添加键值对
            builder.add(key, params.get(key));
        }
        FormBody body = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 下载文件
     *
     * @param url
     * @param path
     */
    private void downloadFile(final String url, String path) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.i(TAG, "onFailure:" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream in = null;
                FileOutputStream fos = null;
                try {
                    String path = "mnt/sdcard/Download/temp.jpg";
                    File file = new File(path);
                    fos = new FileOutputStream(file);
                    if (response.isSuccessful()) {
                        LogUtil.i(TAG, "onResponse success:");
                        in = response.body().byteStream();
                        int len;
                        byte[] buf = new byte[10234];
                        while ((len = in.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                    } else {
                        LogUtil.i(TAG, "onResponse code:" + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (Exception e) {
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        });
    }

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    private static Gson gosn = new Gson();

    private Object uploadFile(String url, File file) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, file);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return gosn.fromJson(response.body().charStream(), Object.class);
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //微博分享结果处理
    @Override
    public void onWbShareSuccess() {
        //分享成功
        ToastUtil.toast("分享成功");
    }

    @Override
    public void onWbShareCancel() {
        //分享取消
        ToastUtil.toast("分享取消");
    }

    @Override
    public void onWbShareFail() {
        //分享失败
        ToastUtil.toast("分享失败");
    }
    //微博分享结果处理


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share:
//                share2Weibo();
//                try {
//                    String encryptStr = AesUtil.encrypt("wangsongbin");
//                    PreferenceUtil.write(MainActivity.this, "username", encryptStr);
//                    String username = PreferenceUtil.readString(MainActivity.this, "username");
//                    LogUtil.i(TAG, "username:" + username);
//                    LogUtil.i(TAG, "decrypt username:" + AesUtil.decrypt(username));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String url = "http://www.yingkounews.com/shenghuo/fushi/201210/W020121022455609991692.jpg";
//                downloadFile(url, "");

                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                startService(intent);
                break;
            case R.id.btn_stop:
                Intent intent1 = new Intent(MainActivity.this, DownloadService.class);
                stopService(intent1);
                break;
            case R.id.btn_rxjava:
                testRxjava();
                break;
            case R.id.btn_tosecond:
                Intent intent2 = new Intent(MainActivity.this, TestSecondActivity.class);
                startActivity(intent2);
                break;

        }

    }

    private void testRxjava() {
        //将数组names中的所有字符串打印出来。
        String[] names = new String[]{"wangwu", "zhangsan", "lisi"};
        compositeSubscription.add(
                Observable.from(names)
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                LogUtil.i(TAG, "name:" + s);
                            }
                        })
        );


        int resId = R.drawable.aa2;

        Observable.create(new Observable.OnSubscribe<Drawable>() {

            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                LogUtil.i(TAG, "currentThread:" + Thread.currentThread());
                Drawable drawable = MainActivity.this.getResources().getDrawable(R.drawable.aa2);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Drawable>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        LogUtil.i(TAG, "onStart");
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        LogUtil.i(TAG, "currentThread:" + Thread.currentThread());
                        LogUtil.i(TAG, "onNext");
                        iv_content.setImageDrawable(drawable);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LogUtil.i(TAG, "onError");
                    }

                    @Override
                    public void onCompleted() {
                        LogUtil.i(TAG, "onCompleted");
                    }
                });
        Observable ob = null;
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                LogUtil.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                LogUtil.i(TAG, "onError");
            }

            @Override
            public void onNext(String s) {
                LogUtil.i(TAG, "onNext:" + s);
            }
        };

//        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
//
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("1 times");
//                subscriber.onNext("2 times");
//                subscriber.onNext("3 times");
//                subscriber.onCompleted();
//            }
//        });

//        Observable observable = Observable.from(new String[]{"1 times", "2 times", "3 times"});
        //将会一次调用
        //onNext("1 times");
        //onNext("2 times");
        //onNext("3 times");
        //onCompleted();

        Observable observable = Observable.just("1 times", "2 times", "3 times");
        //将会依次调用
        //onNext("1 times");
        //onNext("2 times");
        //onNext("3 times");
        //onCompleted();
        // 注意：这不是 subscribe() 的源码，而是将源码中与性能、兼容性、扩展性有关的代码剔除后的核心代码。
//        public Subscription subscribe(Subscriber subscriber) {
//            subscriber.onStart();
//            onSubscribe.call(subscriber);
//            return subscriber;
//        }
        Action1<String> nextAction = new Action1<String>() {
            @Override
            public void call(String s) {

            }
        };
        Action1<Throwable> errorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable o) {

            }
        };
        Action0 completeAction = new Action0() {
            @Override
            public void call() {

            }
        };
        //自动创建Subscriber，使用nextAction,来定义onNext
        observable.subscribe(nextAction);
        //自动创建Subscriber，使用nextAction，errorAction来定义onNext与onError
        observable.subscribe(nextAction, errorAction);
        //自动创建Subscriber，使用nextAction，errorAction，completeAction来定义onNext，onError，onComplete
        observable.subscribe(nextAction, errorAction, completeAction);


    }

    //第三步
    private void share2Weibo() {
        WeiboMultiMessage shareMessage = new WeiboMultiMessage();
        String articleUrl = "http://www.csdn.net/article/2016-10-26/2826665";

        TextObject text = new TextObject();
        text.text = "http://www.csdn.net/article/2016-10-26/2826665";
        shareMessage.textObject = text;

        ImageObject img = new ImageObject();
        img.imagePath = "https://www.baidu.com/img/bd_logo1.png";
        shareMessage.imageObject = img;

        shareHandler.shareMessage(shareMessage, false);
    }

    //第四步,处理返回结果
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//		mAdapter.fluchCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
//		mAdapter.cancelAllTasks();
        if(compositeSubscription.isUnsubscribed()){
            compositeSubscription.unsubscribe();
        }
    }

}