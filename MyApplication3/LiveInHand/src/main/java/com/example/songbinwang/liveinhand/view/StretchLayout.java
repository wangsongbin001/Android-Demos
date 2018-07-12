package com.example.songbinwang.liveinhand.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.songbinwang.liveinhand.R;

/**
 * Created by songbinwang on 2016/6/24.
 */
public class StretchLayout extends RelativeLayout{

    private Animator animator;
    private BaseAnimatorListener mListener;
    private Context mContext;
    private static final String TAG = "StretchLayout";
    private boolean isInAnim = false;

    public StretchLayout(Context context) {
        super(context);
        mContext = context;
        registerListener();
    }

    public StretchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        registerListener();
    }

    public StretchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        registerListener();
    }

    public StretchLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        registerListener();
    }

    private void registerListener(){
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInAnim){
                    return;
                }
                Log.i(TAG, "onClick");
                if (animator == null) {
                    animator = initAnimator(v);
                }
                animator.setTarget(v);
                animator.start();
            }
        });
    }

    private Animator initAnimator(View view){
        animator = AnimatorInflater.loadAnimator(mContext, R.animator.animator_stretch);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isInAnim = true;
                if (mListener != null) {
                    mListener.onAnimationStart(animation);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isInAnim = false;
                if (mListener != null) {
                    mListener.onAnimationEnd(animation);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isInAnim = false;
                if (mListener != null) {
                    mListener.onAnimationCancel(animation);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (mListener != null) {
                    mListener.onAnimationRepeat(animation);
                }
            }
        });
        return animator;
    }

    public void setAnimatorListener(BaseAnimatorListener mListener){
        this.mListener = mListener;
    }

    public static abstract class BaseAnimatorListener implements Animator.AnimatorListener{

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

}
