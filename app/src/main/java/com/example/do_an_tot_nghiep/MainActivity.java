package com.example.do_an_tot_nghiep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.do_an_tot_nghiep.Loginpage.LoginActivity;

public class MainActivity extends AppCompatActivity {

    /**
     * @author Phong-Kaster
     * @since 15-11-2022
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*delay 1s before starting HomeActivity*/
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }
}