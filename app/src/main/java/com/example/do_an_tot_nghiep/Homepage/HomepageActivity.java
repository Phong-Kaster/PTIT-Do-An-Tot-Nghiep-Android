package com.example.do_an_tot_nghiep.Homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.R;

public class HomepageActivity extends AppCompatActivity {

    private String TAG = "Homepage Activity";
    private GlobalVariable globalVariable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        globalVariable = (GlobalVariable) this.getApplication();

        Log.d(TAG, "HOME PAGE ACTIVITY");
        Log.d(TAG, "accessToken: " + globalVariable.getAccessToken());
        Log.d(TAG, "phone: " + globalVariable.getAuthUser().getPhone());
    }
}