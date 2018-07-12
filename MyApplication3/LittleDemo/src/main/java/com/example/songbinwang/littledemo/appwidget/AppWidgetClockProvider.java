package com.example.songbinwang.littledemo.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

import com.example.songbinwang.littledemo.R;
import com.example.songbinwang.littledemo.util.BitmapUtil;
import com.example.songbinwang.littledemo.util.LogUtil;
import com.example.songbinwang.littledemo.util.PixelUtil;
import com.example.songbinwang.littledemo.util.StringUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by songbinwang on 2017/2/15.
 * 最重要的方法有两种onReceive, onUpdate.
 * onUpdate, 小部件被加载到桌面
 * onReceive，自定义的点击事件（即发送自定义的广播，在这里接收并处理）。
 */

public class AppWidgetClockProvider extends AppWidgetProvider {

    public static final String tag = "AppWidgetClockProvider";
    private static RemoteViews remoteViews;
    //自定义的广播Action。
    public static final String AppWidget_Click_Action = "com.example.littledemo.appwidgetclockprovider";
    //自定义的时钟的Drawable，根据当前的时间，绘制正确的图形。
    ClockDrawable clockDrawable;
    //用于实现，每隔一秒更新桌面小部件，（最好应该启动service定时跟新小部件，因为广播接收器的生命周期很短）
    Timer timer;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    /**
     * 接收广播
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        LogUtil.i(tag, "onReceive");
        String action = intent.getAction();
        LogUtil.i(tag, "action" + action);
        //接收到click点击事件发送的广播，启动主界面（响应点击事件）
        if(AppWidget_Click_Action.equals(action)){
            Intent in = new Intent(context, AppWidgetActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(in);
        }
    }

    /**
     * 小部件被更新时，调用方法
     * (原以为响应updatePeriodMillis 定义的周期，但其实没有。)
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        LogUtil.i(tag, "onUpdate");
        //启动定义任务，更新小部件
        if(timer == null){
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < appWidgetIds.length; i++) {
                                onWidgetUpdate(context, appWidgetManager, appWidgetIds[i]);
                            }
                        }
                    });
                }
            }, 0, 1000);
        }
    }

    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget_clock);

        //设置点击事件
        Intent intent = new Intent(AppWidget_Click_Action);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.id_iv_clock, pIntent);

        //更新小部件，图标
        if(clockDrawable == null){
            clockDrawable = new ClockDrawable(context);
            clockDrawable.setBounds(0, 0, PixelUtil.dp2px(150, context),PixelUtil.dp2px(150, context));
        }
        Bitmap bitmap = BitmapUtil.drawable2Bitmap(clockDrawable);
        remoteViews.setImageViewBitmap(R.id.id_iv_clock, bitmap);
        //更新部件文本
        String txt = StringUtil.getAppWidgetTxt();
        remoteViews.setTextViewText(R.id.id_tv_clock, txt);

        //更新部件
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        bitmap.recycle();
    }

    /**
     * 每删除一次窗口小部件，就调用一次
     *
     * @param context
     * @param appWidgetIds
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        LogUtil.i(tag, "onDeleted");
        LogUtil.i(tag, "timer is" + timer != null? "timer" : null);
        if(timer != null){
            timer.cancel();
        }
        timer = null;
    }

    /**
     * 当最后一个窗口小部件被删除时，调用此方法
     *
     * @param context
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        LogUtil.i(tag, "onDisabled");
    }

    /**
     * 当第一个框口小部件第一次被添加到桌面，调用此方法
     *
     * @param context
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        LogUtil.i(tag, "onEnabled");
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        LogUtil.i(tag, "onRestored");
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        LogUtil.i(tag, "onAppWidgetOptionsChanged");
    }

}
