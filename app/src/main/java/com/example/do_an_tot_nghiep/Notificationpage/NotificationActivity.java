package com.example.do_an_tot_nghiep.Notificationpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Homepage.HomepageViewModel;
import com.example.do_an_tot_nghiep.R;

import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    private final String TAG = "NotificationActivity";

    private AppCompatButton btnSet;
    private AppCompatButton btnCancel;

    private GlobalVariable globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Toast.makeText(this, TAG, Toast.LENGTH_SHORT).show();

        setupComponent();
        setupViewModel();
        setupEvent();
    }

    private void setupComponent()
    {
        btnSet = findViewById(R.id.btnSet);
        btnCancel = findViewById(R.id.btnCancel);

        globalVariable = (GlobalVariable) this.getApplication();
    }


    private void setupViewModel()
    {
        /*Step 1 - declare*/
        HomepageViewModel viewModel = new ViewModelProvider(this).get(HomepageViewModel.class);
        viewModel.instantiate();

        /*Step 2 - prepare header & parameters*/
        Map<String, String> header = globalVariable.getHeaders();
        ;

        /*Step 3 - listen speciality Read All */
        Map<String, String> paramsSpeciality = new HashMap<>();
        viewModel.specialityReadAll(header, paramsSpeciality);
        viewModel.getSpecialityReadAllResponse().observe(this, response->{
            int result = response.getResult();
            int quantity = response.getQuantity();
            System.out.println(TAG);
            System.out.println("result: " +result);
            System.out.println("quantity: " + quantity);

        });

    }



    private void setupEvent()
    {
        btnSet.setOnClickListener(view->{
            System.out.println("BUTTON SET CLICKED");
            Intent intent = new Intent(this, NotificationService.class);
            intent.putExtra("input", "Phong");
            ContextCompat.startForegroundService(this, intent);
        });

        /*BUTTON CANCEL*/
        btnCancel.setOnClickListener(view->{
            System.out.println("BUTTON CANCEL CLICKED");
        });
    }
}