package com.example.songbinwang.liveinhand.login2register;

import android.content.Intent;

/**
 * Created by songbinwang on 2016/6/16.
 */
public class Login2RegisterActivity extends AppActivity {

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return LoginFragment.newInstance(null);
    }
}
