package com.example.songbinwang.myapplication.download;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.songbinwang.myapplication.R;

/**
 * Created by SongbinWang on 2017/8/8.
 */

public class DownloadActivity extends Activity{

    private Button btn_start, btn_stop, btn_continue;
    private TextView tv_name,tv_progress, tv_speed;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
//        new MyTask().execute();


    }

    class MyTask extends AsyncTask<Void, Void, String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Void... params) {
            return null;
        }
    }
}
