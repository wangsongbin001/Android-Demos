package com.wang.csdnapp.ui.main;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.wang.csdnapp.R;
import com.wang.csdnapp.ui.fg.HeadLinesFragment;
import com.wang.csdnapp.ui.fg.MBaseFragment;
import com.wang.csdnapp.ui.fg.MainFragment;
import com.wang.csdnapp.ui.fg.UserFragment;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.view.MainMenuLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songbinwang on 2017/6/5.
 */

public class MainMenuActivity extends MBaseActivity implements MBaseFragment.OnFragmentItemClickListener
        , MainMenuLayout.OnMenuItemClickListener {

    private static final String TAG = "MainMenu";
    private DrawerLayout drawerLayout;
    private FrameLayout fl_main, fl_menu;
    HeadLinesFragment mHeadlinesFragment;
    UserFragment userFragment;
    MBaseFragment currentFragment;
    List<MBaseFragment> listFragments;
    private MainMenuLayout mainMenuLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        drawerLayout = (DrawerLayout) findViewById(R.id.dl_container);
        fl_main = (FrameLayout) findViewById(R.id.fl_main);
        fl_menu = (FrameLayout) findViewById(R.id.fl_menu);
        mainMenuLayout = (MainMenuLayout) findViewById(R.id.main_menu);
        mainMenuLayout.setOnMenuItemClickListener(this);

        listFragments = new ArrayList<>();
        if (savedInstanceState == null) {
            mHeadlinesFragment = new HeadLinesFragment();
            mHeadlinesFragment.setOnFragmentItemClickListener(this);
            show(mHeadlinesFragment);
            currentFragment = mHeadlinesFragment;
        }
    }

    @Override
    public int getFragmentContentID() {
        return R.id.fl_main;
    }

    /**
     * MBaseFragment.OnFragmentItemClickListener
     *
     * @param tag
     */
    @Override
    public void onFragmentItemClick(String tag) {
        if ("drawer".equals(tag)) {
            drawerLayout.openDrawer(fl_menu);
        } else if ("add".equals(tag)) {

        } else if ("search".equals(tag)) {

        }
    }

    /**
     * MainMenuLayout.OnMenuItemClickListener
     *
     * @param item
     */
    @Override
    public void onMenuItemClick(int item) {
        LogUtil.i(TAG, "onMenuItemClick " + item);

        switch (item) {
            case 4:
                if (currentFragment == userFragment) {
                    drawerLayout.closeDrawers();
                    return;
                }
                if (userFragment == null) {
                    userFragment = UserFragment.newInstance(new Bundle());
                    userFragment.setOnFragmentItemClickListener(this);
                }
                drawerLayout.closeDrawers();
                switchContent(currentFragment, userFragment);
                currentFragment = userFragment;
                break;
            case 0:
                if (currentFragment == mHeadlinesFragment) {
                    drawerLayout.closeDrawers();
                    return;
                }
                if (mHeadlinesFragment == null) {
                    mHeadlinesFragment = new HeadLinesFragment();
                    mHeadlinesFragment.setOnFragmentItemClickListener(this);
                }
                drawerLayout.closeDrawers();
                switchContent(currentFragment, mHeadlinesFragment);
                currentFragment = mHeadlinesFragment;
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //主界面退出，即app退出，清楚网页缓存,全局
        try {
            WebView webView = new WebView(this);
            webView.clearCache(true);
            webView.destroy();
        } catch (Exception e) {

        }
    }
}
