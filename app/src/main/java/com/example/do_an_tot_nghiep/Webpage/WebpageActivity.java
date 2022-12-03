package com.example.do_an_tot_nghiep.Webpage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an_tot_nghiep.R;

public class WebpageActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);

        String url = getIntent().getStringExtra("url");

        WebView webView = findViewById(R.id.webView);
        webView.requestFocus();
        webView.getSettings().setLightTouchEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);


        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view->finish());
    }
}