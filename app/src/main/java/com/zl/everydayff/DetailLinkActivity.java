package com.zl.everydayff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zl.everydayff.util.ActivityManagerUtil;

/**
 * Created by Boom on 2017/6/24.
 *  显示网页连接 Activity
 */

public class DetailLinkActivity extends AppCompatActivity{

    private WebView mWebView;
    private WebSettings mWebSettings;
    private String mUrl;
    public final static String URL_KEY = "URL_KEY";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 自己单独统一管理activity
        ActivityManagerUtil.getInstance().addActivity(this);
        setContentView(R.layout.activity_detail_link);
        mWebView = (WebView) findViewById(R.id.web_view);
        //获取上个页面传递过来的url
        mUrl = getIntent().getStringExtra(URL_KEY);
        //2.设置WebView的一些参数
        mWebSettings = mWebView.getSettings();  //获取WebView的参数设置
        mWebSettings.setUseWideViewPort(false); //将图片调整到适合的webView大小
        mWebSettings.setJavaScriptEnabled(true);    //支持js
        mWebSettings.setLoadsImagesAutomatically(true); //支持自动加载图片

        //3.利用WebView直接加载网页链接
        //每次启动这个activity 所加载的Url网页路径肯定不一样的 ，Intent传值
        mWebView.loadUrl(mUrl);

/*        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });*/
    }
}
