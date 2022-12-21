package com.example.do_an_tot_nghiep.Bookingpage;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.do_an_tot_nghiep.R;

/**
 * @author Phong-Kaster
 * @since 22-11-2022
 * Booking-page activity
 */
public class BookingpageActivity extends AppCompatActivity {

    private final String TAG = "Booking-page Activity";

    private ImageButton btnBack;
    private final FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingpage);

        setupBookingFragment1();
        setupComponent();
        setupEvent();
    }

    private void setupBookingFragment1()
    {
        String fragmentTag = "BookingFragment1";
        Fragment fragment = new BookingFragment1();


        /*Step 1*/
        FragmentTransaction transaction = manager.beginTransaction();

        /*Step 2*/
        String serviceId = getIntent().getStringExtra("serviceId");
        String doctorId = getIntent().getStringExtra("doctorId");

        /*Step 3*/
        Bundle bundle = new Bundle();
        bundle.putString("serviceId", serviceId);
        bundle.putString("doctorId", doctorId);
        fragment.setArguments(bundle);

        /*Step 4*/
        transaction.replace(R.id.frameLayout, fragment, fragmentTag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * @since 23-11-2022
     * setup component
     */
    private void setupComponent()
    {
        btnBack = findViewById(R.id.btnBack);
    }

    /**
     * @since 23-11-2022
     * setup event
     */
    private void setupEvent()
    {
        btnBack.setOnClickListener(view-> finish());
    }

    /**
     * @since 23-11-2022
     * override button back of device
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}