package com.example.songbinwang.liveinhand.video.biz.impl;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.songbinwang.liveinhand.uitls.LogUtils;
import com.example.songbinwang.liveinhand.uitls.StringUtils;
import com.example.songbinwang.liveinhand.video.biz.IVideoBiz;
import com.example.songbinwang.liveinhand.video.biz.OnAnalizeVideosLisenter;
import com.example.songbinwang.liveinhand.video.entity.VideoEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by songbinwang on 2016/6/27.
 */
public class VideoBiz implements IVideoBiz {

    private static final String VideoOrder = "date_added desc";

    @Override
    public List<VideoEntity> analizeVideos(OnAnalizeVideosLisenter onAnalizeVideosLisenter, ContentResolver contentResolver) {
        Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, VideoOrder);
        List<VideoEntity> videoList = null;
        if (cursor != null && cursor.getCount() != 0) {
            videoList = new ArrayList<VideoEntity>();
            cursor.moveToFirst();
            do{
                String mine = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
                if(mine != null && mine.startsWith("video")){
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                    LogUtils.i("path:"+path);
                    File file = new File(path);
                    if (!file.exists()) {
                        continue;
                    }
                    String videoName = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
                    String strDuration = cursor.getString(cursor.getColumnIndex("duration"));
                    long duration = 0;
                    if(StringUtils.canParseLong(strDuration)){
                        duration = Long.parseLong(strDuration);
                    }
                    String videoId = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                    VideoEntity video = new VideoEntity(path, videoName, duration, videoId, 0);
                    videoList.add(video);
                }
            }while(cursor.moveToNext());
            cursor.close();
        }else{
            if(cursor != null){
                cursor.close();
            }
        }
        return videoList;
    }
}
