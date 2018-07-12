package com.example.songbinwang.littledemo.anim;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import com.example.songbinwang.littledemo.util.LogUtil;

/**
 * Created by songbinwang on 2017/2/20.
 */

public class MatrixDrawable extends Drawable {

    private final static String tag = "MatrixDrawable";

    //画笔
    Paint mPaint;
    //绘图矩阵
    Matrix mMatrix;
    //原始图
    Bitmap mBitmap;
    //矩阵元素存储的数组
    float[] params;

    Bitmap temp;

    MatrixDrawable(Bitmap bitmap, Matrix matrix) {
        this.mBitmap = bitmap;
        this.mMatrix = matrix;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
    }

    @Override
    public int getIntrinsicWidth() {
        if (mBitmap != null) {
            return mBitmap.getWidth();
        }
        return super.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        if (mBitmap != null) {
            return mBitmap.getHeight();
        }
        return super.getIntrinsicHeight();
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

    public Matrix getmMatrix(){
        return mMatrix;
    }
}
