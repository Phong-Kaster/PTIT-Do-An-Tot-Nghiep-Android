package com.example.do_an_tot_nghiep.Doctorpage;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Model.Doctor;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Phong-Kaster
 * @since 20-11-2022
 * Doctorpage Activity
 * this activity is used to show information of a doctor
 */
public class DoctorpageActivity extends AppCompatActivity {
    private static final String TAG = "DoctorpageActivity";

    private String doctorId;
    private CircleImageView imgAvatar;
    private TextView txtName;

    private TextView txtSpeciality;
    private TextView txtPhoneNumber;
    private WebView wvwDescription;

    private DoctorpageViewModel viewModel;
    private GlobalVariable globalVariable;
    private LoadingScreen loadingScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorpage);


        setupComponent();
        setupViewModel();


    }


    /**
     * @since 20-11-2022
     * setup component
     */
    private void setupComponent()
    {
        doctorId = getIntent().getStringExtra("doctorId");
        wvwDescription = findViewById(R.id.wvwDescription);

        imgAvatar = findViewById(R.id.imgAvatar);
        txtName = findViewById(R.id.txtName);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtSpeciality = findViewById(R.id.txtSpeciality);

        globalVariable = (GlobalVariable) this.getApplication();
        loadingScreen = new LoadingScreen(this);
    }

    /**
     * @since 20-11-2022
     * setup view model
     */
    private void setupViewModel()
    {
        viewModel = new ViewModelProvider(this).get(DoctorpageViewModel.class);
        viewModel.instantiate();

        Map<String, String> headers = globalVariable.getHeaders();
        viewModel.readById(headers, doctorId);

        viewModel.getResponse().observe(this, response->{
            try
            {
                int result = response.getResult();
                if( result == 1)
                {
                    Doctor doctor = response.getData();
                    printDoctorInfo(doctor);
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
     * @since 20-11-2022
     * print doctor information
     */
    private void printDoctorInfo(Doctor doctor)
    {
        String name = doctor.getName();
        String avatar = Constant.UPLOAD_URI() + doctor.getAvatar();
        String phone = doctor.getPhone();
        String description = "<html>" +
                                "<style>body{font-size: 11px}</style>"+
                                "<body>"+  doctor.getDescription() +
                                "</body>" +
                            "</html>";
        String speciality = doctor.getSpeciality().getName();

        txtName.setText(name);
        txtSpeciality.setText(speciality);
        txtPhoneNumber.setText(phone);
        Picasso.get().load(avatar).into(imgAvatar);
        wvwDescription.loadDataWithBaseURL(null, description, "text/HTML", "UTF-8", null);
    }
}