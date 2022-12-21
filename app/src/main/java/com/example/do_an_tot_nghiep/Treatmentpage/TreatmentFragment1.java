package com.example.do_an_tot_nghiep.Treatmentpage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_tot_nghiep.Alarmpage.AlarmpageFragment;
import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.LoadingScreen;
import com.example.do_an_tot_nghiep.Model.Treatment;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.RecyclerView.TreatmentRecyclerView;

import java.util.List;
import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 30-11-2022
 *
 * this fragment will show all appointments to users selects one of them
 */
public class TreatmentFragment1 extends Fragment {

    private final String TAG = "Treatment Fragment 1";
    private Context context;
    private Activity activity;
    private Dialog dialog;
    private LoadingScreen loadingScreen;

    private Map<String, String> header ;

    private TreatmentpageViewModel viewModel;
    private RecyclerView treatmentRecyclerView;
    private String appointmentId;


    private AppCompatButton btnSetTime;
    private String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_treatment1, container, false);

        setupComponent(view);
        setupViewModel();
        setupEvent();
        return view;
    }

    /**
     * @since 30-11-2022
     * setup component()
     */
    private void setupComponent(View view)
    {
        context = requireContext();
        activity = requireActivity();
        dialog = new Dialog(context);
        loadingScreen = new LoadingScreen(activity);

        GlobalVariable globalVariable = (GlobalVariable) activity.getApplication();

        appointmentId = activity.getIntent().getStringExtra("appointmentId");

        treatmentRecyclerView = view.findViewById(R.id.treatmentRecyclerView);
        header = globalVariable.getHeaders();

        btnSetTime = view.findViewById(R.id.btnSetAlarm);
    }

    private void setupViewModel()
    {
        viewModel = new ViewModelProvider(this).get(TreatmentpageViewModel.class);
        viewModel.instantiate();

        /*prepare Header & parameter*/
        viewModel.treatmentReadAll(header, appointmentId);
        viewModel.getTreatmentReadAllResponse().observe((LifecycleOwner) context, response->{
            try
            {
                int result = response.getResult();
                /*result == 1 => luu thong tin nguoi dung va vao homepage*/
                if( result == 1)
                {
                    List<Treatment> treatments = response.getData();
                    message = treatments.get(0).getInstruction();
                    setupRecyclerView(treatments);
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
            }
        });

        /*animation*/
        viewModel.getAnimation().observe((LifecycleOwner) context, aBoolean -> {
            if(aBoolean)
            {
                loadingScreen.start();
            }
            else
            {
                loadingScreen.stop();
            }
        });
        /*end animation*/
    }

    /**
     * @since 26-11-2022
     * setup recycler view
     */
    private void setupRecyclerView(List<Treatment> list)
    {
        TreatmentRecyclerView treatmentAdapter = new TreatmentRecyclerView(context, list);
        treatmentRecyclerView.setAdapter(treatmentAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        treatmentRecyclerView.setLayoutManager(manager);

    }

    /**
     * set up event
     * @since 30-11-2022
     */
    private void setupEvent()
    {
        btnSetTime.setOnClickListener(view -> {
            String fragmentTag = "AlarmFragment";
            Fragment nextFragment = new AlarmpageFragment();// this fragment creates alarm

            Bundle bundle = new Bundle();
            bundle.putString("message", message);
            nextFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, nextFragment, fragmentTag)
                    .addToBackStack(fragmentTag)
                    .commit();
        });
    }
}