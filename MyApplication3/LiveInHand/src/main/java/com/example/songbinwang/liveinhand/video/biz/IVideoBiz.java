package com.example.songbinwang.liveinhand.video.biz;

import android.content.ContentResolver;

import com.example.songbinwang.liveinhand.video.entity.VideoEntity;

import java.util.List;

/**
 * Created by songbinwang on 2016/6/27.
 */
public interface IVideoBiz {

    public List<VideoEntity> analizeVideos(OnAnalizeVideosLisenter onAnalizeVideosLisenter, ContentResolver contentResolver);
}
