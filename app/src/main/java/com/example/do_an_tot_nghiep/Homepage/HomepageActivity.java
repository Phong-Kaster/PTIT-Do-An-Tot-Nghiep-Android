package com.example.do_an_tot_nghiep.Homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 17-11-2022
 * Homepage Activity
 */
public class HomepageActivity extends AppCompatActivity {

    private final String TAG = "Homepage Activity";
    private GlobalVariable globalVariable;
    private Dialog dialog;

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        /*Enable HomeFragment by default*/
        fragment = new HomeFragment();
        enableFragment(fragment);

        /*Run necessary functions*/
        setupVariable();
        setupEvent();
    }

    /**
     * @since 17-11-2022
     * setup variable
     */
    private void setupVariable()
    {
        globalVariable = (GlobalVariable) this.getApplication();
        dialog = new Dialog(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationMenu);
    }


    /**
     * @since 17-11-2022
     * setup event
     */
    @SuppressLint("NonConstantResourceId")
    private void setupEvent(){
        /*set up event when users click on item in bottom navigation view*/
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int shortcut = item.getItemId();
            switch (shortcut){
                case R.id.shortcutHome:
                    fragment = new HomeFragment();
                    break;
                case R.id.shortcutNotification:
                    break;
                case R.id.shortcutAppointment:
                    break;
                case R.id.shortcutPersonality:
                    break;
            }

            enableFragment(fragment);
            return true;
        });
    }


    /**
     * @author Phong-Kaster
     * activate a fragment right away
     * */
    public void enableFragment(Fragment fragment)
    {
        /*Step 1*/
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();


        /*Step 2*/
        Map<String, String > headers = ((GlobalVariable)getApplication()).getHeaders();
        String accessToken = headers.get("Authorization");
        String contentType = headers.get("Content-Type");
//
//        User AuthUser = ((GlobalVariable)getApplication()).getAuthUser();
//        SiteSettings appInfo = ((GlobalVariable)getApplication()).getAppInfo();


        /*Step 3*/
        Bundle bundle = new Bundle();

        bundle.putString("accessToken", accessToken);
        bundle.putString("contentType", contentType);
//        bundle.putParcelable("AuthUser", AuthUser);
//        bundle.putParcelable("appInfo", appInfo);
        fragment.setArguments(bundle);


        /*Step 4*/
        transaction.replace(R.id.frameLayout, fragment, "myFragment");
        transaction.commit();
    }

    /**
     * @since 17-11-2022
     * if users click back button by accident or on purpose
     * a confirm dialog will pop up to make sure they want to exit the application
     */
    @Override
    public void onBackPressed() {

        dialog.confirm();
        dialog.show(getString(R.string.attention),
                getString(R.string.are_you_sure_about_that), R.drawable.ic_info);
        dialog.btnOK.setOnClickListener(view->{
            super.onBackPressed();
            finish();
        });
        dialog.btnCancel.setOnClickListener(view-> dialog.close());
    }
}