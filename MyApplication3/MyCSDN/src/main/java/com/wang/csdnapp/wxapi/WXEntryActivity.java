package com.wang.csdnapp.wxapi;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wang.csdnapp.Constant;
import com.wang.csdnapp.R;
import com.wang.csdnapp.bean.SharedParam;
import com.wang.csdnapp.util.LogUtil;
import com.wang.csdnapp.util.ToastUtil;
import com.wang.csdnapp.util.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by songbinwang on 2017/6/19.
 * 用于辅助分享的activity
 * ***********微博分享****************
 * 1，实现接口WBShareCallback
 * 2，初始化Sharedhandler, 并注册app
 * 3，分享WeiboMultiMessage，
 * 4，处理回掉，重写onNewIntent
 * ***********************************
 * ***********WeChat分享**************
 * 1，实现接口IWXAPIEventHandler,方法onReq（请求响应），onResp(返回结果)
 * 必须新建your package.wxapi.WXEntryActivity，才可以（export = true）
 * 1，初始化微信api
 * 2，调用微信api
 * 3，重写onNewIntent将intent交由api.handleIntent(intent, this)处理;
 * ***********************************
 */

public class WXEntryActivity extends Activity implements WbShareCallback,IWXAPIEventHandler,IUiListener{
    private static final String TAG = "SharedActivity";

    private SharedParam params;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            params = (SharedParam) getIntent().getSerializableExtra("param");
        }catch (Exception e){

        }
        if(params != null){
            LogUtil.i(TAG, "params:" + params.toString());
            dealShare();
        }
    }

    private void dealShare(){
        if(params.getType() == SharedParam.TYPE.type_weixin
                || params.getType() == SharedParam.TYPE.type_wechat_circle){
            share2wechat();
        }else if(params.getType() == SharedParam.TYPE.type_qq
                || params.getType() == SharedParam.TYPE.type_qzone){
            share2qq();
        }else if(params.getType() == SharedParam.TYPE.type_weibo){
            share2weibo();
        }
    }

    /**
     * 微博分享
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(shareHandler != null){
            shareHandler.doResultIntent(intent, this);
            finish();
        }
        if(mWXApi != null){
            mWXApi.handleIntent(intent, this);
            finish();
        }
//        finish();
    }

    //微博分享start
    WbShareHandler shareHandler;
    private void share2weibo(){
        //出事化WBShareHandler
        shareHandler = new WbShareHandler(this);
        shareHandler.registerApp();
        //发送微博
        WeiboMultiMessage wbMsg = new WeiboMultiMessage();
        wbMsg.textObject = getTextObj();
        wbMsg.imageObject = getImageObj();
//        wbMsg.mediaObject = getWebpageObj();
        shareHandler.shareMessage(wbMsg, false);
    }

    /**
     * 创建文本消息对象。
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
//        textObject.text = getSharedText();
        StringBuilder sb = new StringBuilder();
        sb.append("#" + params.getTitle() + "#");
        sb.append(params.getTxt());
        sb.append("" + params.getAirticleUrl());
        textObject.text = sb.toString();
        //没看出啥用
//        textObject.title = params.getTitle();
//        textObject.actionUrl = "http://www.baidu.com";
        return textObject;
    }

    /**
     * 创建图片消息对象。
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = Util.getBitmapFromCache(this, params.getImgUrl());
        if(bitmap == null){
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        }
        imageObject.setImageObject(bitmap);
        return imageObject;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aa2);
//        imageObject.setImageObject(bitmap);
//        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        //希望标题后面换行
        mediaObject.title = params.getTitle() + "";
        mediaObject.description = params.getTxt();
        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aa2);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = params.getAirticleUrl();
        return mediaObject;
//        mediaObject.identify = Utility.generateGUID();
//        mediaObject.title ="测试title";
//        mediaObject.description = "测试描述";
////        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);
//        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
////        mediaObject.setThumbImage(bitmap);
//        mediaObject.actionUrl = "http://news.sina.com.cn/c/2013-10-22/021928494669.shtml";
//        mediaObject.defaultText = "Webpage 默认文案";
//        return mediaObject;
    }
    /**
     * 获取分享的文本模板。
     */
