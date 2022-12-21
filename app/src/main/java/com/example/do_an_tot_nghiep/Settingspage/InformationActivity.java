package com.example.do_an_tot_nghiep.Settingspage;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.PatientProfileChangeAvatar;
import com.example.do_an_tot_nghiep.Container.PatientProfileChangePersonalInformation;
import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.Model.User;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    private Uri uriAvatar;
    private AppCompatButton btnUploadAvatar;
    private SharedPreferences sharedPreferences;

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
        btnUploadAvatar = findViewById(R.id.btnAvatarUpload);

        dialog = new Dialog(this);
        loadingScreen = new LoadingScreen(this);
        globalVariable = (GlobalVariable) this.getApplication();

        header = globalVariable.getHeaders();
        sharedPreferences = this.getApplication()
                .getSharedPreferences(globalVariable.getSharedReferenceKey(), MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tooltip.setLocale(this, sharedPreferences);
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
        /*-************************PREPARE TIME & DATE PICKER FOR BUTTON**************************************/
        /*GET TODAY*/
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        /*DATE PICKER FOR BIRTHDAY - if day or month less than 10, we will insert 0 in front of the value*/
        DatePickerDialog.OnDateSetListener birthdayDialog = (view13, year1, month1, day1) -> {
            calendar.set(Calendar.YEAR, year1);
            calendar.set(Calendar.MONTH, month1);
            calendar.set(Calendar.DAY_OF_MONTH, day1);

            String dayFormatted = String.valueOf(day1);
            String monthFormatted = String.valueOf(month1+1);// add 1 unit because 0 <= month <=11

            if( day1 < 10)
            {
                dayFormatted = "0" + day1;
            }
            if( month1 < 10 )
            {
                monthFormatted = "0" + month1;
            }

            String output = year1 + "-" + monthFormatted + "-" + dayFormatted;
            txtBirthday.setText(output);
        };

        /* *************************LISTEN CLICK EVENT FOR BUTTONS**************************************/
        /*EDIT TEXT BIRTHDAY*/
        txtBirthday.setOnClickListener(birthdayView -> {
            new DatePickerDialog(this,birthdayDialog,year,month,day).show();
        });



        /*BUTTON SAVE*/
        btnSave.setOnClickListener(view->{
            String name = txtName.getText().toString();
            String gender = rgGender.getCheckedRadioButtonId() == R.id.rdMale ? "1" : "0";
            String birthday = txtBirthday.getText().toString();
            String address = txtAddress.getText().toString();


            loadingScreen.start();
            changePersonalInformation(name, gender, birthday, address);


        });/*end BUTTON SAVE*/


        /*IMG AVATAR*/
        imgAvatar.setOnClickListener(view->{
            verifyStoragePermissions(this);

            Intent intent = new Intent();
            intent.setType("image/*");//allows any image file type. Change * to specific extension to limit it
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            openGalleryToPickPhoto.launch(intent);
        });

        /*BUTTON UPLOAD AVATAR*/
        btnUploadAvatar.setOnClickListener(view->{
            if( uriAvatar == null)
            {
                dialog.announce();
                dialog.btnOK.setOnClickListener(d->dialog.close());
                dialog.show(R.string.attention, getString(R.string.click_on_your_avatar_to_select_new_photo), R.drawable.ic_info);
                return;
            }
            uploadPhotoToServer(uriAvatar);
        });
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
                    /*update user information*/
                    User user = content.getData();
                    globalVariable.setAuthUser(user);

                    int result = content.getResult();
                    String msg = content.getMsg();
                    dialog.announce();
                    dialog.btnOK.setOnClickListener(view->dialog.close());
                    if( result == 1 )
                    {
                        /*show dialog*/
                        dialog.show(R.string.success, getString(R.string.successful_action), R.drawable.ic_check);
                    }
                    else
                    {
                        dialog.show(R.string.attention, msg, R.drawable.ic_close);
                    }


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

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /**
     * @author Phong-Kaster
     * open Gallery To Pick Photo
     */
    private final ActivityResultLauncher<Intent> openGalleryToPickPhoto = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if( result.getResultCode() == RESULT_OK)
                    {
                        Intent data = result.getData();
                        assert data != null;
                        Uri uri = data.getData();

                        imgAvatar.setImageURI(uri);
                        uriAvatar = uri;
                    }
                    else
                    {
//                        dialog.announce();
//                        dialog.show(R.string.attention, getString(R.string.oops_there_is_an_issue), R.drawable.ic_close);
//                        dialog.btnOK.setOnClickListener(view->{
//                            dialog.close();
//                        });
                    }
                }
            });


    private void uploadPhotoToServer(Uri uri)
    {
        /*Step 1 - set up file path*/
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri,
                projection, null, null, null);

        int columnIndex = cursor.getColumnIndex(projection[0]);
        cursor.moveToFirst();
        String filePath = cursor.getString(columnIndex);
        cursor.close();


        /*Step 2 - configure new request*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        //String action = "avatar";// the key to distingue POST request: personal, avatar, password
        RequestBody action = RequestBody.create(MediaType.parse("multipart/form-data"), "avatar");


        File file = new File(Uri.parse(filePath).toString());
        RequestBody requestBodyFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part actualFile = MultipartBody.Part.createFormData("file", file.getName(), requestBodyFile);


        String accessToken = globalVariable.getAccessToken();
        String type = "Patient";
        Call<PatientProfileChangeAvatar> container = api.changeAvatar(accessToken, type, actualFile, action);

        /*Step 4*/
        container.enqueue(new Callback<PatientProfileChangeAvatar>() {
            @Override
            public void onResponse(@NonNull Call<PatientProfileChangeAvatar> call, @NonNull Response<PatientProfileChangeAvatar> response) {

                if(response.isSuccessful())
                {
                    PatientProfileChangeAvatar content = response.body();
                    assert content != null;

                    /*Show successful message*/
                    dialog.announce();
                    dialog.btnOK.setOnClickListener(view->dialog.close());
                    dialog.show(R.string.success, getString(R.string.successful_action), R.drawable.ic_check);


                    /*Update AuthUser in Application storage*/
                    User user = content.getData();
                    globalVariable.setAuthUser(user);
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PatientProfileChangeAvatar> call, @NonNull Throwable t) {
                System.out.println(TAG);
                System.out.println("ERROR");
                t.printStackTrace();
            }
        });
    }
}