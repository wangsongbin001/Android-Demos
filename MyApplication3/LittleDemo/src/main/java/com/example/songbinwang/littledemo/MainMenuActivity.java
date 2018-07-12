package com.example.songbinwang.littledemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 * Created by SongbinWang on 2017/7/19.
 */

public class MainMenuActivity extends Activity{

    private ListView lv_content;
    private static final String TAG = "TAGMainMenu";
    private ImageView iv_glide,iv_picasso;
    private TextView tv_debug;
    private Button btn_start;
    private PackageManager pm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mainmenu);

//        lv_content = (ListView) findViewById(R.id.lv_content);
//        List<String> data = Arrays.asList(Constant.data);
//        lv_content.setAdapter(new ListAdapter(data));
//        Log.i(TAG, "onCreate");

        setContentView(R.layout.layout_glide);
        iv_glide = (ImageView) findViewById(R.id.iv_glide);
        iv_picasso = (ImageView) findViewById(R.id.iv_picasso);

//        startActivityForResult(new Intent(this, MainActivity.class), 0);

//        Picasso.with(this.getApplicationContext())
//                .load("http://img0.imgtn.bdimg.com/it/u=1418485010,2783104222&fm=26&gp=0.jpg")
//                .resize(768,432)
//                .into(iv_picasso);

//        Picasso.with(this.getApplicationContext())
//                .load("http://img0.imgtn.bdimg.com/it/u=1418485010,2783104222&fm=26&gp=0.jpg")
//                .fit()
//                .centerCrop()
//                .into(iv_picasso);
//
//        Glide.with(this)
//                .load("http://img0.imgtn.bdimg.com/it/u=1418485010,2783104222&fm=26&gp=0.jpg")
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(iv_glide);

        Glide.with(this)
                .load("http://img0.imgtn.bdimg.com/it/u=1418485010,2783104222&fm=26&gp=0.jpg")
//                .crossFade(android.R.anim.fade_in, 300)//可以换成自定义的动画
                .thumbnail(0.1f)
                .into(iv_glide);

        tv_debug = (TextView) findViewById(R.id.tv_debug);
        tv_debug.setText(getApplicationMetaValue("UMENG_CHANNEL"));
        tv_debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("wang", "onClick");
            }
        });
        new Thread(){
            @Override
            public void run() {
                super.run();
                Log.i(TAG, "run  " + Thread.currentThread());
                new TestAsyncTask().execute();
            }
        }.start();

        pm = getPackageManager();
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.testsecond");
                intent.setDataAndType(Uri.parse("file/abc"), "image/jpeg");
                ResolveInfo info = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if(info != null){
                    Log.i(TAG, info.toString());
                    startActivity(intent);
                }

            }
        });

    }

    class TestAsyncTask extends AsyncTask<Void,Void,String>{
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute  " + Thread.currentThread());
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.i(TAG, "doInBackground  " + Thread.currentThread());
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i(TAG, "onPostExecute  " + Thread.currentThread());
            super.onPostExecute(s);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "requestCode" + requestCode + ",resultCode" + resultCode);
    }

    private String  getApplicationMetaValue(String name) {
        String value= "";
        try {
            ApplicationInfo appInfo =getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    class ListAdapter extends BaseAdapter{

        List<String> data;
        LayoutInflater mInflater;

        public ListAdapter(List<String> data){
            this.data = data;
            mInflater = LayoutInflater.from(MainMenuActivity.this);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item, null);
                holder.init(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_right.setText(data.get(position));
            Glide.with(MainMenuActivity.this).load(data.get(position))
                    .error(R.drawable.aa2)
                    .fallback(R.drawable.ic_launcher)
                    .placeholder(R.drawable.ic_launcher)
                    .thumbnail(0.1f).into(holder.iv_left);
            return convertView;
        }
    }

    class ViewHolder{
        ImageView iv_left;
        TextView tv_right;

        public void init(View root){
            if(root == null){
                return;
            }
            iv_left = (ImageView) root.findViewById(R.id.iv_left);
            tv_right = (TextView) root.findViewById(R.id.tv_right);
        }
    }
}
