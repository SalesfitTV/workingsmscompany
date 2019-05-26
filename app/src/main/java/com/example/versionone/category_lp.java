package com.example.versionone;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class category_lp extends AppCompatActivity {
    private static String category_share_url = "com.example.application.example.blah";
    public static String getData() {
        return category_share_url;
    }
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_lp);

        webView = findViewById(R.id.category_webview);


        webView.setWebViewClient(new MyWebViewClient());
        WebSettings browserSetting = webView.getSettings();
        browserSetting.setJavaScriptEnabled(true);
        loadURL("https://sftv.app/sftvallproducts/products");


    }


    private void loadURL(String url) {
        webView.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {




        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            if (url.startsWith("http://open/smsactivity/")) {
                Intent intent1 = new Intent(getApplicationContext(), choose_send_option.class);
                startActivity(intent1);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (url.startsWith("http://open/smsactivity/")) {
                category_share_url = view.getUrl();
                String collectedurl = category_share_url.toString();
                GlobalClass globalClass = (GlobalClass) getApplicationContext();
                globalClass.setUrlcolleccted(collectedurl);

            }
        }

        @Override public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            String url = view.getUrl();
            if(url.startsWith("https://sftv.app")){
                view.loadUrl("file:///android_asset/error.html");
            }
        }
    }


    @Override
    public void onBackPressed() {
        WebView browser = (WebView) findViewById(R.id.category_webview);
        if (browser.canGoBack()) {
            browser.goBack();
        } else {
            super.onBackPressed();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

    }
}
