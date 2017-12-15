package com.wang.mtoolsdemo.common.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.wang.mtoolsdemo.R;
import com.wang.mtoolsdemo.common.util.LogUtil;
import com.wang.mtoolsdemo.common.util.NetUtil;
import com.wang.mtoolsdemo.common.util.VerifyUtil;
import com.wang.mtoolsdemo.common.view.CommonTitlebarBuilder;
import com.wang.mtoolsdemo.common.view.WebViewEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by dell on 2017/12/1
 * 1，加载loadUrl.
 * 2，同步onResume,onPause
 * 3，WebView的销毁要先移除，再销毁
 * 4，缓存处理，
 * 5，WebSetting webVie几乎所有的属性配置
 * 6，设置监听。
 * 7，交互：
 * Android调用Html
 * 1.1，webView.loadUrl("javascript:callJS()");
 * 1.2，evaluateJavascript
 * JS通过WebView调用Android
 * 2.1，webView.addJavascriptInterface(new Android2Js(), "test"); 将Android方法映射给JS
 * 2.2，重写shouldOverrideUrlLoading,解析url, JS调用document.location = "js://webview?arg1=111&arg2=222"
 * 2.3，重写ChromeClient的 onJsPrompt
 */

public class HtmlActivity extends AppCompatActivity {

    public static final String KEY_URL = "key_url";
    public static final String KEY_TITLE = "key_title";

    private ProgressBar progressBar;
    private WebViewEx webView;
    private LinearLayout ll_container;
    WebSettings webSettings;
    private Activity mActivity;
    private String url = "http://10.138.60.131:10000/paydayloan.html#/noQuota/46190/2/B";
    private String title;
    CommonTitlebarBuilder mBuilder;
    private ArrayList<String> searchHistory = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        mActivity = this;

//        if(!isRightUrl()){
//            ToastUtil.showLong(mActivity.getApplicationContext(), "URL不正确！");
//            return;
//        }
        initTitleBar();
        initViews();
    }

    private boolean isRightUrl(){
        url = getIntent().getStringExtra(KEY_URL);
        title = getIntent().getStringExtra(KEY_TITLE);
        if(VerifyUtil.isUrl(url)){
            return true;
        }
        return false;
    }

    private void initTitleBar() {
        mBuilder = new CommonTitlebarBuilder(this, R.id.titlebar)
                .withBackIcon()
                .setMiddleTitleText("" + title);
    }

    private void initViews() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new WebViewEx(mActivity);
        webView.setLayoutParams(params);
        ll_container.addView(webView);

        setupWebView();
//        webView.loadUrl(url);
        webView.loadUrl("file:///android_asset/test_1.html");
    }

    private void setupWebView() {
        webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //缓存
        if (NetUtil.isConnected(this)) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//有网，根据cache-controll
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //使用本地缓存
        }

        //其他细节操作
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webView.setWebViewClient(new WebViewEx.WebViewClientEx() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.i("wangsongbin", "url:" + url);
                searchHistory.add(url);
                if (TextUtils.isEmpty(url)) {
                    return true;
                }
                Uri uri = Uri.parse(url);
                if (uri.getScheme().equals("js") && uri.getAuthority().equals("webview")) {
                    HashMap<String, String> params = new HashMap<>();
                    Set<String> collection = uri.getQueryParameterNames();
                    Iterator<String> iterator = collection.iterator();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        String value = uri.getQueryParameter(key);
                        params.put(key, value);
                    }
                    LogUtil.i("wangsongbin", "url:" + params.toString());
                } else {
                    webView.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //开始加载
                LogUtil.i("wangsongbin", "onPageStart");
                if(progressBar != null){
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //结束加载
                LogUtil.i("wangsongbin", "onPageEnd");
                if(progressBar != null){
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
//                if(error.getErrorCode() == 404){//
//                }
            }
        });
        webView.setWebChromeClient(new WebViewEx.WebChromeClientEx() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                LogUtil.i("wangsongbin", "progress:" + newProgress);
                if(progressBar != null){
                    progressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if(TextUtils.isEmpty(title)){
                    mBuilder.setMiddleTitleText(title);
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(HtmlActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                LogUtil.i("wangsongbin", "message:" + message);
                // 调用prompt（）
                Uri uri = Uri.parse(message);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                LogUtil.i("wangsongbin", "url:" + url);
                if (uri.getScheme().equals("js")) {

                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {
                        //
                        // 执行JS所需要调用的逻辑
                        System.out.println("js调用了Android的方法");
                        // 可以在协议上带有参数并传递到Android上
                        HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();

                        //参数result:代表消息框的返回值(输入值)
                        result.confirm("js调用了Android的方法成功啦");
                    }
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

        });
        webView.addJavascriptInterface(new JSInterface(), "Android");// "Android");//AndroidtoJS类对象映射到js的test对象
        webView.setVerticalScrollBarEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //webView.resumeTimers(); 针对所有的web，onResume只针对当前
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
            searchHistory.clear();
            searchHistory = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()
                && searchHistory.size() > 0) {
            searchHistory.remove(searchHistory.size() - 1);
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
