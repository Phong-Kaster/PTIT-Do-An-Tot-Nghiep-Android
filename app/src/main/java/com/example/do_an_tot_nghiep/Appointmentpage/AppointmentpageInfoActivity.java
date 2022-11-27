package com.example.do_an_tot_nghiep.Appointmentpage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Model.Appointment;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Phong-Kaster
 * @since 27-11-2022
 * Appointment-page Info Activity
 * this activity shows full information about an appointment
 */
public class AppointmentpageInfoActivity extends AppCompatActivity {

    private final String TAG = "Appointment-page Info Activity";
    private Map<String, String> header;

    private Dialog dialog;
    private LoadingScreen loadingScreen;

    private String appointmentId;


    private CircleImageView imgDoctorAvatar;
    private TextView txtDoctorName;

    private TextView txtSpecialityName;
    private TextView txtLocation;

    private TextView txtPatientName;
    private TextView txtNumericalOrder;
    private TextView txtPatientBirthday;

    private TextView txtPosition;
    private TextView txtPatientPhone;
    private TextView txtPatientReason;

    private TextView txtAppointmentDate;
    private TextView txtAppointmentTime;

    private TextView txtStatusCancel;
    private TextView txtStatusDone;
    private TextView txtStatusProcessing;

    private AppCompatButton btnWatchMedicalRecord;
    private AppCompatButton btnWatchMedicalTreatment;

