package com.example.do_an_tot_nghiep.Bookingpage;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.BookingCreate;
import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.Homepage.HomepageActivity;
import com.example.do_an_tot_nghiep.Model.Doctor;
import com.example.do_an_tot_nghiep.Model.Service;
import com.example.do_an_tot_nghiep.Model.User;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @since 09-12-2022
 * flow: Fragment 1 -> Fragment 3 -> Fragment 2
 */
public class BookingFragment1 extends Fragment {

    private final String TAG = "BookingFragment1";

    private String serviceId;// nếu serviceId == null thì nó sẽ bằng 1 bởi vì API cần serviceId do rằng buộc dữ liệu trong database
    private String doctorId;// doctorId == null thì nó sẽ bằng 0, vì chúng ta không cần
    private GlobalVariable globalVariable;
    private LoadingScreen loadingScreen;

    private Dialog dialog;

    private ImageView imgServiceAvatar;
    private TextView txtServiceName;

    private Activity activity;
    private Context context;
    private AppCompatButton btnConfirm;

    /*FORM*/
    private EditText txtBookingName;
    private EditText txtBookingPhone;
    private EditText txtPatientName;

    private RadioGroup rdPatientGender;
    private EditText txtPatientBirthday;

    private EditText txtPatientAddress;
    private EditText txtPatientReason;
    private EditText txtAppointmentDate;
    private EditText txtAppointmentTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking1, container, false);

        setupComponent(view);
        setupViewModel();
        setupEvent(view);

        return view;
    }

    /**
     * @since 23-11-2022
     * setup component()
     */
    private void setupComponent(View view)
    {
        /*GLOBAL VARIABLE*/
        activity = requireActivity();
        context = requireContext();

        globalVariable = (GlobalVariable) activity.getApplication();
        loadingScreen = new LoadingScreen(activity);
        dialog = new Dialog(context);
        User user = globalVariable.getAuthUser();

        Bundle bundle = getArguments();
        assert bundle != null;
        serviceId = bundle.getString("serviceId") != null ? bundle.getString("serviceId") : "";
        doctorId = bundle.getString("doctorId") != null ? bundle.getString("doctorId") : "0";

        System.out.println(TAG);
        System.out.println("serviceId: " + serviceId);
        System.out.println("doctorId: " + doctorId);

        /*FORM*/
        imgServiceAvatar = view.findViewById(R.id.imgServiceAvatar);
        txtServiceName = view.findViewById(R.id.txtServiceName);
        btnConfirm = view.findViewById(R.id.btnConfirm);

        txtBookingName = view.findViewById(R.id.txtBookingName);
        txtBookingPhone = view.findViewById(R.id.txtBookingPhone);
        txtPatientName = view.findViewById(R.id.txtPatientName);

        rdPatientGender = view.findViewById(R.id.rdPatientGender);

        txtPatientBirthday = view.findViewById(R.id.txtPatientBirthday);
        txtPatientAddress = view.findViewById(R.id.txtPatientAddress);
        txtPatientReason = view.findViewById(R.id.txtPatientReason);

        txtAppointmentDate = view.findViewById(R.id.txtAppointmentDate);
        txtAppointmentTime = view.findViewById(R.id.txtAppointmentTime);

        /*SET UP FORM*/
        txtBookingPhone.setText(user.getPhone());
        txtPatientBirthday.setText(user.getBirthday());
        txtPatientAddress.setText(user.getAddress());
        txtAppointmentDate.setText(Tooltip.getToday());
        txtAppointmentTime.setText(R.string.default_appointment_time);
    }

    /**
     * @since 23-11-2022
     * setup view model
     */
    private void setupViewModel()
    {
        /*Step 1 - declare*/
        BookingpageViewModel viewModel = new ViewModelProvider(this).get(BookingpageViewModel.class);
        viewModel.instantiate();

        /*Step 2 - prepare HTTP header*/
        Map<String, String> header = globalVariable.getHeaders();

        if(!Objects.equals(doctorId, "0"))
        {
            viewModel.doctorReadByID(header, doctorId);
        }
        else
        {
            viewModel.serviceReadById(header, serviceId);
        }





        /*Step 3 - animation & listen for response*/
        viewModel.getAnimation().observe((LifecycleOwner) context, aBoolean -> {
            if( aBoolean )
            {
                loadingScreen.start();
            }
            else
            {
                loadingScreen.stop();
            }
        });

        /*Step 4 - get service read by id response*/
        viewModel.getServiceReadByIdResponse().observe((LifecycleOwner) context, response->{
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
                    System.out.println(TAG + "- result: " + result);
                    dialog.announce();
                    dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                    dialog.btnOK.setOnClickListener(view->{
                        dialog.close();
                        activity.finish();
                    });
                }

            }
            catch(Exception ex)
            {
                /*Neu truy van lau qua ma khong nhan duoc phan hoi thi cung dong ung dung*/
                System.out.println(TAG);
                System.out.println("Exception: " + ex.getMessage());
                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view->{
                    dialog.close();
                    activity.finish();
                });
            }
        });/*end Step 4*/

        /*Step 5*/
        viewModel.getDoctorReadByIdResponse().observe((LifecycleOwner) context, response->{
            try
            {
                int result = response.getResult();
                /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                if( result == 1)
                {
                    Doctor doctor = response.getData();
                    printDoctorInformation(doctor);
                }
                /*result == 0 => thong bao va thoat ung dung*/
                if( result == 0)
                {
                    System.out.println(TAG + "- result: " + result);
                    dialog.announce();
                    dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                    dialog.btnOK.setOnClickListener(view->{
                        dialog.close();
                        activity.finish();
                    });
                }

            }
            catch(Exception ex)
            {
                /*Neu truy van lau qua ma khong nhan duoc phan hoi thi cung dong ung dung*/
                System.out.println(TAG);
                System.out.println("Exception: " + ex.getMessage());
                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view->{
                    dialog.close();
                    activity.finish();
                });
            }
        });/*end Step 4*/
        /*end Step 5*/
    }

    /**
     * @since 23-11-2022
     * print service information
     */
    private void printServiceInformation(Service service)
    {
        String name = service.getName();// for instance: Đặt lịch khám với bác sĩ Phong
        String image = Constant.UPLOAD_URI() + service.getImage();

        txtServiceName.setText(name);
        if( service.getImage().length() > 0)
        {
            Picasso.get().load(image).into(imgServiceAvatar);
        }
    }

    /**
     * @since 19-12-2022
     * print doctor information
     */
    private void printDoctorInformation(Doctor doctor)
    {
        String name = getString(R.string.create_booking)
                + " " + getString(R.string.with)
                + " " + getString(R.string.doctor)
                + " " + doctor.getName();// for instance: Đặt lịch khám với bác sĩ Phong
        String image = Constant.UPLOAD_URI() + doctor.getAvatar();

        txtServiceName.setText(name);
        if( doctor.getAvatar().length() > 0)
        {
            Picasso.get().load(image).into(imgServiceAvatar);
        }
    }

    /**
     * @since 23-11-2022
     * setup event
     */
    private void setupEvent(View view)
    {
        /*-************************PREPARE TIME & DATE PICKER FOR BUTTON**************************************/
        /*GET TODAY*/
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        /*DATE PICKER FOR BIRTHDAY - if day or month less than 10, we will insert 0 in front of the value*/
        DatePickerDialog.OnDateSetListener birthday = (view13, year1, month1, day1) -> {
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
            txtPatientBirthday.setText(output);
        };

        /*DATE PICKER FOR APPOINTMENT DATE - if day or month less than 10, we will insert 0 in front of the value*/
        DatePickerDialog.OnDateSetListener appointmentDateDialog = (view13, year1, month1, day1) -> {
            calendar.set(Calendar.YEAR, year1);
            calendar.set(Calendar.MONTH, month1);
            calendar.set(Calendar.DAY_OF_MONTH, day1);

            String dayFormatted = String.valueOf(day1);
            String monthFormatted = String.valueOf(month1+1);
            if( day1 < 10)
            {
                dayFormatted = "0" + day1;
            }
            if( month1 < 10 )
            {
                monthFormatted = "0" + month1;
            }
            String output = year1 + "-" + monthFormatted + "-" + dayFormatted;
            txtAppointmentDate.setText(output);
        };

        /*TIME PICKER FOR APPOINTMENT TIME*/
        TimePickerDialog.OnTimeSetListener appointmentTimeDialog = (timePicker, hour, minute) -> {
            String hourFormatted = String.valueOf(hour);
            String minuteFormatted = String.valueOf(minute);
            if(hour < 10)
            {
                hourFormatted = "0" + hour;
            }
            if( minute < 10)
            {
                minuteFormatted = "0" + minute;
            }
            String output = hourFormatted + ":" + minuteFormatted;
            txtAppointmentTime.setText(output);
        };


        /* *************************LISTEN CLICK EVENT FOR BUTTONS**************************************/
        /*EDIT TEXT BIRTHDAY*/
        txtPatientBirthday.setOnClickListener(birthdayView -> new DatePickerDialog(context,birthday,year,month,day).show());

        /*EDIT TEXT APPOINTMENT DATE*/
        txtAppointmentDate.setOnClickListener(appointmentDateView-> new DatePickerDialog(context, appointmentDateDialog, year, month, day).show());

        /*EDIT TEXT APPOINTMENT TIME*/
        txtAppointmentTime.setOnClickListener(appointmentTimeView-> new TimePickerDialog(context, appointmentTimeDialog, 9, 0, true).show() );

        /*BUTTON CONFIRM*/
        btnConfirm.setOnClickListener(view1->{
            /*Step 1 - user must fill up all mandatory fields*/
            boolean flag = areMandatoryFieldsFilledUp();
            if( !flag ){
                return;
            }

            /*Step 2 - get date that user enters*/
            String bookingName = txtBookingName.getText().toString();
            String bookingPhone = txtBookingPhone.getText().toString();
            String patientName = txtPatientName.getText().toString();

            int selectedId = rdPatientGender.getCheckedRadioButtonId();
            RadioButton radioButton = view.findViewById(selectedId);
            String patientGender = radioButton.getHint().toString();
            String patientAddress = txtPatientAddress.getText().toString();
            String patientReason = txtPatientReason.getText().toString();

            String patientBirthday = txtPatientBirthday.getText().toString();
            String appointmentDate = txtAppointmentDate.getText().toString();
            String appointmentTime = txtAppointmentTime.getText().toString();


            /*Step 3 - setup header and body for POST request*/
            Map<String, String> header = globalVariable.getHeaders();
            Map<String, String> body = new HashMap<>();
            body.put("serviceId", serviceId);
            body.put("doctorId", doctorId);
            body.put("bookingName", bookingName);
            body.put("bookingPhone", bookingPhone);
            body.put("name", patientName);
            body.put("gender", patientGender);
            body.put("address", patientAddress);
            body.put("reason", patientReason);
            body.put("birthday", patientBirthday);
            body.put("appointmentTime", appointmentTime);
            body.put("appointmentDate", appointmentDate);

            /*ở đây sẽ gửi trực tiếp POST request bằng retrofit để tránh việc tạo ra nhiều observer mỗi lần ấn nút gửi yêu cầu*/
            loadingScreen.start();
            sendBookingCreate(header, body);
        });/*end BUTTON CONFIRM*/
    }

    /**
     * @since 23-11-2022
     * are mandatory fields filled up?
     * YES, it returns TRUE
     * NO, it returns FALSE
     */
    private boolean areMandatoryFieldsFilledUp()
    {
        String bookingName = txtBookingName.getText().toString();
        String bookingPhone = txtBookingPhone.getText().toString();
        String patientName = txtPatientName.getText().toString();
        String appointmentDate = txtAppointmentDate.getText().toString();
        String appointmentTime = txtAppointmentTime.getText().toString();

        String[] requiredFields = { bookingName, bookingPhone, patientName, appointmentTime, appointmentDate };

        for (String element : requiredFields) {
            if (TextUtils.isEmpty(element)) {
                dialog.announce();
                dialog.show(R.string.attention, context.getString(R.string.you_do_not_fill_mandatory_field_try_again), R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view -> dialog.close());
                return false;
            }
        }
        return true;
    }


    private void sendBookingCreate(Map<String, String> header, Map<String,String> body)
    {
        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 3*/
        String serviceId = body.get("serviceId");
        String doctorId = body.get("doctorId");
        String bookingName = body.get("bookingName");
        String bookingPhone = body.get("bookingPhone");
        String name = body.get("name");
        String gender = body.get("gender");
        String address = body.get("address");
        String reason = body.get("reason");
        String birthday = body.get("birthday");
        String appointmentTime = body.get("appointmentTime");
        String appointmentDate = body.get("appointmentDate");

        Call<BookingCreate> container = api.bookingCreate(header, doctorId, serviceId,
                bookingName, bookingPhone, name, gender, address, reason, birthday, appointmentTime, appointmentDate);

        /*Step 4*/
        container.enqueue(new Callback<BookingCreate>() {
            @Override
            public void onResponse(@NonNull Call<BookingCreate> call, @NonNull Response<BookingCreate> response) {
                loadingScreen.stop();
                if(response.isSuccessful())
                {
                    BookingCreate content = response.body();
                    assert content != null;
                    processWithPOSTResponse(content);
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
            public void onFailure(@NonNull Call<BookingCreate> call, @NonNull Throwable t) {
                loadingScreen.stop();
                System.out.println("Booking Fragment - Create - error: " + t.getMessage());
            }
        });
    }

    /**
     * @since 23-11-2022
     * process with the response from POST request that we send to server
     */
    private void processWithPOSTResponse(BookingCreate response)
    {
        /*Step 1 - prepare Dialog if we have error*/
        dialog.announce();
        dialog.btnOK.setOnClickListener(view -> dialog.close());


        /*Step 2 - show result*/
        try
        {
            int result = response.getResult();
            if( result == 1)// create successfully -> go to next booking fragment
            {
                Toast.makeText(context, context.getString(R.string.success), Toast.LENGTH_SHORT).show();
                String fragmentTag = "bookingFragment3";

                Bundle bundle = new Bundle();
                bundle.putString("bookingId", String.valueOf(response.getData().getId()));


                BookingFragment3 nextFragment = new BookingFragment3();
                nextFragment.setArguments(bundle);


                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, nextFragment, fragmentTag)
                        .addToBackStack(fragmentTag)
                        .commit();

                HomepageActivity.getInstance().setNumberOnNotificationIcon();
            }
            else// create failed -> show error message
            {
                String message = response.getMsg();
                dialog.show(R.string.attention, message, R.drawable.ic_info);
            }
        }
        catch (Exception exception)
        {
            System.out.println(TAG);
            System.out.println(exception);
            dialog.show(R.string.attention, context.getString(R.string.oops_there_is_an_issue), R.drawable.ic_info);
        }
    }
}