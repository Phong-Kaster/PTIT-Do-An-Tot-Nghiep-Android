package com.example.do_an_tot_nghiep.Bookingpage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.Model.Service;
import com.example.do_an_tot_nghiep.Model.User;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class BookingFragment1 extends Fragment {

    private final String TAG = "BookingFragment1";

    private String serviceId;
    private GlobalVariable globalVariable;
    private LoadingScreen loadingScreen;

    private Dialog dialog;
    private BookingpageViewModel viewModel;

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
    private RadioButton rdMale;
    private RadioButton rdFemale;
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
        serviceId = bundle.getString("serviceId");


        /*FORM*/
        imgServiceAvatar = view.findViewById(R.id.imgServiceAvatar);
        txtServiceName = view.findViewById(R.id.txtServiceName);
        btnConfirm = view.findViewById(R.id.btnConfirm);

        txtBookingName = view.findViewById(R.id.txtBookingName);
        txtBookingPhone = view.findViewById(R.id.txtBookingPhone);
        txtPatientName = view.findViewById(R.id.txtPatientName);

        rdPatientGender = view.findViewById(R.id.rdPatientGender);
        rdMale = view.findViewById(R.id.rdMale);
        rdFemale = view.findViewById(R.id.rdFemale);

        txtPatientBirthday = view.findViewById(R.id.txtPatientBirthday);
        txtPatientAddress = view.findViewById(R.id.txtPatientAddress);
        txtPatientReason = view.findViewById(R.id.txtPatientReason);

        txtAppointmentDate = view.findViewById(R.id.txtAppointmentDate);
        txtAppointmentTime = view.findViewById(R.id.txtAppointmentTime);

        /*SET UP FORM*/
        System.out.println(TAG);
        txtBookingPhone.setText(user.getPhone());
        txtPatientBirthday.setText(user.getBirthday());
        txtPatientAddress.setText(user.getAddress());
        txtAppointmentDate.setText(Tooltip.getToday());
    }

    /**
     * @since 23-11-2022
     * setup view model
     */
    private void setupViewModel()
    {
        /*Step 1 - declare*/
        viewModel = new ViewModelProvider(this).get(BookingpageViewModel.class);
        viewModel.instantiate();

        /*Step 2 - prepare HTTP header*/
        Map<String, String> header = globalVariable.getHeaders();
        viewModel.serviceReadById(header, serviceId);

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
                System.out.println(TAG + "- exception: " + ex.getMessage());
                dialog.announce();
                dialog.show(R.string.attention, getString(R.string.check_your_internet_connection), R.drawable.ic_info);
                dialog.btnOK.setOnClickListener(view->{
                    dialog.close();
                    activity.finish();
                });
            }
        });/*end get service read by id response*/
    }

    /**
     * @since 23-11-2022
     * print service information
     */
    private void printServiceInformation(Service service)
    {
        String name = service.getName();
        String image = Constant.UPLOAD_URI() + service.getImage();

        txtServiceName.setText(name);
        if( service.getImage().length() > 0)
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
        /*BUTTON CONFIRM*/
        btnConfirm.setOnClickListener(view1->{
//            String fragmentTag = "bookingFragment2";
//            BookingFragment2 nextFragment = new BookingFragment2();
//            requireActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.frameLayout, nextFragment, fragmentTag)
//                    .addToBackStack(fragmentTag)
//                    .commit();

            // get patient gender
            int selectedId = rdPatientGender.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
            String patientGender = radioButton.getHint().toString();

            Toast.makeText(context, patientGender, Toast.LENGTH_SHORT).show();

        });/*end BUTTON CONFIRM*/
    }
}