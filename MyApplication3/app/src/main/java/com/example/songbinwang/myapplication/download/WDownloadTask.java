package com.example.songbinwang.myapplication.download;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by SongbinWang on 2017/8/8.
 */

public class WDownloadTask {

    /**
     * 设置一个单例
     */
    static WDownloadTask mWDownloadTask;
    public static WDownloadTask getInstance(){
        if(mWDownloadTask == null){
            synchronized (WDownloadTask.class){
                if(mWDownloadTask == null){
                    mWDownloadTask = new WDownloadTask();
                }
            }
        }
        return mWDownloadTask;
    }

    public ExecutorService mSchulder = Executors.newFixedThreadPool(3);
    private DownloadTask[] tasks  = new DownloadTask[3];
    public void schulde(){

        mSchulder.execute(tasks[1]);
    }

    static class DownloadTask implements Runnable{
        @Override
        public void run() {

        }
    };

    /**
     * 资源url与下载的保存路径mnt/sdcaard/path
     * @param url
     * @param path
     */
    public void download(String url, String path){

    }

    public void download(String url, String path, DownloadListener mListener){

    }

    /**
     * 下载结果的返回
     */
    private DownloadListener mListener;

    public void setDownloadListener(DownloadListener mListener){
        this.mListener = mListener;
    }

    public static interface DownloadListener{
        //下载失败
        void onFailure(int errorCode);
        //下载成功
        void onSuccess();
        //下载中，百分比
        void onProgress(float percent);
    }

}
