package com.example.do_an_tot_nghiep.Servicepage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_tot_nghiep.Bookingpage.BookingpageActivity;
import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.Model.Doctor;
import com.example.do_an_tot_nghiep.Model.Service;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.RecyclerView.DoctorRecyclerView;
import com.example.do_an_tot_nghiep.RecyclerView.SpecialityRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 22-11-2022
 * Service-page activity
 * this activity shows service's information and button booking
 */
public class ServicepageActivity extends AppCompatActivity {

    private final String TAG = "Service-page Activity";
    private String serviceId;

    private GlobalVariable globalVariable;
    private Dialog dialog;
    private LoadingScreen loadingScreen;

    private WebView wvwDescription;
    private TextView txtName;
    private ImageView imgAvatar;

    private ImageButton btnBack;
    private AppCompatButton btnCreateBooking;
    private RecyclerView doctorRecyclerView;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicepage);

        setupComponent();
        setupViewModel();
        setupEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tooltip.setLocale(this, sharedPreferences);
    }

    /**
     * @since 22-11-2022
     * setup component
     */
    private void setupComponent()
    {
        serviceId = getIntent().getStringExtra("serviceId");
        globalVariable = (GlobalVariable) this.getApplication();
        sharedPreferences = this.getApplication()
                .getSharedPreferences(globalVariable.getSharedReferenceKey(), MODE_PRIVATE);

        dialog = new Dialog(this);
        loadingScreen = new LoadingScreen(this);

        wvwDescription = findViewById(R.id.wvwDescription);
        txtName = findViewById(R.id.txtName);
        imgAvatar = findViewById(R.id.imgAvatar);

        btnBack = findViewById(R.id.btnBack);
        btnCreateBooking = findViewById(R.id.btnCreateBooking);
        doctorRecyclerView = findViewById(R.id.doctorRecyclerView);
    }

    /**
     * @since 22-11-2022
     * setup view model
     */
    private void setupViewModel()
    {
        ServicepageViewModel viewModel = new ViewModelProvider(this).get(ServicepageViewModel.class);
        viewModel.instantiate();

        /*prepare HEADER*/
        Map<String, String> header = globalVariable.getHeaders();

        /*listen for response*/
        viewModel.readById(header, serviceId);
        viewModel.getResponse().observe(this, response->{
            try
            {
                int result = response.getResult();
                /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                if( result == 1)
                {
                    Service service = response.getData();
                    printServiceInformation(service);
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
                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view->{
                    dialog.close();
                    finish();
                });
            }
        });/*end viewModel.getResponse()*/


        /*animation*/
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

        /*doctor read all*/
        Map<String, String> parameters = new HashMap<>();
        parameters.put("service_id", serviceId);
        viewModel.doctorReadAll(header, parameters);
        viewModel.getDoctorReadAllResponse().observe(this, response->{
            try
            {
                int result = response.getResult();
                /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                if( result == 1)
                {
                    List<Doctor> list = response.getData();
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
                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view->{
                    dialog.close();
                    finish();
                });
            }
        });
    }


    /**
     * @since 22-11-2022
     * setup event
     */
    private void printServiceInformation(Service service)
    {
        String image = Constant.UPLOAD_URI() + service.getImage();
        String name = service.getName();
        String description = "<html>"+
                "<style>body{font-size: 11px}</style>"+
                service.getDescription() + "</body></html>";

        txtName.setText(name);

        if( service.getImage().length() > 0)
        {
            Picasso.get().load(image).into(imgAvatar);
        }
        wvwDescription.loadDataWithBaseURL(null, description, "text/HTML", "UTF-8", null);
    }

    private void setupEvent()
    {
        btnBack.setOnClickListener(view->finish());


        btnCreateBooking.setOnClickListener(view->{
            Intent intent = new Intent(this, BookingpageActivity.class);
            intent.putExtra("serviceId", serviceId);
            startActivity(intent);
        });
    }

    /**
     * @since 19-12-2022
     * @param list is list<doctor>
     */
    private void setupRecyclerView(List<Doctor> list)
    {
        DoctorRecyclerView doctorAdapter = new DoctorRecyclerView(this, list);
        doctorRecyclerView.setAdapter(doctorAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        doctorRecyclerView.setLayoutManager(manager);
    }
}