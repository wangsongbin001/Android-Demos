package com.wang.mtoolsdemo.common.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.umeng.message.PushAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.wang.mtoolsdemo.R;
import com.wang.mtoolsdemo.common.util.DialogUtil;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

/**
 * Created by dell on 2017/12/12.
 */

public class ShareActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_share)
    TextView tvShare;
    private Activity mActivity;
    AlertDialog shareDialog;
    private UMShareAPI mShareAPI;

    private boolean islogining = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        mActivity = this;
        mShareAPI = UMShareAPI.get(this);
//        PushAgent.getInstance(this).onAppStart();
    }

    @OnClick({R.id.tv_share, R.id.btn_login})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_share) {
            if (shareDialog == null) {
                shareDialog = DialogUtil.showShareDialog(this, onClicker);
            } else {
                if (!shareDialog.isShowing()) {
                    shareDialog.show();
                }
            }
        }else if(view.getId() == R.id.btn_login){
            if (shareDialog == null) {
                shareDialog = DialogUtil.showShareDialog(this, onClicker);
            } else {
                if (!shareDialog.isShowing()) {
                    shareDialog.show();
                }
            }
        }
    }

    View.OnClickListener onClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_share_qq:
//                    if (mShareAPI.isInstall(mActivity, SHARE_MEDIA.QQ)) {
                    shareContent(SHARE_MEDIA.QQ);
                    break;
                case R.id.iv_share_qzone:
                    shareContent(SHARE_MEDIA.QZONE);
                    break;
                case R.id.iv_share_wechat:
                    shareContent(SHARE_MEDIA.WEIXIN);
                    break;
                case R.id.iv_share_wxcircle:
                    shareContent(SHARE_MEDIA.WEIXIN_CIRCLE);//传入平台
                    break;
                case R.id.iv_share_sina:
                    shareContent(SHARE_MEDIA.SINA);
                    break;
                case R.id.iv_share_copy:
                    break;
                case R.id.iv_share_cancel:
                    if (shareDialog != null && shareDialog.isShowing()) {
                        shareDialog.dismiss();
                    }
                    break;
            }
        }
    };

    /**
     * 调用分享接口
     *
     * @param platform
     */
    private void shareContent(SHARE_MEDIA platform) {
        if(islogining){
            mShareAPI.getPlatformInfo(mActivity, platform, authListener);
            return;
        }
        shareWeb(platform);
    }

    /**
     * 分享文本
     *
     * @param platform
     */
    private void shareText(SHARE_MEDIA platform) {
        new ShareAction(mActivity)
                .setPlatform(platform)//传入平台
                .withText("hello")//分享内容
                .setCallback(umShareListener)//回调监听器
                .share();
    }

    /**
     * 分享图片
     */
    private void shareImage(SHARE_MEDIA platform) {
        UMImage image = new UMImage(mActivity,
                "http://c.hiphotos.baidu.com/baike/pic/item/d009b3de9c82d158b62f49ef890a19d8bc3e423a.jpg");
        image.compressStyle = UMImage.CompressStyle.SCALE;
        image.compressFormat = Bitmap.CompressFormat.PNG;
        new ShareAction(ShareActivity.this)
                .setPlatform(platform)
                .withText("分享图片")
                .withMedia(image)
                .setCallback(umShareListener)
                .share();
    }

    private void shareWeb(SHARE_MEDIA platform) {
        UMImage image = new UMImage(mActivity,
                "http://c.hiphotos.baidu.com/baike/pic/item/d009b3de9c82d158b62f49ef890a19d8bc3e423a.jpg");
        image.compressStyle = UMImage.CompressStyle.SCALE;
        image.compressFormat = Bitmap.CompressFormat.PNG;

        UMWeb web = new UMWeb("https://weibo.com/u/5720728725?is_all=1");
        web.setTitle("This is music title");//标题
        web.setThumb(image);  //缩略图
        web.setDescription("#百度AI开发者大会四大猜想：会公开什么“法宝”" +
                "#百度正式宣布将于7月5日在北京国家会议中心举办Baidu Create 2017百度AI开发者大会");//描述

        new ShareAction(ShareActivity.this)
                .setPlatform(platform)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.i("wangsongbin", "data:" + data.toString());
            Toast.makeText(mActivity, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(mActivity, "失败：" + t.getMessage(),                                  Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(mActivity, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.i("wangsongbin", "onStart:" + platform);
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(mActivity, "成功了", Toast.LENGTH_LONG).show();
            Log.i("wangsongbin", "onResult:" + platform);
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(mActivity, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("wangsongbin", "onError:" + platform + ",t" + t.getMessage());
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mActivity, "取消了", Toast.LENGTH_LONG).show();
            Log.i("wangsongbin", "onError:" + platform);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        UMShareAPI.get(this).release();
    }
}