//    private String getSharedText() {
//        int formatId = R.string.weibosdk_demo_share_text_template;
//        String format = getString(formatId);
//        String text = format;
//        if (mTextCheckbox.isChecked() || mImageCheckbox.isChecked()) {
//            //text = "@大屁老师，这是一个很漂亮的小狗，朕甚是喜欢-_-!! http://weibo.com/p/1005052052202067/home?from=page_100505&mod=TAB&is_all=1#place";
//            text = "70岁富翁拥有两座城堡 抛弃伴侣想找年轻女子生育继承人#UC头条# http://s4.uczzd.cn/ucnews/news?app=ucnews-iflow&aid=17106278003054546318&cid=100&zzd_from=ucnews-iflow&uc_param_str=dndseiwifrvesvntgipf&rd_type=share&pagetype=share&btifl=100&sdkdeep=2&sdksid=55a8f9d3-223a-d5a7-812c-c3af12bb6430&sdkoriginal=55a8f9d3-223a-d5a7-812c-c3af12bb6430";
//        }
//        return text;
//    }

    //微博分享结果处理start
    @Override
    public void onWbShareSuccess() {
        ToastUtil.toast("分享成功");
    }

    @Override
    public void onWbShareCancel() {
        ToastUtil.toast("分享取消");
    }

    @Override
    public void onWbShareFail() {
        ToastUtil.toast("分享失败");
    }
    //微博分享结果处理end
    //微博分享end

    //微信分享start**********************************************
    public IWXAPI mWXApi;
    //分享到微信回话
    private void share2wechat(){
        if(mWXApi == null){
            mWXApi = WXAPIFactory.createWXAPI(this, Constant.WeiXin_AppId, true);
            mWXApi.registerApp(Constant.WeiXin_AppId);
        }
        //初始化一个WXWebpageObject，定义url
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = params.getAirticleUrl();

        //利用WXWebpageObject生成一个WXMediaMessage
        WXMediaMessage wxMediaMessage = new WXMediaMessage(webpageObject);
        wxMediaMessage.title = params.getTitle();
        wxMediaMessage.description = params.getTxt();
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.share_thumb);
        wxMediaMessage.thumbData = Util.bmpToByteArray(thumb, true);
//        Bitmap bitmap = Util.getBitmapFromCache(this, params.getImgUrl());
//        if(bitmap == null){
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
//        }
//        Bitmap thumbBitmap = bitmap.createScaledBitmap(bitmap, 150, 150, true);
//        wxMediaMessage.thumbData = Util.bmpToByteArray(bitmap, true);
//        bitmap.recycle();

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "transaction: share type + webpage";
        req.message = wxMediaMessage;//决定着文本，图片或者webpage
        if(params.getType() == SharedParam.TYPE.type_weixin){
            req.scene = SendMessageToWX.Req.WXSceneSession;//分享到回话
        }else if(params.getType() == SharedParam.TYPE.type_wechat_circle){
            req.scene = SendMessageToWX.Req.WXSceneTimeline;//分享到回话
        }else{
            req.scene = SendMessageToWX.Req.WXSceneFavorite;//分享到收藏夹
        }
        boolean result = mWXApi.sendReq(req);
        LogUtil.i(TAG, "result:" + result);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        String result = "发送请求";
        ToastUtil.toast("" + result);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        String result = "null";
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "分享取消";
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = "不支持操作";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                break;
            default:
                result = "发送返回";
                break;
        }
        ToastUtil.toast("" + result);
    }
    //微信分享end

    //qq分享start
    private Tencent mTencent;
    private void share2qq(){
        if(mTencent == null){
            mTencent = Tencent.createInstance(Constant.QQ_API_ID, this.getApplicationContext());
        }
        Bundle bundle = new Bundle();
        //消息被点击后的跳转URL
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, params.getAirticleUrl());
        //分享标题
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, params.getTitle());
        //分享图片的url
        if(!TextUtils.isEmpty(params.getImgUrl())){
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, params.getImgUrl());
        }else{
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
                    "http://img.25pp.com/uploadfile/soft/images/2013/0625/20130625085103542.jpg");
        }
        //分享内容的摘要,最长50个字
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, params.getTxt());
        //应用名
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "CSDNApp");

        if(SharedParam.TYPE.type_qq == params.getType()){
//            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
            mTencent.shareToQQ(this, bundle, this);
        }else if(SharedParam.TYPE.type_qzone == params.getType()){
            //Qzone分享必须设置缩略图才行
            ArrayList<String> images = new ArrayList<String>();
            images.add("http://img.25pp.com/uploadfile/soft/images/2013/0625/20130625085103542.jpg");
            bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, images);
            mTencent.shareToQzone(this, bundle, this);
        }
    }

    private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mTencent != null){
            mTencent.onActivityResultData(requestCode, resultCode, data, this);
            finish();
        }
    }

    @Override
    public void onComplete(Object o) {
        ToastUtil.toast("分享成功：" + o.toString());
    }

    @Override
    public void onError(UiError uiError) {
        ToastUtil.toast("分享失败");
    }

    @Override
    public void onCancel() {
        ToastUtil.toast("分享取消");
    }
    //qq分享end

}
