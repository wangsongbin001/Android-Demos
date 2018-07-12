package com.wang.csdnapp.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by SongbinWang on 2017/2/28.
 */

public abstract class BaseFragment extends Fragment{

    protected BaseActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }
    //布局id
    protected abstract int getLayoutId();
    //初始化布局
    protected abstract void initViews(View view, Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initViews(view, savedInstanceState);
        return view;
    }

    protected void toast(String msg){
        Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show();
    }
}
