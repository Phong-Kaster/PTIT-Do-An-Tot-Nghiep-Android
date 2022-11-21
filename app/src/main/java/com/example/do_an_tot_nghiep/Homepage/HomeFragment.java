package com.example.do_an_tot_nghiep.Homepage;

import static android.content.Context.ALARM_SERVICE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.do_an_tot_nghiep.Configuration.QueueBroadcastReceiver;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Model.Doctor;
import com.example.do_an_tot_nghiep.Model.Speciality;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.RecyclerView.DoctorRecyclerView;
import com.example.do_an_tot_nghiep.RecyclerView.SpecialityRecyclerView;
import com.example.do_an_tot_nghiep.Searchpage.SearchpageActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 17-11-2022
 * Home fragment
 */
public class HomeFragment extends Fragment{


    private final String TAG = "Home Fragment";
    private HomepageViewModel viewModel;
    private GlobalVariable globalVariable;

    private RecyclerView recyclerViewSpeciality;
    private SpecialityRecyclerView specialityAdapter;

    private RecyclerView recyclerViewDoctor;
    private DoctorRecyclerView doctorAdapter;

    /*EXAMINATION BUTTON*/
    private AppCompatImageButton btnExamSpeciality;
    private AppCompatImageButton btnExamHeart;
    private AppCompatImageButton btnExamPregnant;

    private EditText searchBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        setupComponent(view);
        setupViewModel();
        setupEvent();

        return view;
    }



    /**
     * @since 17-11-2022
     * setup component
     */
    private void setupComponent(View view)
    {
        globalVariable = (GlobalVariable) requireActivity().getApplication();
        recyclerViewSpeciality = view.findViewById(R.id.recyclerViewSpeciality);
        recyclerViewDoctor = view.findViewById(R.id.recyclerViewDoctor);


        btnExamSpeciality = view.findViewById(R.id.btnExamSpeciality);
        btnExamHeart = view.findViewById(R.id.btnExamHeart);
        btnExamPregnant = view.findViewById(R.id.btnExamPregnant);

        searchBar = view.findViewById(R.id.searchBar);
    }

    /**
     * @since 17-11-2022
     * setup View model
     */
    private void setupViewModel()
    {
        /*Step 1 - declare*/
        viewModel = new ViewModelProvider(this).get(HomepageViewModel.class);
        viewModel.instantiate();

        /*Step 2 - prepare header & parameters*/
        Map<String, String> header = globalVariable.getHeaders();
        header.put("type", "patient");


        /*Step 3 - listen speciality Read All */
        Map<String, String> paramsSpeciality = new HashMap<>();
        viewModel.specialityReadAll(header, paramsSpeciality);

        viewModel.getSpecialityReadAllResponse().observe(getViewLifecycleOwner(), response->{
            int result = response.getResult();
            if( result == 1)
            {
                List<Speciality> list = response.getData();
                setupRecyclerViewSpeciality(list);
            }
        });

        /*Step 4 - listen doctor read all*/
        Map<String, String> paramsDoctor = new HashMap<>();
        viewModel.doctorReadAll(header, paramsDoctor);

//        for (Map.Entry<String,String> entry : paramsDoctor.entrySet())
//            System.out.println("Key = " + entry.getKey() +
//                    ", Value = " + entry.getValue());
//
//        for (Map.Entry<String,String> entry : header.entrySet())
//            System.out.println("Key = " + entry.getKey() +
//                    ", Value = " + entry.getValue());

        viewModel.getDoctorReadAllResponse().observe(getViewLifecycleOwner(), response->{
            int result = response.getResult();
            if( result == 1)
            {
                List<Doctor> list = response.getData();
                setupRecyclerViewDoctor(list);
            }
        });
    }

    /**
     * @since 17-11-2022
     * setup recycler view speciality
     */
    private void setupRecyclerViewSpeciality(List<Speciality> list)
    {
        specialityAdapter = new SpecialityRecyclerView(requireActivity(), list);
        recyclerViewSpeciality.setAdapter(specialityAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSpeciality.setLayoutManager(manager);
    }


    /**
     * @since 17-11-2022
     * setup recycler view doctor
     */
    private void setupRecyclerViewDoctor(List<Doctor> list)
    {
        doctorAdapter = new DoctorRecyclerView(requireActivity(), list);
        recyclerViewDoctor.setAdapter(doctorAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDoctor.setLayoutManager(manager);
    }


    /**
     * @since 18-11-2022
     * setup event for buttons
     */
    @SuppressLint({"UnspecifiedImmutableFlag", "ShortAlarm"})
    private void setupEvent()
    {
        /*SEARCH BAR*/
        searchBar.setOnClickListener(view->{
            Intent intent = new Intent(requireContext(), SearchpageActivity.class);
            startActivity(intent);
        });

        /*BUTTON EXAM SPECIALITY*/
        btnExamSpeciality.setOnClickListener(view->{

        });/*end BUTTON EXAM SPECIALITY*/

        /*BUTTON EXAM HEART - set alarm*/
        btnExamHeart.setOnClickListener(view->{

        });

        /*BUTTON EXAM PREGNANT - cancel alarm*/
        btnExamPregnant.setOnClickListener(view->{

        });
    }
}