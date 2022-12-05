package com.example.do_an_tot_nghiep.Alarmpage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.do_an_tot_nghiep.R;


/**
 * @author Phong-Kaster
 * @since 05-12-2022
 * this activity is used to set alarm with message
 */
public class AlarmpageActivity extends AppCompatActivity {

    private final String TAG = "Alarm-page Activity";
    private final FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmpage);

        String fragmentTag = "AlarmFragment";
        Fragment fragment = new AlarmpageFragment();


        /*Step 1*/
        FragmentTransaction transaction = manager.beginTransaction();

        try
        {
            /*Step 2*/
            transaction.replace(R.id.frameLayout, fragment, fragmentTag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        catch (Exception ex)
        {
            System.out.println(TAG);
            ex.getStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        this.finish();
    }
}