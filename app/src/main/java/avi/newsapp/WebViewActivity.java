package avi.newsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.RecoverySystem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressDialog progressDialog;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        webView = (WebView) findViewById(R.id.webview);
        Intent i=getIntent();
        url = i.getStringExtra("urlart");
        webView.getSettings().setJavaScriptEnabled(true);
        progressDialog.dismiss();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }
}
