package com.example.do_an_tot_nghiep.Settingspage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.AppointmentReadAll;
import com.example.do_an_tot_nghiep.Container.PatientProfileChangePersonalInformation;
import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.Model.User;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InformationActivity extends AppCompatActivity {
    private final String TAG = "Information Activity";

    private CircleImageView imgAvatar;
    private TextView txtHealthInsuranceNumber;

    private TextView txtEmail;
    private TextView txtName;

    private TextView txtPhone;
    private RadioGroup rgGender;
    private TextView txtBirthday;

    private TextView txtAddress;
    private TextView txtCreateAt;
    private TextView txtUpdateAt;

    private AppCompatButton btnSave;
    private GlobalVariable globalVariable;
    private Dialog dialog;
    private LoadingScreen loadingScreen;

    private Map<String, String> header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        setupComponent();
        showInfo();
        setupEvent();
    }

    /**
     * @since 02-12-2022
     * setup component
     */
    private void setupComponent()
    {
        imgAvatar = findViewById(R.id.imgAvatar);
        txtHealthInsuranceNumber = findViewById(R.id.txtHealthInsuranceNumber);

        txtEmail = findViewById(R.id.txtEmail);
        txtName = findViewById(R.id.txtName);

        txtPhone = findViewById(R.id.txtPhone);
        rgGender = findViewById(R.id.rgGender);

        txtBirthday = findViewById(R.id.txtBirthday);
        txtAddress = findViewById(R.id.txtAddress);

        txtCreateAt = findViewById(R.id.txtCreateAt);
        txtUpdateAt = findViewById(R.id.txtUpdateAt);

        btnSave = findViewById(R.id.btnSave);
        dialog = new Dialog(this);
        loadingScreen = new LoadingScreen(this);
        globalVariable = (GlobalVariable) this.getApplication();

        header = globalVariable.getHeaders();
    }

    /**
     * @since 02-12-2022
     * show user info
     */
    private void showInfo()
    {
        User user = globalVariable.getAuthUser();

        String avatar = Constant.UPLOAD_URI() + user.getAvatar();
        String id = String.valueOf(user.getId());

        String email = user.getEmail();
        String name = user.getName();

        String phone = user.getPhone();
        int gender = user.getGender();

        String birthday = user.getBirthday();
        String address = user.getAddress();

        String createAt = Tooltip.beautifierDatetime(this, user.getCreateAt() );
        String updateAt = Tooltip.beautifierDatetime(this, user.getUpdateAt() );



        if( user.getAvatar().length() > 0)
        {
            Picasso.get().load(avatar).into(imgAvatar);
        }
        txtHealthInsuranceNumber.setText(id);
        txtEmail.setText(email);

        txtName.setText(name);
        txtPhone.setText(phone);


        if(gender == 1)
        {
            rgGender.check(R.id.rdMale);
        }
        else
        {
            rgGender.check(R.id.rdFemale);
        }
        txtBirthday.setText(birthday);
        txtAddress.setText(address);
        txtCreateAt.setText(createAt);
        txtUpdateAt.setText(updateAt);
    }

    /**
     * @since 02-12-2022
     * setup event
     */
    private void setupEvent()
    {
        /*BUTTON SAVE*/
        btnSave.setOnClickListener(view->{
            String name = txtName.getText().toString();
            String gender = rgGender.getCheckedRadioButtonId() == R.id.rdMale ? "1" : "0";
            String birthday = txtBirthday.getText().toString();
            String address = txtAddress.getText().toString();


            loadingScreen.start();
            changePersonalInformation(name, gender, birthday, address);


        });/*end BUTTON SAVE*/
    }

    private void changePersonalInformation(String name, String gender, String birthday, String address)
    {
        /*Step 1*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 2*/
        String action = "personal"; // is the key to distingue POST request: personal, avatar, password
        Call<PatientProfileChangePersonalInformation> container = api.changePersonalInformation(header, action, name, gender, birthday, address );

        /*Step 3*/
        container.enqueue(new Callback<PatientProfileChangePersonalInformation>() {
            @Override
            public void onResponse(@NonNull Call<PatientProfileChangePersonalInformation> call, @NonNull Response<PatientProfileChangePersonalInformation> response) {
                loadingScreen.stop();
                if(response.isSuccessful())
                {
                    PatientProfileChangePersonalInformation content = response.body();
                    assert content != null;
//                    System.out.println(TAG);
//                    System.out.println("result: " + content.getResult());
//                    System.out.println("msg: " + content.getMsg());

                    /*update user information*/
                    User user = content.getData();
                    globalVariable.setAuthUser(user);

                    /*show dialog*/
                    dialog.announce();
                    dialog.btnOK.setOnClickListener(view->dialog.close());
                    dialog.show(R.string.success, getString(R.string.successful_action), R.drawable.ic_check);
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println(TAG);
                        System.out.println( jObjError );
                    }
                    catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PatientProfileChangePersonalInformation> call, @NonNull Throwable t) {
                loadingScreen.stop();
                System.out.println(TAG);
                System.out.println("Change Personal Information - error: " + t.getMessage());
            }
        });
    }
}