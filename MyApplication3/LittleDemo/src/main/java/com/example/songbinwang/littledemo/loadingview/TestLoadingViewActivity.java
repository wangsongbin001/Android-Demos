package com.example.songbinwang.littledemo.loadingview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.songbinwang.littledemo.R;

/**
 * Created by songbinwang on 2016/8/19.
 */
public class TestLoadingViewActivity extends Activity{

    private LVCircularCD mLVCircularCD;
    private LVCircularRing mLVCircularRing;
    private LVCircular mLVCircular;
    private LVFivePointedStar mLVFivePointedStar;
    private LVCircularSmile mLVCircularSmile;
    private LVGear mLVGear;
    private LVGearsTwo mLVGearsTwo;
    private LVWifi mLVWifi;
    private LVCircularJump mLVCircularJump;
    private LVCircularZoom mLVCircularZoom;
    private LVPlayBall mLVPlayBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_loadingview);
        initViews();
    }

    public void initViews(){
        mLVCircularCD = (LVCircularCD) findViewById(R.id.lvcircularcd);
        mLVCircularRing = (LVCircularRing) findViewById(R.id.lvcircularring);
        mLVCircular = (LVCircular) findViewById(R.id.lvcircular);
        mLVFivePointedStar = (LVFivePointedStar) findViewById(R.id.lvfivepointedstar);
        mLVCircularSmile = (LVCircularSmile) findViewById(R.id.lvcircularsmile);
        mLVGear = (LVGear) findViewById(R.id.lvgear);
        mLVGearsTwo = (LVGearsTwo) findViewById(R.id.lvgearstwo);
        mLVWifi = (LVWifi) findViewById(R.id.lvwifi);
        mLVCircularJump = (LVCircularJump) findViewById(R.id.lvcircularjump);
        mLVCircularZoom = (LVCircularZoom) findViewById(R.id.lvcircularzoom);
        mLVPlayBall = (LVPlayBall) findViewById(R.id.lvplayball);
    }
    
    public void onClick(View view){
        switch(view.getId()){
            case R.id.lvcircularcd:
                if(mLVCircularCD.isInAnim()){
                    mLVCircularCD.stopAnim();
                }else{
                    mLVCircularCD.startAnim();
                }
                break;
            case R.id.lvcircularring:
                if(mLVCircularRing.isInAnim()){
                    mLVCircularRing.stopAnim();
                }else{
                    mLVCircularRing.startAnim();
                }
                break;
            case R.id.lvcircular:
                if(mLVCircular.isInAnim()){
                    mLVCircular.stopAnim();
                }else{
                    mLVCircular.startAnim();
                }
                break;
            case R.id.lvfivepointedstar:
                if(mLVFivePointedStar.isInAnim()){
                    mLVFivePointedStar.stopAnim();
                }else{
                    mLVFivePointedStar.setIsDrawPath(true);
                    mLVFivePointedStar.startAnim();
                }
                break;
            case R.id.lvcircularsmile:
                if(mLVCircularSmile.isInAnim()){
                    mLVCircularSmile.stopAnim();
                }else{
                    mLVCircularSmile.startAnim();
                }
                break;
            case R.id.lvgear:
                if(mLVGear.isInAnim()){
                    mLVGear.stopAnim();
                }else{
                    mLVGear.startAnim();
                }
                break;
            case R.id.lvgearstwo:
                if(mLVGearsTwo.isInAnim()){
                    mLVGearsTwo.stopAnim();
                }else{
                    mLVGearsTwo.startAnim();
                }
                break;
            case R.id.lvwifi:
                if (mLVWifi.isInAnim()){
                    mLVWifi.stopAnim();
                }else{
                    mLVWifi.startAnim();
                }
                break;
            case R.id.lvcircularjump:
                if(mLVCircularJump.isInAnim()){
                    mLVCircularJump.stopAnim();
                }else{
                    mLVCircularJump.startAnim();
                }
                break;
            case R.id.lvcircularzoom:
                if(mLVCircularZoom.isInAnim()){
                    mLVCircularZoom.stopAnim();
                }else{
                    mLVCircularZoom.startAnim();
                }
                break;
            case R.id.lvplayball:
                if(mLVPlayBall.isInAnim()){
                    mLVPlayBall.stopAnim();
                }else{
                    mLVPlayBall.startAnim();
                }
                break;
        }
    }
}
