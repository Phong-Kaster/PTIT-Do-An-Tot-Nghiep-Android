package com.example.do_an_tot_nghiep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Homepage.HomepageActivity;
import com.example.do_an_tot_nghiep.Loginpage.LoginActivity;
import com.example.do_an_tot_nghiep.Model.User;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MAIN ACTIVITY";
    private SharedPreferences sharedPreferences;
    private GlobalVariable globalVariable;
    private Dialog dialog;
    private MainViewModel viewModel;

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
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        dialog = new Dialog(this);


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


        /*Step 2 - is AccessToken null?*/
        String accessToken = sharedPreferences.getString("accessToken", null);
        if(accessToken != null)
        {
            /*global variable chi hoat dong trong phien lam viec nen phai gan lai accessToken cho no*/
            globalVariable.setAccessToken(accessToken);

            /*cai dat header voi yeu cau doc thong tin ca nhan cua mot benh nhan*/
            Map<String, String> headers = globalVariable.getHeaders();
            headers.put("type", "patient");

            /*gui yeu cau doc thong tin benh nhan*/
            viewModel.readPersonalInformation(headers);

            /*lang nghe phan hoi*/
            viewModel.getResponse().observe(this, response->{
                int result = response.getResult();
                /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                if( result == 1)
                {
                    User user = response.getData();
                    globalVariable.setAuthUser( user );

                    Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                    startActivity(intent);
                    finish();
                }
                /*result == 0 => thong bao va thoat ung dung*/
                if( result == 0)
                {
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
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1000);
        }
    }/*end OnCreate*/

    /**
     * @author Phong-Kaster
     * @since 16-11-2022
     * @return boolean
     * true if we are connecting with Internet
     * false if we aren't
     */
    public boolean isInternetAvailable() {
        boolean connected = false;
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