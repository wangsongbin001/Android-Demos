package com.example.songbinwang.liveinhand.video.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.songbinwang.liveinhand.R;
import com.example.songbinwang.liveinhand.uitls.LogUtils;
import com.example.songbinwang.liveinhand.uitls.StringUtils;
import com.example.songbinwang.liveinhand.uitls.VideoImageLoaderHelper;
import com.example.songbinwang.liveinhand.video.entity.VideoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songbinwang on 2016/6/27.
 */
public class VideoListAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<VideoEntity> videoEntityList;
    private Context mContext;

    public VideoListAdapter(Context mContext , List<VideoEntity> videoEntityList) {
        if (videoEntityList == null) {
            this.videoEntityList = new ArrayList<VideoEntity>();
        }else{
            this.videoEntityList = videoEntityList;
        }
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void update(List<VideoEntity> videoEntityList){
        if(videoEntityList != null){
            this.videoEntityList.clear();
            this.videoEntityList.addAll(videoEntityList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return videoEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.video_list_item ,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = ViewHolder.getFromView(convertView);
        }
        initView(viewHolder, position);
        return convertView;
    }

    private void initView(ViewHolder viewHolder, int position){
        VideoEntity video = videoEntityList.get(position);
        viewHolder.tv_name.setText(video.getVideoName());
        viewHolder.tv_duration.setText(StringUtils.getFormatDuration(video.getDuration()));
        Bitmap bm = VideoImageLoaderHelper.getBitmapFromLruCache(video.getResUrl());
        if(bm == null){
            viewHolder.iv_account.setTag(video.getResUrl());
            new BitmapTask(viewHolder.iv_account).execute(video);
        }else{
            viewHolder.iv_account.setImageBitmap(bm);
        }
        if (position == videoEntityList.size() - 1) {
            viewHolder.iv_divider.setPadding(0, 0, 0 ,0);
        }else{
            viewHolder.iv_divider.setPadding((int) mContext.getResources().getDimension(R.dimen.video_list_paddingleft), 0,
                    (int) mContext.getResources().getDimension(R.dimen.video_list_paddingright) ,0);
        }

    }

    public static class ViewHolder{
        ImageView iv_account;
        TextView tv_name, tv_duration;
        CheckBox cb_selected;
        ImageView iv_divider;

        public ViewHolder(View view) {
            iv_account = (ImageView) view.findViewById(R.id.iv_account);
            tv_name = (TextView) view.findViewById(R.id.tv_video_name);
            tv_duration = (TextView) view.findViewById(R.id.tv_video_duration);
            cb_selected = (CheckBox) view.findViewById(R.id.cb_selected);
            iv_divider = (ImageView) view.findViewById(R.id.iv_divider);
        }

        public static ViewHolder getFromView(View view){
            Object tag = view.getTag();
            if(tag instanceof ViewHolder){
                return (ViewHolder) tag;
            }
            return new ViewHolder(view);
        }
    }

    class BitmapTask extends AsyncTask<VideoEntity, Void, Bitmap>{

        ImageView iv_account;
        String resUrl;

        BitmapTask(ImageView iv_account){
            this.iv_account = iv_account;
        }

        @Override
        protected Bitmap doInBackground(VideoEntity... params) {
            VideoEntity video = params[0];
            resUrl = video.getResUrl();
            Bitmap bitmap = VideoImageLoaderHelper.getBitmapFromLruCache(resUrl);
            if(bitmap == null){
                LogUtils.i("get bitmap for key : "+ resUrl+" -start");
                bitmap = MediaStore.Video.Thumbnails.getThumbnail(mContext.getContentResolver(), Long.parseLong(video.getVideoId()),MediaStore.Video.Thumbnails.MINI_KIND, null);
                LogUtils.i("get bitmap for key : "+ resUrl+" -end");
                if (bitmap != null) {
                    VideoImageLoaderHelper.addBitmapToLruCache(resUrl, bitmap);
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (iv_account != null && bitmap != null) {
                String tag = (String) iv_account.getTag();
                if (resUrl.equals(tag)) {
                    iv_account.setImageBitmap(bitmap);
                }
            }
        }
    }
}
