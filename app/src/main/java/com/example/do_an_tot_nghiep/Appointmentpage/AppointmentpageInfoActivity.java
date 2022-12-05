package com.example.do_an_tot_nghiep.Appointmentpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.AppointmentReadByID;
import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.Model.Appointment;
import com.example.do_an_tot_nghiep.Model.Queue;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.Recordpage.RecordpageActivity;
import com.example.do_an_tot_nghiep.RecyclerView.AppointmentQueueRecyclerView;
import com.example.do_an_tot_nghiep.Treatmentpage.TreatmentpageActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

    /*Data from recycler view*/
    private String appointmentId;// is the id of appointment that the patient are waiting for his/her turn
    private String myPosition;// is the patient position in queue
    private String doctorId;
    private boolean appointmentStatus = true;// is appointment status == false, we hide recycler view appointment queue


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
    private RecyclerView appointmentQueueRecyclerView;
    private TextView appointmentQueueTitle;

    private AppointmentpageViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointmentpage_info);

        setupComponent();
        setupViewModel();
        setupEvent();
        setupUpdateAutomatically();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( appointmentStatus )
        {
            getAppointmentQueue();
        }
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

        /*data from appointment recycler view*/
        appointmentId = getIntent().getStringExtra("id");
        myPosition = getIntent().getStringExtra("position");
        doctorId = getIntent().getStringExtra("doctorId");

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
        appointmentQueueRecyclerView = findViewById(R.id.appointmentQueueRecyclerView);
        appointmentQueueTitle = findViewById(R.id.appointmentQueueTitle);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }


    /**
     * @since 27-11-2022
     * setup view model
     *
     * if the user appointment is not PROCESSING, we hide recycler view appointment queue
     * and never request current appointment queue.
     */
    private void setupViewModel()
    {
        viewModel = new ViewModelProvider(this).get(AppointmentpageViewModel.class);
        viewModel.instantiate();


        /*SEND REQUEST READ BY ID*/
        loadingScreen.start();
        requestAppointmentInfo(header, appointmentId);


        /*SEND REQUEST - get appointment queue only when appointment status == TRUE*/
        if(appointmentStatus)
        {
            getAppointmentQueue();
            viewModel.getAppointmentQueueResponse().observe(this, response -> {
                try {
                    int result = response.getResult();
                    /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                    if (result == 1) {
                        List<Queue> list = response.getData();
                        setupAppointmentQueueRecyclerView(list);
                    }
                    /*result == 0 => thong bao va thoat ung dung*/
                    if (result == 0) {
                        System.out.println(TAG);
                        System.out.println("READ ALL");
                        System.out.println("shut down by result == 0");
                        dialog.announce();
                        dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                        dialog.btnOK.setOnClickListener(view -> {
                            dialog.close();
                            finish();
                        });
                    }

                } catch (Exception ex) {
                    System.out.println(TAG);
                    System.out.println("READ ALL");
                    System.out.println("shut down by exception");
                    System.out.println(ex);
                    /*Neu truy van lau qua ma khong nhan duoc phan hoi thi cung dong ung dung*/
                    dialog.announce();
                    dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                    dialog.btnOK.setOnClickListener(view -> {
                        dialog.close();
                        finish();
                    });
                }
            });
        }
        /*end SEND REQUEST - READ ALL*/

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
     * send request to get appointment info
     */
    private void requestAppointmentInfo(Map<String, String> header, String appointmentId)
    {
        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<AppointmentReadByID> container = api.appointmentReadByID(header, appointmentId);

        /*Step 4*/
        container.enqueue(new Callback<AppointmentReadByID>() {
            @Override
            public void onResponse(@NonNull Call<AppointmentReadByID> call, @NonNull Response<AppointmentReadByID> response) {
                loadingScreen.stop();
                if(response.isSuccessful())
                {
                    AppointmentReadByID content = response.body();
                    assert content != null;
                    Appointment appointment = content.getData();
                    printAppointmentInfo(appointment);
//                    System.out.println(TAG);
//                    System.out.println("result: " + content.getResult());
//                    System.out.println("msg: " + content.getMsg());
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
            public void onFailure(@NonNull Call<AppointmentReadByID> call, @NonNull Throwable t) {
                System.out.println(TAG);
                System.out.println("Read By ID - error: " + t.getMessage());
            }
        });
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

        String appointmentTime = appointment.getAppointmentTime().length() > 0 ?  appointment.getAppointmentTime() : getString(R.string.none) ;
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
            appointmentStatus = true;// we show recycler view appointment queue and send GET request to server
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            appointmentQueueRecyclerView.setVisibility(View.VISIBLE);
            appointmentQueueTitle.setVisibility(View.VISIBLE);
        }
        if(Objects.equals(status, "done"))
        {
            txtStatusProcessing.setVisibility(View.GONE);
            txtStatusDone.setVisibility(View.VISIBLE);
            txtStatusCancel.setVisibility(View.GONE);
            appointmentStatus = false;// we hide recycler view appointment queue and never send GET request to server
            swipeRefreshLayout.setVisibility(View.GONE);
            appointmentQueueRecyclerView.setVisibility(View.GONE);
            appointmentQueueTitle.setVisibility(View.GONE);
        }
        if(Objects.equals(status, "cancelled"))
        {
            txtStatusProcessing.setVisibility(View.GONE);
            txtStatusDone.setVisibility(View.GONE);
            txtStatusCancel.setVisibility(View.VISIBLE);
            appointmentStatus = false;// we hide recycler view appointment queue and never send GET request to server
            swipeRefreshLayout.setVisibility(View.GONE);
            appointmentQueueRecyclerView.setVisibility(View.GONE);
            appointmentQueueTitle.setVisibility(View.GONE);
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
        btnWatchMedicalTreatment.setOnClickListener(view-> {
            Intent intent = new Intent(this, TreatmentpageActivity.class);
            intent.putExtra("appointmentId", appointmentId);
            this.startActivity(intent);
        });

        /*BUTTON WATCH MEDICAL RECORD*/
        btnWatchMedicalRecord.setOnClickListener(view -> {
            Intent intent = new Intent(this, RecordpageActivity.class);
            intent.putExtra("appointmentId", appointmentId);
            this.startActivity(intent);
        });

        /*SWIPE REFRESH LAYOUT*/
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            getAppointmentQueue();
        });
    }

    /**
     * @since 28-11-2022
     * setup appointment queue recycler view
     *
     * myPosition is the position of the patient who are waiting for his/her turn
     *
     */
    private void setupAppointmentQueueRecyclerView(List<Queue> list)
    {
        AppointmentQueueRecyclerView appointmentAdapter = new AppointmentQueueRecyclerView(this, list, Integer.parseInt(myPosition));
        appointmentQueueRecyclerView.setAdapter(appointmentAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        appointmentQueueRecyclerView.setLayoutManager(manager);
    }

    /**
     * @since 28-11-2022
     * this function will run update view model - GET APPOINTMENT QUEUE - every 45 seconds if the device is active
     */
    final Handler handler = new Handler();
    final int delay = 1000  * 45 ; // delay 45 seconds
    private void setupUpdateAutomatically()
    {
        handler.postDelayed(new Runnable() {
            public void run() {
                getAppointmentQueue();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    /**
     * @since 28-11-2022
     * this function send GET request to get current appointment queue
     *
     * we just send GET quest when the "appointmentStatus" flag == TRUE
     */
    private void getAppointmentQueue()
    {
        if(!appointmentStatus)
        {
            return;
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put("doctor_id", doctorId);
        parameters.put("date", Tooltip.getToday());
        parameters.put("order[column]", "position" );
        parameters.put("order[dir]", "asc");
        parameters.put("length", "3");
        parameters.put("status", "processing");
        viewModel.getQueue(header, parameters);

        /*get latest information from appointment*/
        requestAppointmentInfo(header, appointmentId);
    }
}