    private ImageButton btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointmentpage_info);

        setupComponent();
        setupViewModel();
        setupEvent();
    }

    /**
     * @since 27-11-2022
     * setup component
     */
    private void setupComponent()
    {
        GlobalVariable globalVariable = (GlobalVariable) this.getApplication();
        dialog = new Dialog(this);
        loadingScreen = new LoadingScreen(this);

        header = globalVariable.getHeaders();
        appointmentId = getIntent().getStringExtra("id");

        txtPosition = findViewById(R.id.txtPosition);
        imgDoctorAvatar = findViewById(R.id.imgDoctorAvatar);
        txtDoctorName = findViewById(R.id.txtDoctorName);

        txtSpecialityName = findViewById(R.id.txtSpecialityName);
        txtLocation = findViewById(R.id.txtLocation);

        txtPatientName = findViewById(R.id.txtPatientName);
        txtNumericalOrder = findViewById(R.id.txtNumericalOrder);
        txtPatientBirthday = findViewById(R.id.txtPatientBirthday);

        txtPatientPhone = findViewById(R.id.txtPatientPhone);
        txtPatientReason = findViewById(R.id.txtPatientReason);

        txtAppointmentDate = findViewById(R.id.txtDate);
        txtAppointmentTime = findViewById(R.id.txtAppointmentTime);

        txtStatusCancel = findViewById(R.id.txtStatusCancel);
        txtStatusDone = findViewById(R.id.txtStatusDone);
        txtStatusProcessing = findViewById(R.id.txtStatusProcessing);

        btnWatchMedicalRecord = findViewById(R.id.btnWatchMedicalRecord);
        btnWatchMedicalTreatment = findViewById(R.id.btnWatchMedicalTreatment);

        btnBack = findViewById(R.id.btnBack);
    }


    /**
     * @since 27-11-2022
     * setup view model
     */
    private void setupViewModel()
    {
        AppointmentpageViewModel viewModel = new ViewModelProvider(this).get(AppointmentpageViewModel.class);
        viewModel.instantiate();


        /*SEND REQUEST*/
        viewModel.readByID(header, appointmentId);
        viewModel.getReadByIDResponse().observe(this, response->{
            try
            {
                int result = response.getResult();
                /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                if( result == 1)
                {
                    Appointment appointment = response.getData();
                    printAppointmentInfo(appointment);
                }
                /*result == 0 => thong bao va thoat ung dung*/
                if( result == 0)
                {
                    System.out.println(TAG);
                    System.out.println("shut down by result == 0");
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
                System.out.println(TAG);
                System.out.println("shut down by exception");
                System.out.println(ex);
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
        });/*end ANIMATION*/
    }

    /**
     * @since 27-11-2022
     * print appointment info
     */
    private void printAppointmentInfo(Appointment appointment)
    {
        String doctorName = appointment.getDoctor().getName();
        String specialityName = appointment.getSpeciality().getName();

        String location = appointment.getRoom().getLocation() + ", " + appointment.getRoom().getName();
        int position = appointment.getPosition();

        String patientName = appointment.getPatientName();
        int numericalOrder = appointment.getNumericalOrder();

        String patientBirthday = appointment.getPatientBirthday();
        String patientPhone = appointment.getPatientPhone();

        String patientReason = appointment.getPatientReason();
        String appointmentDate = appointment.getDate();

        String appointmentTime = appointment.getAppointmentTime().length() > 0 ?  appointment.getAppointmentTime() : "" ;
        String status = appointment.getStatus();

//        System.out.println(TAG);
//        System.out.println("NECESSARY INFORMATION");
//        System.out.println("doctor name: " + doctorName);
//        System.out.println("doctor avatar: " + Constant.UPLOAD_URI() + appointment.getDoctor().getAvatar());
//        System.out.println("speciality Name: " +specialityName);
//        System.out.println("location: " + location);
//        System.out.println("position: " + position);
//        System.out.println("patient name: " +patientName);
//        System.out.println("numerical order: " + numericalOrder);
//        System.out.println("patient birthday: " + patientBirthday);
//        System.out.println("patient phone: " +patientPhone);
//        System.out.println("patient reason: " + patientReason);
//        System.out.println("appointment date: " + appointmentDate);
//        System.out.println("appointment time: " + appointmentTime);
//        System.out.println("status: " + status);

        /*DOCTOR INFO*/
        if( appointment.getDoctor().getAvatar().length()> 0)
        {
            String doctorAvatar = Constant.UPLOAD_URI() + appointment.getDoctor().getAvatar();
            Picasso.get().load(doctorAvatar).into(imgDoctorAvatar);
        }

        txtSpecialityName.setText(specialityName);
        txtDoctorName.setText(doctorName);
        txtLocation.setText(location);

        /*STATUS*/
        if(Objects.equals(status, "processing"))
        {
            txtStatusProcessing.setVisibility(View.VISIBLE);
            txtStatusDone.setVisibility(View.GONE);
            txtStatusCancel.setVisibility(View.GONE);
        }
        if(Objects.equals(status, "done"))
        {
            txtStatusProcessing.setVisibility(View.GONE);
            txtStatusDone.setVisibility(View.VISIBLE);
            txtStatusCancel.setVisibility(View.GONE);
        }
        if(Objects.equals(status, "cancelled"))
        {
            txtStatusProcessing.setVisibility(View.GONE);
            txtStatusDone.setVisibility(View.GONE);
            txtStatusCancel.setVisibility(View.VISIBLE);
        }

        /*OTHER INFORMATION*/
        txtPosition.setText(String.valueOf(position));
        txtPatientName.setText(patientName);
        txtNumericalOrder.setText(String.valueOf(numericalOrder));
        txtPatientBirthday.setText(patientBirthday);
        txtPatientPhone.setText(patientPhone);
        txtPatientReason.setText(patientReason);
        txtAppointmentDate.setText(appointmentDate);
        txtAppointmentTime.setText(appointmentTime);



        /*BUTTONS - show 2 buttons when status == DONE*/
        if(Objects.equals(status, "done"))
        {
            btnWatchMedicalTreatment.setVisibility(View.VISIBLE);
            btnWatchMedicalRecord.setVisibility(View.VISIBLE);
        }
        else
        {
            btnWatchMedicalTreatment.setVisibility(View.GONE);
            btnWatchMedicalRecord.setVisibility(View.GONE);
        }
    }

    /**
     * @since 27-11-2022
     * setup event
     */
    private void setupEvent()
    {
        /*BUTTON BACK*/
        btnBack.setOnClickListener(view->finish());

        /*BUTTON WATCH MEDICAL TREATMENT*/
        btnWatchMedicalTreatment.setOnClickListener(view-> Toast.makeText(this, "BUTTON WATCH MEDICAL TREATMENT", Toast.LENGTH_SHORT).show());

        /*BUTTON WATCH MEDICAL RECORD*/
        btnWatchMedicalRecord.setOnClickListener(view -> Toast.makeText(this, "BUTTON WATCH MEDICAL RECORD", Toast.LENGTH_SHORT).show());
    }
}