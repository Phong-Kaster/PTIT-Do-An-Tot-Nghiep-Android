package com.example.do_an_tot_nghiep.Specialitypage;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.Model.Doctor;
import com.example.do_an_tot_nghiep.Model.Speciality;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.RecyclerView.DoctorRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Phong-Kaster
 * @since 21-11-2022
 * Speciality page activity
 * this activity is used to show speciality's information and related doctors
 */
public class SpecialitypageActivity extends AppCompatActivity {

    private final String TAG = "Speciality-page Activity";
    private TextView txtName;
    private WebView wvwDescription;
    private RecyclerView recyclerViewDoctor;


    private GlobalVariable globalVariable;
    private String specialityId;

    private LoadingScreen loadingScreen;
    private Dialog dialog;
    private ImageView imgAvatar;

    private ImageButton btnBack;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialitypage);

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
     * @since 21-11-2022
     * setup component
     */
    private void setupComponent()
    {
        txtName = findViewById(R.id.txtName);
        wvwDescription = findViewById(R.id.wvwDescription);
        recyclerViewDoctor = findViewById(R.id.recyclerViewDoctor);


        globalVariable = (GlobalVariable) this.getApplication();
        sharedPreferences = this.getApplication()
                .getSharedPreferences(globalVariable.getSharedReferenceKey(), MODE_PRIVATE);
        specialityId = getIntent().getStringExtra("specialityId");


        loadingScreen = new LoadingScreen(this);
        dialog = new Dialog(this);
        imgAvatar = findViewById(R.id.imgAvatar);

        btnBack = findViewById(R.id.btnBack);
    }

    /**
     * @since 21-11-2022
     * setup view model
     */
    private void setupViewModel()
    {
        /*declare*/
        SpecialitypageViewModel viewModel = new ViewModelProvider(this).get(SpecialitypageViewModel.class);
        viewModel.instantiate();


        /*setup headers & send request*/
        Map<String, String> header = globalVariable.getHeaders();
        viewModel.specialityReadById(header, specialityId);


        Map<String, String> parameters = new HashMap<>();
        parameters.put("speciality_id", specialityId);
        viewModel.doctorReadAll(header, parameters);


        /*animation*/
        viewModel.getAnimation().observe(this, aBoolean -> {
            if(aBoolean)
            {
                loadingScreen.start();
            }
            else
            {
                loadingScreen.stop();
            }
        });



        /*listen for response*/
        viewModel.getDoctorReadAllResponse().observe(this, response->{
            int result = response.getResult();

            try
            {
                if( result == 1)
                {
                    List<Doctor> list = response.getData();
                    setupDoctorRecyclerView(list);
                }
                else
                {
                    Toast.makeText(this, getString(R.string.oops_there_is_an_issue), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            catch (Exception ex)
            {
                System.out.println(TAG);
                System.out.println(ex.getMessage());
                Toast.makeText(this, getString(R.string.oops_there_is_an_issue), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        viewModel.getSpecialityReadByIdResponse().observe(this, response->{
            int result = response.getResult();
            try
            {
                if( result == 1)
                {
                    Speciality speciality = response.getData();
                    printSpecialityInformation(speciality);
                }
                else
                {
                    Toast.makeText(this, getString(R.string.oops_there_is_an_issue), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            catch (Exception ex)
            {
                System.out.println(TAG);
                System.out.println(ex.getMessage());
                Toast.makeText(this, getString(R.string.oops_there_is_an_issue), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    /**
     * @since 22-11-2022
     */
    private void setupEvent()
    {
        btnBack.setOnClickListener(view->finish());
    }

    /**
     * @author Phong-Kaster
     * @since 21-11-2022
     * setup doctor recycler view
     */
    private void setupDoctorRecyclerView(List<Doctor> list)
    {
        DoctorRecyclerView doctorAdapter = new DoctorRecyclerView(this, list);
        recyclerViewDoctor.setAdapter(doctorAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewDoctor.setLayoutManager(manager);
    }


    /**
     * @since 21-11-2022
     * @param speciality is the speciality that users have clicked from HomepageActivity
     */
    private void printSpecialityInformation(Speciality speciality)
    {
        String name = speciality.getName();
        String description = "<html>" +
                "<style>body{font-size: 11px}</style>"+
                "<body>"+  speciality.getDescription() +
                "</body>" +
                "</html>";
        String image = Constant.UPLOAD_URI() + speciality.getImage();

        txtName.setText(name);
        Picasso.get().load(image).into(imgAvatar);
        wvwDescription.loadDataWithBaseURL(null, description, "text/HTML", "UTF-8", null);
    }
}