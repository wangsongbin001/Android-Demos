package com.example.songbinwang.liveinhand.video.presenter;

import android.content.ContentResolver;
import android.os.AsyncTask;

import com.example.songbinwang.liveinhand.uitls.LogUtils;
import com.example.songbinwang.liveinhand.video.biz.IVideoBiz;
import com.example.songbinwang.liveinhand.video.biz.impl.VideoBiz;
import com.example.songbinwang.liveinhand.video.entity.VideoEntity;
import com.example.songbinwang.liveinhand.video.view.IVideoListView;

import java.util.List;

/**
 * Created by songbinwang on 2016/6/27.
 */
public class VideoListPresenter {

    IVideoListView mIVideoListView = null;
    IVideoBiz videoBiz = null;
    private GetVideosTask getVideosTask;

    enum e{

    }

    public VideoListPresenter(IVideoListView mIVideoListView) {
        this.mIVideoListView = mIVideoListView;
        videoBiz = new VideoBiz();
    }

    public void analizeSystemVideos(ContentResolver cr){
        if (getVideosTask != null && getVideosTask.getStatus() == AsyncTask.Status.PENDING) {
            getVideosTask.cancel(true);
        }
        getVideosTask = null;
        getVideosTask = new GetVideosTask();
        getVideosTask.execute(cr);
    };

    class GetVideosTask extends AsyncTask<ContentResolver, Void, List<VideoEntity>>{

        @Override
        protected List<VideoEntity> doInBackground(ContentResolver... params) {
            try {
                return videoBiz.analizeVideos(null, params[0]);
            }catch (Exception e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<VideoEntity> videoEntities) {
            LogUtils.i("wangpresenter", videoEntities == null ? "null": videoEntities.toString());
            super.onPostExecute(videoEntities);
            mIVideoListView.updateListView(videoEntities);
        }
    }


}
