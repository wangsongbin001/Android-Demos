package com.wang.csdnapp.ui.fg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.csdnapp.R;
import com.wang.csdnapp.util.ToastUtil;

/**
 * Created by songbinwang on 2017/6/15.
 */

public class UserFragment extends MBaseFragment {
    RelativeLayout rl_set;
    private ImageView iv_set_item_left, iv_drawer;
    private TextView tv_set_item_title, tv_set_item_data;

    public static UserFragment newInstance(Bundle bundle) {
        UserFragment fg = new UserFragment();
        fg.setArguments(bundle);
        return fg;
    }

    ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fg_user, null);
        initViews(root);
        registerListener();
        return root;
    }

    private void initViews(View root) {
        iv_drawer = (ImageView) root.findViewById(R.id.iv_drawer);
        rl_set = (RelativeLayout) root.findViewById(R.id.rl_set);
        rl_set.setOnClickListener(onClicker);

        iv_set_item_left = (ImageView) rl_set.findViewById(R.id.iv_item_left);
        tv_set_item_title = (TextView) rl_set.findViewById(R.id.tv_item_title);
        tv_set_item_data = (TextView) rl_set.findViewById(R.id.tv_item_data);
    }

    private void registerListener(){
        iv_drawer.setOnClickListener(onClicker);
    }

    View.OnClickListener onClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtil.toast("设置");
            switch (v.getId()) {
                case R.id.iv_drawer:
                    if(onFragmentItemClickListener != null){
                        onFragmentItemClickListener.onFragmentItemClick("drawer");
                    }
                    break;
            }
        }
    };
}
