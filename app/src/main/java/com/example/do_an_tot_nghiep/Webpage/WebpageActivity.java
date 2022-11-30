package com.example.do_an_tot_nghiep.Webpage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.do_an_tot_nghiep.R;

public class WebpageActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);

        String url = getIntent().getStringExtra("url");

        WebView webView = findViewById(R.id.webView);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage( this.getString(R.string.loading));
        progressDialog.setCancelable(false);

        webView.requestFocus();
        webView.getSettings().setLightTouchEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}