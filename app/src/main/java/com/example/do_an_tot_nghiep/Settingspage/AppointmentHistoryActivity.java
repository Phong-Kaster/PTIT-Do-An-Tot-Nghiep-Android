package com.example.do_an_tot_nghiep.Settingspage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Model.Appointment;
import com.example.do_an_tot_nghiep.Model.Queue;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.RecyclerView.Appointment2RecyclerView;
import com.example.do_an_tot_nghiep.RecyclerView.AppointmentQueueRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 01-12-2022
 * Appointment History Activity shows the history of appointment create by PATIENTS
 */
public class AppointmentHistoryActivity extends AppCompatActivity {

    private final String TAG = "Appointment History Activity";
    private ImageButton btnBack;
    private RecyclerView appointmentRecyclerView;

    private Map<String, String> header;
    private Dialog dialog;
    private LoadingScreen loadingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_history);

        setupComponent();
        setupViewModel();
        setupEvent();
    }

    /**
     * @since 01-12-2022
     * setup component
     */
    private void setupComponent()
    {
        btnBack = findViewById(R.id.btnBack);
        appointmentRecyclerView = findViewById(R.id.appointmentRecyclerView);

        GlobalVariable globalVariable = (GlobalVariable) this.getApplication();
        header = globalVariable.getHeaders();
        dialog = new Dialog(this);
        loadingScreen = new LoadingScreen(this);
    }

    private void setupViewModel()
    {
        SettingspageViewModel viewModel = new ViewModelProvider(this).get(SettingspageViewModel.class);
        viewModel.instantiate();

        /*SEND REQUEST*/
        Map<String, String> parameters = new HashMap<>();
        parameters.put("order[dir]", "desc");
        parameters.put("order[column]", "date");
        viewModel.readAll(header, parameters);
        viewModel.getReadAllResponse().observe(this, response->{
            try {
                int result = response.getResult();
                /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                if (result == 1) {
                    List<Appointment> list = response.getData();
                    setupRecyclerView(list);
                }
                /*result == 0 => thong bao va thoat ung dung*/
                if (result == 0) {
                    System.out.println(TAG);;
                    System.out.println("READ ALL");
                    System.out.println("shut down by result == 0");
                    System.out.println("msg: " + response.getMsg());
                    dialog.announce();
                    dialog.show(R.string.attention, getString(R.string.oops_there_is_an_issue), R.drawable.ic_info);
                    dialog.btnOK.setOnClickListener(view -> {
                        dialog.close();
                        finish();
                    });
                }

            } catch (Exception ex) {
                System.out.println(TAG);
                System.out.println("READ ALL");
                System.out.println("shut down by exception");
                System.out.println(ex);
                /*Neu truy van lau qua ma khong nhan duoc phan hoi thi cung dong ung dung*/
                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view -> {
                    dialog.close();
                    finish();
                });
            }
        });/*end SEND REQUEST*/


        /*ANIMATION*/
        viewModel.getAnimation().observe(this, aBoolean -> {
            if( aBoolean )
            {
                loadingScreen.start();
            }
            else
            {
                loadingScreen.stop();
            }
        });
    }

    /**
     * @since 01-12-2022
     * setup event
     */
    private void setupEvent()
    {
        btnBack.setOnClickListener(view->finish());
    }

    /**
     * @since 01-12-2022
     * setup recycler view
     */
    private void setupRecyclerView(List<Appointment> list)
    {
        Appointment2RecyclerView appointmentAdapter = new Appointment2RecyclerView(this, list);
        appointmentRecyclerView.setAdapter(appointmentAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        appointmentRecyclerView.setLayoutManager(manager);
    }
}