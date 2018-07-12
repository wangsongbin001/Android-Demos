package com.example.songbinwang.liveinhand.video.entity;

import java.io.Serializable;

/**
 * Created by songbinwang on 2016/6/27.
 */
public class VideoEntity implements Serializable{

    private String resUrl;

    private String videoName;

    private long duration;

    private String videoId;

    private int playTo;

    public VideoEntity() {
    }

    public VideoEntity(String resUrl, String videoName, long duration, String videoId, int playTo) {
        this.resUrl = resUrl;
        this.videoName = videoName;
        this.duration = duration;
        this.videoId = videoId;
        this.playTo = playTo;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public int getPlayTo() {
        return playTo;
    }

    public void setPlayTo(int playTo) {
        this.playTo = playTo;
    }

    @Override
    public String toString() {
        return "VideoEntity{" +
                "resUrl='" + resUrl + '\'' +
                ", videoName='" + videoName + '\'' +
                ", duration=" + duration +
                ", videoId='" + videoId + '\'' +
                ", playTo=" + playTo +
                '}';
    }
}
