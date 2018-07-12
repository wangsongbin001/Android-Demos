package com.wang.csdnapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.wang.csdnapp.R;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.ScreenUtil;

/**
 * Created by SongbinWang on 2017/6/6.
 */

public class CircleImageView extends ImageView {
    private static final String TAG = "CircleImageView";
    private Paint paint;
    private boolean needCircle;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    public void setNeedCircle(boolean needCircle){
        this.needCircle = needCircle;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap b = getCircleBitmap(bitmap);

            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDst = new Rect(getPaddingLeft(), getPaddingTop(),
                    getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
            paint.reset();
            canvas.drawBitmap(b, rectSrc, rectDst, paint);
            if(needCircle){
                Paint tempPaint = new Paint();
                tempPaint.setStyle(Paint.Style.STROKE);
                tempPaint.setAntiAlias(true);
                tempPaint.setStrokeWidth(ScreenUtil.dp2px(2));
                tempPaint.setColor(0xffffffff);
                int x = getWidth();
                canvas.drawCircle(x/2, x/2, x/2 - ScreenUtil.dp2px(2), paint);
            }

        } else {
            super.onDraw(canvas);
        }

    }

    /**
     * 获取圆形的bitmap
     *
     * @param bitmap
     * @return
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {

        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        //绘制透明色
        canvas.drawARGB(0, 0, 0, 0);
        //设置画笔一帮属性
        final int color = 0xff424242;
        paint.setColor(color);
        paint.setAntiAlias(true);

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        int x = bitmap.getWidth();
        canvas.drawCircle(x/ 2, x / 2, x / 2, paint);
        //设置绘制模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return outBitmap;
    }



}
