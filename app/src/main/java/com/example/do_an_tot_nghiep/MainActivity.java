package com.example.do_an_tot_nghiep;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.Notification;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.Homepage.HomepageActivity;
import com.example.do_an_tot_nghiep.Loginpage.LoginActivity;
import com.example.do_an_tot_nghiep.Model.User;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MAIN ACTIVITY";
    private SharedPreferences sharedPreferences;
    private GlobalVariable globalVariable;
    private Dialog dialog;

    /**
     * @author Phong-Kaster
     * @since 15-11-2022
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*Step 0 - declare sharedPreferences & globalVariable*/
        globalVariable = (GlobalVariable)this.getApplication();
        sharedPreferences = this.getApplication()
                .getSharedPreferences(globalVariable.getSharedReferenceKey(), MODE_PRIVATE);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        dialog = new Dialog(this);


        //If we wanna use notification on Android 8 or higher, this function must be run
        Notification notification = new Notification(this);
        notification.createChannel();


        /*Step 1 - does the application connect to Internet? - NO, close the application*/
        boolean isConnected = isInternetAvailable();
        if( !isConnected )
        {
            dialog.announce();
            dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
            dialog.btnOK.setOnClickListener(view->{
                dialog.close();
                finish();
            });
            return;
        }


        /*Step 2 - is dark mode turned on?*/
        int value = sharedPreferences.getInt("darkMode", 1);
        AppCompatDelegate.setDefaultNightMode(value);


        /*Step 3 - what is the language of application?
        * Find onResume to watch*/


        /*Step 4 - is AccessToken null?*/
        String accessToken = sharedPreferences.getString("accessToken", null);
        System.out.println(TAG);
        System.out.println(accessToken);
        if(accessToken != null)
        {
            /*global variable chi hoat dong trong phien lam viec nen phai gan lai accessToken cho no*/
            globalVariable.setAccessToken(accessToken);

            /*cai dat header voi yeu cau doc thong tin ca nhan cua mot benh nhan*/
            Map<String, String> headers = globalVariable.getHeaders();

            /*gui yeu cau doc thong tin benh nhan*/
            viewModel.readPersonalInformation(headers);

            /*lang nghe phan hoi*/
            viewModel.getResponse().observe(this, response->{
                try
                {
                    int result = response.getResult();
                    /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                    if( result == 1)
                    {
                        /*cap nhat thong tin nguoi dung*/
                        User user = response.getData();
                        globalVariable.setAuthUser( user );

                        /*bat dau vao trang chu*/
                        Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    /*result == 0 => thong bao va cho dang nhap lai*/
                    if( result == 0)
                    {
                        System.out.println(TAG);
                        System.out.println("result: " + result);
                        System.out.println("msg: " + response.getMsg());
                        sharedPreferences.edit().putString("accessToken",null).apply();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
                catch(Exception ex)
                {
                    /*Neu truy van lau qua ma khong nhan duoc phan hoi thi cung dong ung dung*/
                    System.out.println(TAG + "- exception: " + ex.getMessage());
                    dialog.announce();
                    dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                    dialog.btnOK.setOnClickListener(view->{
                        dialog.close();
                        finish();
                    });
                }
            });

        }
        else
        {
            /*delay 1s before starting HomeActivity*/
            Handler handler = new Handler(Looper.myLooper());
            handler.postDelayed(() -> {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            },1000);
        }
    }/*end OnCreate*/


    @Override
    protected void onResume() {
        super.onResume();
        Tooltip.setLocale(this, sharedPreferences);
    }

    /**
     * @author Phong-Kaster
     * @since 16-11-2022
     * @return boolean
     * true if we are connecting with Internet
     * false if we aren't
     */
    public boolean isInternetAvailable() {
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        return connected;
    }
}