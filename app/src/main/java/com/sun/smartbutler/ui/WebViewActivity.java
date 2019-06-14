package com.sun.smartbutler.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sun.smartbutler.R;
import com.sun.smartbutler.utils.LogUtil;

public class WebViewActivity extends BaseActivity {
    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    private void initView() {

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");
        LogUtil.d(title);
        LogUtil.d(url);

        //设置标题
        getSupportActionBar().setTitle(title);

        progressBar= (ProgressBar) findViewById(R.id.webview_progress);
        webView= (WebView) findViewById(R.id.mWeb_view);

        //支持JS
        webView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //接口回调 页面处理程序
        webView.setWebChromeClient(new mWbeViewClient());

        //设置目标网页仍然在当前网页显示
        webView.setWebViewClient(new WebViewClient());
        //加载网页
        webView.loadUrl(url);
    }

    public class mWbeViewClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress==100){
                progressBar.setVisibility(View.GONE);
            }
        }
    }
}
