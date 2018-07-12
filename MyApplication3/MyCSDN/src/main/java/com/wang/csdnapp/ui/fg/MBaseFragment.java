package com.wang.csdnapp.ui.fg;

import android.support.v4.app.Fragment;

/**
 * Created by SongbinWang on 2017/6/14.
 */

public class MBaseFragment extends Fragment{

    //外部接口
    public static interface OnFragmentItemClickListener{
        void onFragmentItemClick(String tag);
    }
    protected OnFragmentItemClickListener onFragmentItemClickListener;

    public void setOnFragmentItemClickListener(OnFragmentItemClickListener onFragmentItemClickListener){
        this.onFragmentItemClickListener = onFragmentItemClickListener;
    }


}
