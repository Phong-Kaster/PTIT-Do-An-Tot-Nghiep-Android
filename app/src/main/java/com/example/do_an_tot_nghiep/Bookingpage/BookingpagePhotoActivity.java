package com.example.do_an_tot_nghiep.Bookingpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Model.Booking;
import com.example.do_an_tot_nghiep.Model.Photo;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.RecyclerView.BookingPhotoRecyclerView;
import com.example.do_an_tot_nghiep.RecyclerView.DoctorRecyclerView;

import java.util.List;
import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 10-12-2022
 * Booking page Photo activity shows all photos that have been uploads by user in a booking.
 */
public class BookingpagePhotoActivity extends AppCompatActivity {

    private final String TAG = "Booking-page Photo Activity";
    private String bookingId;

    private ImageButton btnBack;
    private BookingpageViewModel viewModel;

    private Map<String, String> header;
    private LoadingScreen loadingScreen;
    private Dialog dialog;


    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingpage_photo);


        setupComponent();
        setupViewModel();
        setupEvent();

    }

    /**
     * @since 10-12-2022
     */
    private void setupComponent()
    {
        if( getIntent().getStringExtra("bookingId") != null)
        {
            bookingId = getIntent().getStringExtra("bookingId");
            Toast.makeText(this, "Booking ID: " + bookingId, Toast.LENGTH_SHORT).show();
        }
        else
        {
            System.out.println(TAG);
            System.out.println("Booking ID is empty !");
            Toast.makeText(this, getString(R.string.oops_there_is_an_issue), Toast.LENGTH_SHORT).show();
            this.finish();
        }

        GlobalVariable globalVariable = (GlobalVariable) this.getApplication();
        header = globalVariable.getHeaders();
        dialog = new Dialog(this);
        loadingScreen = new LoadingScreen(this);

        btnBack = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.recyclerView);
    }

    /**
     * @since 10-12-2022
     * setup event
     */
    private void setupEvent()
    {
        btnBack.setOnClickListener(view->finish());
    }

    /**
     * @since 10-12-2022
     * setup view model
     */
    private void setupViewModel()
    {
        /*DECLARE*/
        viewModel = new ViewModelProvider(this).get(BookingpageViewModel.class);
        viewModel.instantiate();


        /*SEND REQUEST*/
        viewModel.bookingPhotoReadAll(header, bookingId);
        viewModel.getBookingPhotoReadAllResponse().observe(this, response->{
            try
            {
                int result = response.getResult();
                /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                if( result == 1)
                {
                    List<Photo> list = response.getData();
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
            if( aBoolean ) loadingScreen.start();
            else loadingScreen.stop();
        });
    }

    /**
     * @since 10-12-2022
     * setup recycler view
     */
    private void setupRecyclerView(List<Photo> list)
    {
        BookingPhotoRecyclerView bookingPhotoAdapter = new BookingPhotoRecyclerView(this, list);
        recyclerView.setAdapter(bookingPhotoAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }
}