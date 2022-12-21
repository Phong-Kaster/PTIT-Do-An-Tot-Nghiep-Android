package com.example.do_an_tot_nghiep.Doctorpage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.example.do_an_tot_nghiep.Bookingpage.BookingpageActivity;
import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Model.Doctor;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Phong-Kaster
 * @since 20-11-2022
 * Doctor-page Activity
 * this activity is used to show information of a doctor
 */
public class DoctorpageActivity extends AppCompatActivity {
    private static final String TAG = "Doctor-page Activity";

    private String doctorId;
    private CircleImageView imgAvatar;
    private TextView txtName;

    private TextView txtSpeciality;
    private TextView txtPhoneNumber;
    private WebView wvwDescription;

    private DoctorpageViewModel viewModel;
    private GlobalVariable globalVariable;
    private LoadingScreen loadingScreen;

    private ImageButton btnBack;
    private AppCompatButton btnCreateBooking;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorpage);


        setupComponent();
        setupViewModel();
        setupEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*tu bo nho ROM cua thiet bi lay ra ngon ngu da cai dat cho ung dung*/
        String language = sharedPreferences.getString("language", getString(R.string.vietnamese));


//        System.out.println(TAG);
//        System.out.println(language);
        String vietnamese = getString(R.string.vietnamese);
        String deutsch = getString(R.string.deutsch);


        Locale myLocale = new Locale("en");
        if(Objects.equals(language, vietnamese))
        {
            myLocale = new Locale("vi");
        }
        if(Objects.equals(language,deutsch))
        {
            myLocale = new Locale("de");
        }


        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();


        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(myLocale);


        Locale.setDefault(myLocale);
        resources.updateConfiguration(configuration, displayMetrics);
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
        sharedPreferences = this.getApplication()
                .getSharedPreferences(globalVariable.getSharedReferenceKey(), MODE_PRIVATE);
        loadingScreen = new LoadingScreen(this);
        btnBack = findViewById(R.id.btnBack);
        btnCreateBooking = findViewById(R.id.btnCreateBooking);
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
     * @since 22-11-2022
     * setup event
     */
    private void setupEvent()
    {
        btnBack.setOnClickListener(view->finish());

        btnCreateBooking.setOnClickListener(view->{
            Intent intent = new Intent(this, BookingpageActivity.class);
            intent.putExtra("doctorId", doctorId);
            startActivity(intent);
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