package com.example.songbinwang.liveinhand.video;

import android.app.Activity;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.songbinwang.liveinhand.R;
import com.example.songbinwang.liveinhand.uitls.LogUtils;
import com.example.songbinwang.liveinhand.uitls.VideoImageLoaderHelper;
import com.example.songbinwang.liveinhand.video.adapter.VideoListAdapter;
import com.example.songbinwang.liveinhand.video.entity.VideoEntity;
import com.example.songbinwang.liveinhand.video.presenter.VideoListPresenter;
import com.example.songbinwang.liveinhand.video.view.IVideoListView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by songbinwang on 2016/6/27.
 */
public class VideoListActivity extends AppCompatActivity implements IVideoListView {

    private ListView lv_video_list;
    private ImageView iv_more;
    private Activity mActivity;
    private VideoListPresenter mPresnter = new VideoListPresenter(this);
    private VideoListAdapter mAdapter;
    private static final String TAG = "wangvideolist";
    private PopupWindow popupWindow;
    private List<VideoEntity> videoList;

    private ContentObserver videoObserver = new ContentObserver(new Handler()) {

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            LogUtils.i(TAG, "onChange(boolean selfChange)");
            mPresnter.analizeSystemVideos(mActivity.getContentResolver());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list);
        mActivity = this;
        VideoImageLoaderHelper.createCache();
        initView();
        registerListener();
        registerContentObserver();
        mPresnter.analizeSystemVideos(mActivity.getContentResolver());
    }

    private void initView() {
        lv_video_list = (ListView) findViewById(R.id.lv_video_list);
        iv_more = (ImageView) findViewById(R.id.iv_more);
    }

    private void registerListener() {
        lv_video_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(mActivity, VideoPlayerActivity.class);
                Intent intent = new Intent(mActivity, VideoPlayerNewActivity.class);
                intent.putExtra("video", (Serializable)videoList.get(position));
                startActivity(intent);
            }
        });

        iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i(TAG, "onClick(View v)");
                initPopWindow();
                popupWindow.showAsDropDown(iv_more);
            }
        });
    }

    private void registerContentObserver() {
        mActivity.getContentResolver().registerContentObserver(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, true, videoObserver);
    }

    private void unregisterContentObserver() {
        if (videoObserver != null) {
            mActivity.getContentResolver().unregisterContentObserver(videoObserver);
            videoObserver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterContentObserver();
    }

    @Override
    public void updateListView(List<VideoEntity> videoEntityList) {
        if (videoEntityList == null) {
            return;
        }
        this.videoList = videoEntityList;
        if (mAdapter == null) {
            mAdapter = new VideoListAdapter(mActivity, videoEntityList);
            lv_video_list.setAdapter(mAdapter);
        } else {
            mAdapter.update(videoEntityList);
        }
    }

    public void initPopWindow() {
        if (popupWindow == null) {
            View content = LayoutInflater.from(mActivity).inflate(R.layout.video_popup_layout, null);
            popupWindow = new PopupWindow(content, (int) getResources().getDimension(R.dimen.video_window_width),
                    (int) getResources().getDimension(R.dimen.video_window_height), true);
            ColorDrawable colorDrawable = new ColorDrawable(Color.WHITE);
            popupWindow.setBackgroundDrawable(colorDrawable);
        }

    }
}
