package com.wang.csdnapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wang.csdnapp.R;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.ScreenUtil;

import java.util.Random;

/**
 * Created by SongbinWang on 2017/5/24.
 */

public class LocalVerifyCode extends TextView implements View.OnClickListener {

    private static final String TAG = "LocalVerifyCode";

    String text;
    Paint mPaint;
    private Context mContext;
    private int[] codeArray = null;
    private int[] colorIds = null;

    public LocalVerifyCode(Context context) {
        this(context, null);
    }

    public LocalVerifyCode(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LocalVerifyCode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
        mContext = context;

        mPaint = new Paint();
        mPaint.setStrokeWidth(ScreenUtil.dp2px(mContext, 2));
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);

//        setTextSize(ScreenUtil.sp2px(mContext, 18));
        setGravity(Gravity.CENTER);
//        int padding = (int) ScreenUtil.dp2px(mContext, 10);
//        setPadding(padding, padding, padding, padding);
        setText(createNewContent());
    }

    private void init() {
        codeArray = new int[]{0, 0, 0, 0};
        colorIds = new int[]{
                R.color.verify_color1,
                R.color.verify_color2,
                R.color.verify_color3,
                R.color.verify_color4,
                R.color.verify_color5,
                R.color.verify_color6,
                R.color.verify_color7,
                R.color.verify_color8,
                R.color.verify_color9,
                R.color.verify_color0,
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getMeasuredWidth();
        float height = getMeasuredHeight();

        mPaint.setColor(mContext.getResources().getColor(colorIds[codeArray[0]]));
        canvas.drawLine(0, codeArray[0] * height / 9,
                width, codeArray[1] * height / 9, mPaint);
        canvas.drawLine(codeArray[2] * width / 9, 0,
                codeArray[3] * width / 9, height, mPaint);
    }

    /**
     * 判断验证码是否正确
     *
     * @param contont
     * @return
     */
    public boolean check(String contont) {
        text = "" + codeArray[0] + codeArray[1] + codeArray[2] + codeArray[3];
        LogUtil.i(TAG, "check text:" + text);
        return text.equals(contont);
    }

    @Override
    public void onClick(View v) {
        LogUtil.i(TAG, "onClick");
        setText(createNewContent());
    }

    /**
     * 创建随机数列，并且渲染不同的颜色
     *
     * @return
     */
    private SpannableStringBuilder createNewContent() {
        Random random = new Random();
        StringBuilder txt = new StringBuilder();
        for (int i = 0; i < codeArray.length; i++) {
            codeArray[i] = random.nextInt(10);
            txt.append(codeArray[i]);
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(txt.toString());
        builder.setSpan(new ForegroundColorSpan(mContext.getResources()
                .getColor(colorIds[codeArray[0]])), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(mContext.getResources()
                .getColor(colorIds[codeArray[1]])), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(mContext.getResources()
                .getColor(colorIds[codeArray[2]])), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(mContext.getResources()
                .getColor(colorIds[codeArray[3]])), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * 判断验证码是否正确
     * @param content
     * @return
     */
    public boolean isEqual(String content) {
        if (content == null || content.length() != 4) {
            return false;
        }
        StringBuilder txt = new StringBuilder();
        for (int i = 0; i < codeArray.length; i++) {
            txt.append(codeArray[i]);
        }
        return content.equals(txt.toString());
    }
}
