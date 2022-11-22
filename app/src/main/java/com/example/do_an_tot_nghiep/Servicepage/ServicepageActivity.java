package com.example.do_an_tot_nghiep.Servicepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Homepage.HomepageActivity;
import com.example.do_an_tot_nghiep.MainActivity;
import com.example.do_an_tot_nghiep.Model.Service;
import com.example.do_an_tot_nghiep.Model.User;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

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
    private ServicepageViewModel viewModel;

    private GlobalVariable globalVariable;
    private Dialog dialog;
    private LoadingScreen loadingScreen;

    private WebView wvwDescription;
    private TextView txtName;
    private ImageView imgAvatar;

    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicepage);

        setupComponent();
        setupViewModel();
        setupEvent();
    }

    /**
     * @since 22-11-2022
     * setup component
     */
    private void setupComponent()
    {
        serviceId = getIntent().getStringExtra("serviceId");
        globalVariable = (GlobalVariable) this.getApplication();

        dialog = new Dialog(this);
        loadingScreen = new LoadingScreen(this);

        wvwDescription = findViewById(R.id.wvwDescription);
        txtName = findViewById(R.id.txtName);
        imgAvatar = findViewById(R.id.imgAvatar);

        btnBack = findViewById(R.id.btnBack);
    }

    /**
     * @since 22-11-2022
     * setup view model
     */
    private void setupViewModel()
    {
        viewModel = new ViewModelProvider(this).get(ServicepageViewModel.class);
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
    }
}