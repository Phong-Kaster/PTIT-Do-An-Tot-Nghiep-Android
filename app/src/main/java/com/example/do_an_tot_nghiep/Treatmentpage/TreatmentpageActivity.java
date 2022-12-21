package com.example.do_an_tot_nghiep.Treatmentpage;

import androidx.appcompat.app.AppCompatActivity;
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
 * @since 30-11-2022
 * Treatment-page Activity shows treatments from an appointment
 */
public class TreatmentpageActivity extends AppCompatActivity {

    private final String TAG = "Treatment-page Activity";
    private SharedPreferences sharedPreferences;

    private ImageButton btnBack;
    private final FragmentManager manager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatmentpage);

        setupComponent();
        setupEvent();
        setupTreatmentFragment1();
    }

    /**
     * @since 30-11-2022
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

    private void setupTreatmentFragment1()
    {
        String fragmentTag = "TreatmentFragment1";
        Fragment fragment = new TreatmentFragment1();


        /*Step 1*/
        FragmentTransaction transaction = manager.beginTransaction();

        /*Step 2*/
        transaction.replace(R.id.frameLayout, fragment, fragmentTag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * @since 30-11-2022
     * setup Event;
     */
    private void setupEvent()
    {
        btnBack.setOnClickListener(view-> this.finish());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}