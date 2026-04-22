package com.police.pass;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);
        WebSettings webSettings = webView.getSettings();

        // ========== 核心：全量强制禁用所有缓存，彻底根治二次打开时间不显示BUG ==========
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAppCacheEnabled(false);
        webSettings.setDatabaseEnabled(false);
        webView.clearHistory();
        webView.clearCache(true);

        // ========== 纯本地离线，完全拦截所有网络请求 ==========
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowNetworkAccess(false);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });

        // 加载你仓库内的本地HTML页面
        webView.loadUrl("file:///android_asset/index.html");
    }

    // 系统原生返回键功能
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
