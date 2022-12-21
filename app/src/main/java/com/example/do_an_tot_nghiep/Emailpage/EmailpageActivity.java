package com.example.do_an_tot_nghiep.Emailpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.do_an_tot_nghiep.Bookingpage.BookingFragment1;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.R;

/**
 * @author Phong-Kaster
 * @since 03-12-2022
 * Email-page activity
 */
public class EmailpageActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private final FragmentManager manager = getSupportFragmentManager();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailpage);

        setupEmailFragment1();
        setupComponent();
        setupEvent();
    }



    /**
     * @since 03-12-2022
     */
    private void setupEmailFragment1()
    {
        String fragmentTag = "EmailFragment1";
        Fragment fragment = new EmailFragment1();


        /*Step 1*/
        FragmentTransaction transaction = manager.beginTransaction();

        /*Step 2*/
//        String serviceId = getIntent().getStringExtra("serviceId");

        /*Step 3*/
//        Bundle bundle = new Bundle();
//        bundle.putString("serviceId", serviceId);
//        fragment.setArguments(bundle);

        /*Step 4*/
        transaction.replace(R.id.frameLayout, fragment, fragmentTag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * @since 03-12-2022
     * setup component
     */
    private void setupComponent()
    {
        btnBack = findViewById(R.id.btnBack);
        GlobalVariable globalVariable = (GlobalVariable) this.getApplication();
        sharedPreferences = this.getApplication()
                .getSharedPreferences(globalVariable.getSharedReferenceKey(), MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tooltip.setLocale(this, sharedPreferences);
    }

    /**
     * @since 03-12-2022
     * setup event
     */
    private void setupEvent()
    {
        btnBack.setOnClickListener(view-> finish());
    }

    /**
     * @since 03-12-2022
     * override button back of device
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}