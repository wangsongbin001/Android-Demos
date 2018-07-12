package com.example.songbinwang.littledemo.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.example.songbinwang.littledemo.R;

/**
 * Created by songbinwang on 2017/2/13.
 */

public class NotificationActivity extends Activity {

    Button btn_notify, btn_update, btn_cancel;
    ImageView iv_progress;
    private static final int Notification_ID = 1001;

    private static final int MSG_UPDATE = 1002;
    private static final int MSG_FINISH = 1003;
    private static final int MSG_TEMP = 1004;

    private ProgressDrawable progressDrawable;
    private Bitmap mBitmap;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case MSG_UPDATE:
                    int progress = (int) msg.obj;
                    //notifyN(progress);
                    updateProgressDrawable(progress);
                    break;
                case MSG_FINISH:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initViews();
        registerListeners();
    }

    private void initViews(){
        btn_notify = (Button) findViewById(R.id.id_notify);
        btn_update = (Button) findViewById(R.id.id_update);
        btn_cancel = (Button) findViewById(R.id.id_cancel);

        progressDrawable = new ProgressDrawable();
        iv_progress = (ImageView) findViewById(R.id.id_iv_progress);
        iv_progress.setImageDrawable(progressDrawable);
    }

    private void registerListeners(){
        btn_notify.setOnClickListener(onClicker);
        btn_update.setOnClickListener(onClicker);
        btn_cancel.setOnClickListener(onClicker);
        iv_progress.setOnClickListener(onClicker);
    }

    private View.OnClickListener onClicker = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.id_notify:
                    notifyN();
                    break;
                case R.id.id_update:
                    updateN();
                    break;
                case R.id.id_cancel:
                    cancelN();
                    break;
            }
        }
    };

    private void notifyN(int...progress){
        int prog = 0;
        if(progress != null && progress.length > 0){
            prog = progress[0];
        }
//        Notification notification = createDefault(prog);
        Notification notification = createCustom();
        NotificationManager nManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(Notification_ID, notification);
    }

    private void updateN(){
        new Thread(){
            private boolean isContinue = true;
            private int progress;
            @Override
            public void run() {
                while(isContinue){
                    if(progress >= 100){
                        isContinue = false;
                    }
                    Message msg = handler.obtainMessage();
                    msg.what = MSG_UPDATE;
                    msg.obj = progress;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendMessage(msg);
                    progress += 5;
                }
            }
        }.start();
    }

    private void cancelN(){
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.cancel(Notification_ID);
    }

    /**
     * 创造默认形式的通知
     * @return
     */
    private Notification createDefault(int prog){
        NotificationCompat.Builder mBuilder
                = new NotificationCompat.Builder(this);
        Notification n = mBuilder
                .setContentTitle("测试标题")
                //设置通知栏标题
                .setContentText("测试内容")
                //设置通知栏显示内容
                .setLargeIcon(BitmapFactory.decodeResource(
                        getResources(),R.drawable.aa2))
                //设置大图标
                .setContentText("测试内容")
                //设置通知栏显示内容
                .setContentIntent(getDefalutIntent(
                        Notification.FLAG_AUTO_CANCEL))
                //设置通知栏点击意图
                //  .setNumber(number) //设置通知集合的数量
                .setTicker("测试通知来啦")
                //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())
                //通知产生的时间，会在通知信息里显示，一般是系统获
                // 取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT)
                //设置该通知优先级
                .setAutoCancel(true)
                //设置这个标志当用户单击面板就可以让通知将自动取消
                // setOngoing(false)
                // ture，设置他为一个正在进行的通知。他们通常是用来
                // 表示一个后台任务,用户积极参与(如播放音乐)或以某种
                // 方式正在等待,因此占用设备(如一个文件下载,同步操作
                // ,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                //向通知添加声音、闪灯和振动效果的最简单、最一致的方
                // 式是使用当前的用户默认设置，使用defaults属性，可以
                // 组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音
                // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_launcher)
                //设置通知小ICON
                .setProgress(100, prog, false)
                //设置进度
        .build();
        return n;
    }

    /**
     *
     * @param flags
     * @return
     */
    private PendingIntent getDefalutIntent(int flags){
        PendingIntent pIntent = PendingIntent.getActivity(this,
                0, new Intent(this, NotificationActivity.class),
                flags);
        return pIntent;
    }

    private Notification createCustom(boolean...b){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Notification n = mBuilder
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                //设置通知栏点击意图
                //  .setNumber(number)
                // 设置通知集合的数量
                .setTicker("测试通知来啦")
                //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())
                //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT)
                //设置该通知优先级
                .setAutoCancel(true)
                //设置这个标志当用户单击面板就可以让通知将自动取消
                //.setOngoing(false)
                // ture，设置他为一个正在进行的通知。他们通常是用来表示
                // 一个后台任务,用户积极参与(如播放音乐)或以某种方式正
                // 在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                //向通知添加声音、闪灯和振动效果的最简单、最一致的方式是
                // 使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音
                // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_launcher)
                //设置通知小ICON
                .build();
        RemoteViews remoteViews =
                new RemoteViews(this.getPackageName(), R.layout.notification);
        remoteViews.setImageViewResource(R.id.id_icon, R.drawable.aa2);
        remoteViews.setTextViewText(R.id.id_title, "test title");
        remoteViews.setTextViewText(R.id.id_content, "test content");

        if(b == null){
            mBitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.RGB_565);
            drawBitmap(mBitmap);
        }
        remoteViews.setImageViewBitmap(R.id.id_progress, mBitmap);
        n.contentView = remoteViews;
        return n;
    }

    private void updateProgressDrawable(int progress){
        progressDrawable.setProgress(progress);
        int w = dp2px(50);
        int h = dp2px(50);
        Bitmap.Config config = progressDrawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);

        Canvas canvas = new Canvas(bitmap);
        progressDrawable.setBounds(0, 0, w, h);
        progressDrawable.draw(canvas);
        mBitmap = bitmap;

        Notification notification = createCustom(true);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(Notification_ID, notification);
        mBitmap.recycle();
    }

    private void drawBitmap(Bitmap bitmap){
        Canvas canvas = new Canvas(bitmap);

    };

    public int dp2px(int dp){
        float density = getResources().getDisplayMetrics().density;
        return (int) (density * dp);
    }
}
