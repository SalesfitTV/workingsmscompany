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

public class offers_lp extends AppCompatActivity {

    private static String blah = "com.example.application.example.blah";
    public static String getData() {
        return blah;
    }

    Button btnLoadURL;
    EditText etLoadURL;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_lp);


        webView = findViewById(R.id.webView);


        webView.setWebViewClient(new MyWebViewClient());
        WebSettings browserSetting = webView.getSettings();
        browserSetting.setJavaScriptEnabled(true);
        loadURL("https://sftv.app/sftvallproducts/offers");


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
                String offers_share_url = view.getUrl();
                String collectedurl = offers_share_url.toString();
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
        WebView browser = (WebView) findViewById(R.id.webView);
        if (browser.canGoBack()) {
            browser.goBack();
        } else {
            super.onBackPressed();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }



}
