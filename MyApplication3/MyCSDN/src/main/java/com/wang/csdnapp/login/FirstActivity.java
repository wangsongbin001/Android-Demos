package com.wang.csdnapp.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.wang.csdnapp.R;
import com.wang.csdnapp.test.Images;
import com.wang.csdnapp.util.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SongbinWang on 2017/2/27.
 */

public class FirstActivity extends Activity {

    private GridView gv;
    ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        gv = (GridView) findViewById(R.id.gv_content);
        List<String> list = Arrays.asList(Images.imageThumbUrls);
        mAdapter = new ImageAdapter(this, list);
        gv.setAdapter(mAdapter);
    }

    class ImageAdapter extends BaseAdapter {

        private Context mContext;
        private List<String> mDatas;
        private LayoutInflater mInflater;

        ImageAdapter(Context context, List<String> mDatas) {
            this.mContext = context;
            if (mDatas != null) {
                this.mDatas = mDatas;
            } else {
                this.mDatas = new ArrayList<>();
            }
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mHolder;
            if (convertView == null) {
                mHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.gv_item, parent, false);
                mHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_content);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
            String url = mDatas.get(position);
            ImageLoader.getInstance(mContext).bindBitmap(url, mHolder.imageView, 100, 100);
            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
        }
    }

}
