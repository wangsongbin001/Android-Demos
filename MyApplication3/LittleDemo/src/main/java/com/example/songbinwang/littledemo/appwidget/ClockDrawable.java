package com.example.songbinwang.littledemo.appwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.songbinwang.littledemo.util.LogUtil;
import com.example.songbinwang.littledemo.util.PixelUtil;

import java.util.Calendar;

/**
 * Created by songbinwang on 2017/2/15.
 */

public class ClockDrawable extends Drawable {

    private Paint mPaint;
    private final static int C_default = Color.WHITE;
    private final static String tag = "ClockDrawable";
    private Context mContext;
    private int padding = 2;

    public ClockDrawable(Context context) {
        this.mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(C_default);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(PixelUtil.dp2px(2, mContext));
        mPaint.setTextSize(PixelUtil.sp2px(12, mContext));
        padding = PixelUtil.dp2px(padding, mContext);
    }

    @Override
    public void draw(Canvas canvas) {
        LogUtil.i(tag, "draw");
        final Rect rect = getBounds();
        //画圆
        drawCircle(rect, canvas);
        //画竖线
        drawVerLine(rect, canvas);
        //画数字
        drawNum(rect, canvas);
        //画时针和分针
        drawHourMinuteHand(rect, canvas);
    }

    private void drawCircle(Rect rect, Canvas canvas) {
        float cx = rect.exactCenterX();
        float cy = rect.exactCenterY();
        float radius = Math.min(cx, cy) - padding;
        canvas.drawCircle(cx, cy, radius, mPaint);
        canvas.drawCircle(cx, cy, PixelUtil.dp2px(2, mContext), mPaint);
    }

    private void drawVerLine(Rect rect, Canvas canvas) {
        float cx = rect.exactCenterX();
        float cy = rect.exactCenterY();
        for (int i = 0; i < 12; i++) {
            canvas.save();
            canvas.rotate(30 * i, cx, cy);
            canvas.drawLine(cx, padding, cx, padding + PixelUtil.dp2px(5, mContext), mPaint);
            canvas.restore();
        }
    }

    private void drawNum(Rect rect, Canvas canvas) {
        float cx = rect.exactCenterX();
        float cy = rect.exactCenterY();
        for (int i = 0; i < 12; i++) {
            canvas.save();

            String txt = "" + i;
            if (i == 0) {
                txt = "12";
            }
            Rect tempRect = new Rect();
            mPaint.getTextBounds(txt, 0, txt.length(), tempRect);

            if (i == 0 || i == 1 || i == 2 || i == 10 || i == 11) {
                canvas.rotate(30 * i, cx, cy);
                float x = cx - tempRect.width() / 2.0f;
                float y = padding + PixelUtil.dp2px(5, mContext) + tempRect.height();
                canvas.drawText(txt, x, y, mPaint);
            } else if (i == 3) {
                float x = rect.width() - padding - tempRect.width() - PixelUtil.dp2px(5, mContext);
                float y = rect.height() / 2.0f + tempRect.height() / 2.0f;
                canvas.drawText(txt, x, y, mPaint);
            } else if (i == 9) {
                float x = padding + PixelUtil.dp2px(5, mContext);
                float y = rect.height() / 2.0f + tempRect.height() / 2.0f;
                canvas.drawText(txt, x, y, mPaint);
            } else {
                canvas.rotate(30 * (i - 6), cx, cy);
                float x = cx - tempRect.width() / 2.0f;
                float y = rect.height() - padding - PixelUtil.dp2px(5, mContext);
                canvas.drawText(txt, x, y, mPaint);
            }
            canvas.restore();
        }
    }

    private void drawHourMinuteHand(Rect rect, Canvas canvas) {
        float cx = rect.exactCenterX();
        float cy = rect.exactCenterY();
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        int rotate = (int) (360f * (hour * 60 * 60 + minutes * 60 + second) / (12 * 60.0f * 60));
        LogUtil.i(tag, "hour:" + hour + " minutes:" + minutes + " second:" + second + " rotate:" + rotate);

        canvas.save();
        canvas.rotate(rotate, cx, cy);
        canvas.drawLine(cx, cy - rect.height() / 6.0f, cx, cy + rect.height() / 10.0f, mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(360 * (minutes * 60 + second) / (60 * 60.0f), cx, cy);
        canvas.drawLine(cx, cy - rect.height() / 4.0f, cx, cy + rect.height() / 10.0f, mPaint);
        canvas.restore();
    }

    @Override
    public void setBounds(Rect bounds) {
        super.setBounds(bounds);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
