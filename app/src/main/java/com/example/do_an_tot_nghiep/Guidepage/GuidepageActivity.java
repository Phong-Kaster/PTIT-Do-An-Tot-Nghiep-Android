package com.example.do_an_tot_nghiep.Guidepage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.R;

/**
 * @author Phong-Kaster
 * @since 23-11-2022
 * Guide page activity
 */
public class GuidepageActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private WebView wvwLocation;
    private AppCompatButton btnOpenWithGoogleMap;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidepage);
        setupComponent();
        setupEvent();
    }

    private void setupComponent()
    {
        btnBack = findViewById(R.id.btnBack);
        wvwLocation = findViewById(R.id.wvwDescription);
        btnOpenWithGoogleMap = findViewById(R.id.btnOpenWithGoogleMap);

        GlobalVariable globalVariable = (GlobalVariable) this.getApplication();
        sharedPreferences = this.getApplication()
                .getSharedPreferences(globalVariable.getSharedReferenceKey(), MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tooltip.setLocale(this, sharedPreferences);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupEvent()
    {
        /*BUTTON BACK*/
        btnBack.setOnClickListener(view-> finish());

        /*GOOGLE MAP*/
        String location =
            "<html>\n" +
            "   <body>\n" +
                    "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.3614347512976!2d106.76861713956941!3d10.783605143395203!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x317525d4fb4d7abb%3A0xed06665f8a047451!2sLe%20Van%20Thinh%20Hospital!5e0!3m2!1sen!2s!4v1669215482921!5m2!1sen!2s\" " +
                        "width=\"600\" height=\"450\" \n" +
                        "style=\"border:0;\" allowfullscreen=\"\"\n" +
                        "loading=\"lazy\" \n" +
                        "referrerpolicy=\"no-referrer-when-downgrade\">\n" +
                    "</iframe>\n" +
            "   </body>\n" +
            "</html> ";

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage( this.getString(R.string.loading));
        progressDialog.setCancelable(false);

        wvwLocation.requestFocus();
        wvwLocation.getSettings().setJavaScriptEnabled(true);
        wvwLocation.getSettings().setGeolocationEnabled(true);
        wvwLocation.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });
        wvwLocation.loadDataWithBaseURL(null, location, "text/HTML", "UTF-8", null);
        /*end GOOGLE MAP*/


        /*BUTTON OPEN WITH GOOGLE MAP*/
        btnOpenWithGoogleMap.setOnClickListener(view->{
            Uri uri = Uri.parse("https://www.google.com/maps?ll=10.782762,106.769197&z=16&t=m&hl=en&gl=US&mapclient=embed&cid=17079451197372658769");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            finish();
        });
    }
}