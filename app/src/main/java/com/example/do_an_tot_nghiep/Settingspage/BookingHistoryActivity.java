package com.example.do_an_tot_nghiep.Settingspage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.Model.Booking;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.RecyclerView.BookingRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 10-12-2022
 * Booking History Activity
 */
public class BookingHistoryActivity extends AppCompatActivity {

    private final String TAG = "Booking History Activity";
    private ImageButton btnBack;
    private RecyclerView bookingRecyclerView;

    private Map<String, String> header;
    private Dialog dialog;
    private LoadingScreen loadingScreen;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        setupComponent();
        setupViewModel();
        setupEvent();

    }

    /**
     * @since 10-12-2022
     * setup component
     */
    private void setupComponent()
    {
        btnBack = findViewById(R.id.btnBack);
        bookingRecyclerView = findViewById(R.id.bookingRecyclerView);

        GlobalVariable globalVariable = (GlobalVariable) this.getApplication();
        sharedPreferences = this.getApplication()
                .getSharedPreferences(globalVariable.getSharedReferenceKey(), MODE_PRIVATE);

        header = globalVariable.getHeaders();
        dialog = new Dialog(this);
        loadingScreen = new LoadingScreen(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tooltip.setLocale(this, sharedPreferences);
    }

    /**
     * @since 10-12-2022
     * set up view model
     */
    private void setupViewModel()
    {
        /*declare*/
        SettingspageViewModel viewModel = new ViewModelProvider(this).get(SettingspageViewModel.class);
        viewModel.instantiate();

        /*SEND REQUEST*/
        Map<String, String> parameters = new HashMap<>();
        parameters.put("length", "20");
        viewModel.bookingReadAll(header, parameters);

        viewModel.getBookingReadAll().observe(this, response->{
            try
            {
                int result = response.getResult();
                /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                if( result == 1)
                {
                    List<Booking> list = response.getData();
                    setupRecyclerView(list);
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

            }
            catch(Exception ex)
            {
                /*Neu truy van lau qua ma khong nhan duoc phan hoi thi cung dong ung dung*/
                System.out.println(TAG);
                System.out.println(ex);

                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view->{
                    dialog.close();
                    finish();
                });
            }

        });/*end SEND REQUEST*/

        /*ANIMATION*/
        viewModel.getAnimation().observe(this, aBoolean -> {
            if(aBoolean) loadingScreen.start();
            else loadingScreen.stop();
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
    private void setupRecyclerView(List<Booking> list)
    {
        BookingRecyclerView appointmentAdapter = new BookingRecyclerView(this, list);
        bookingRecyclerView.setAdapter(appointmentAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        bookingRecyclerView.setLayoutManager(manager);
    }
}