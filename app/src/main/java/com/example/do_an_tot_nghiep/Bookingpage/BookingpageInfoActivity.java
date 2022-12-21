package com.example.do_an_tot_nghiep.Bookingpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.BookingCancel;
import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.Homepage.HomepageActivity;
import com.example.do_an_tot_nghiep.Model.Booking;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author  Phong-Kaster
 * @since 24-11-2022
 * BOOKING PAGE ACTIVITY
 * this activity shows all information of a booking created by patient
 */
public class BookingpageInfoActivity extends AppCompatActivity {

    private final String TAG = "Booking-page Info Activity";

    private String bookingId;
    private String bookingStatus;


    private TextView txtBookingName;
    private TextView txtBookingPhone;
    private TextView txtPatientName;
    private TextView txtPatientGender;

    private TextView txtPatientBirthday;
    private TextView txtPatientAddress;
    private TextView txtPatientReason;
    private TextView txtDatetime;

    private TextView txtBookingStatus;

    private ImageView imgServiceAvatar;
    private TextView txtServiceName;

    private androidx.appcompat.widget.AppCompatButton btnPhoto;
    private androidx.appcompat.widget.AppCompatButton btnCancel;
    private ImageButton btnBack;

    private Map<String, String> header;

    private Dialog dialog;
    private LoadingScreen loadingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingpage_info);

        setupComponent();
        setupViewModel();
        setupEvent();
    }

    /**
     * @since 25-11-2022
     * setup component
     */
    private void setupComponent()
    {
        GlobalVariable globalVariable = (GlobalVariable) this.getApplication();
        header = globalVariable.getHeaders();
        bookingId = getIntent().getStringExtra("id");

        dialog = new Dialog(this);
        loadingScreen = new LoadingScreen(this);

        txtBookingName =findViewById(R.id.txtBookingName);
        txtBookingPhone = findViewById(R.id.txtBookingPhone);
        txtPatientName = findViewById(R.id.txtPatientName);
        txtPatientAddress = findViewById(R.id.txtPatientAddress);
        txtPatientBirthday = findViewById(R.id.txtPatientBirthday);
        txtDatetime = findViewById(R.id.txtDatetime);
        txtPatientGender = findViewById(R.id.txtPatientGender);
        txtPatientReason = findViewById(R.id.txtPatientReason);
        txtBookingStatus = findViewById(R.id.txtBookingStatus);

        btnPhoto = findViewById(R.id.btnPhoto);
        btnCancel = findViewById(R.id.btnCancel);
        btnBack = findViewById(R.id.btnBack);

        imgServiceAvatar = findViewById(R.id.imgServiceAvatar);
        txtServiceName = findViewById(R.id.txtServiceName);
    }


    /**
     * @since 25-11-2022
     * setup view model
     */
    private void setupViewModel()
    {
        /*declare*/
        BookingpageViewModel viewModel = new ViewModelProvider(this).get(BookingpageViewModel.class);
        viewModel.instantiate();

        /*SEND REQUEST TO SERVER*/
        viewModel.bookingReadByID(header, bookingId);
        viewModel.getBookingReadByIdResponse().observe(this, response->{
            try
            {
                int result = response.getResult();
                /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                if( result == 1)
                {
                    Booking booking = response.getData();
                    printBookingInfo(booking);
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
        /*end ANIMATION*/
    }

    /**
     * @since 25-11-2022
     * setup event
     */
    private void setupEvent()
    {
        btnBack.setOnClickListener(view->finish());
        btnPhoto.setOnClickListener(view->{
            Intent intent = new Intent(this, BookingpagePhotoActivity.class);
            intent.putExtra("bookingId", bookingId);
            intent.putExtra("bookingStatus", bookingStatus);
            startActivity(intent);
        });
        btnCancel.setOnClickListener(view->{

            Dialog dialog = new Dialog(this);
            dialog.confirm();
            dialog.show(R.string.attention, getString(R.string.are_you_sure_about_that), R.drawable.ic_info);
            dialog.btnOK.setOnClickListener(view1->{
                loadingScreen.start();
                sendCancelRequest();
                dialog.close();
            });
            dialog.btnCancel.setOnClickListener(view1-> dialog.close());


        });
    }

    /**
     * @since 25-11-2022
     * print booking information
     */
    private void printBookingInfo(Booking booking) throws ParseException {
        /*Step 1 - get data*/
        String bookingName = booking.getBookingName();
        String bookingPhone = booking.getBookingPhone();

        String name = booking.getName();
        String address = booking.getAddress();

        String birthday = booking.getBirthday();
        String date = booking.getAppointmentDate();

        String time = booking.getAppointmentTime();
        String datetime = date + " " + getString(R.string.at) + " " + time;

        String gender = booking.getGender() == 1 ? getString(R.string.male) : getString(R.string.female);
        String reason = booking.getReason();

        String serviceName = booking.getService().getName();
        String serviceImage = Constant.UPLOAD_URI() + booking.getService().getImage();

        String status = booking.getStatus();
        bookingStatus = status;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        /*Step 2 - calculate difference between today & appointment date. If difference < 0,
        * this mean this booking happened yesterday => Disable CANCEL button */
        Date dateFormatted = formatter.parse(date);
        Date today = new Date();
        assert dateFormatted != null;
        long difference = Tooltip.getDateDifference(today, dateFormatted, TimeUnit.DAYS);
        if( !Objects.equals(status, "processing") || difference < 0)
        {
            btnCancel.setVisibility(TextView.GONE);
        }

        String verified = this.getString(R.string.verified);
        String processing = this.getString(R.string.processing);
        String cancelled = this.getString(R.string.cancel);
        int colorProcessing = this.getColor(R.color.colorGreen);
        int colorCancelled = this.getColor(R.color.colorRed);

        if(Objects.equals(status, "verified"))
        {
            txtBookingStatus.setText(verified);
            txtBookingStatus.setTextColor(colorProcessing);
        }
        else if( Objects.equals(status, "processing"))
        {
            txtBookingStatus.setText(processing);
            txtBookingStatus.setTextColor(colorProcessing);
        }
        else
        {
            txtBookingStatus.setText(cancelled);
            txtBookingStatus.setTextColor(colorCancelled);
        }


        /*Step 3 - print booking info to UI*/
        if( booking.getService().getImage().length() > 0)
        {
            Picasso.get().load(serviceImage).into(imgServiceAvatar);
        }
        txtServiceName.setText(serviceName);

        txtBookingName.setText(bookingName);
        txtBookingPhone.setText(bookingPhone);

        txtPatientName.setText(name);
        txtPatientGender.setText(gender);

        txtPatientBirthday.setText(birthday);
        txtPatientAddress.setText(address);

        txtPatientReason.setText(reason);
        txtDatetime.setText(datetime);
    }

    /**
     * @author Phong-Kaster
     * @since 25-11-2022
     * this function send a request to CANCEL booking
     */
    private void sendCancelRequest()
    {
        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        Call<BookingCancel> container = api.bookingCancel(header, bookingId);

        /*Step 4*/
        container.enqueue(new Callback<BookingCancel>() {
            @Override
            public void onResponse(@NonNull Call<BookingCancel> call, @NonNull Response<BookingCancel> response) {
                loadingScreen.stop();
                if(response.isSuccessful())
                {
                    /*show dialog message*/
                    BookingCancel content = response.body();
                    assert content != null;

                    System.out.println(content.getResult());
                    System.out.println(content.getMsg());

                    String title = getString(R.string.success);
                    String message = getString(R.string.successful_action);

                    dialog.announce();
                    dialog.show(title, message, R.drawable.ic_check );
                    dialog.btnOK.setOnClickListener(view->dialog.close());

                    /*hide button CANCEL*/
                    btnCancel.setVisibility(View.GONE);

                    /*update number of unread notifications*/
                    HomepageActivity.getInstance().setNumberOnNotificationIcon();
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
                        System.out.println(TAG);
                        System.out.println( e.getMessage() );
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<BookingCancel> call, @NonNull Throwable t) {
                loadingScreen.stop();
                System.out.println("Booking-page Info Activity - Cancel - error: " + t.getMessage());
            }
        });
    }
}