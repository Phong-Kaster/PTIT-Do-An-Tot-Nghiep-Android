package com.example.do_an_tot_nghiep.Notificationpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Homepage.HomepageViewModel;
import com.example.do_an_tot_nghiep.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 18-11-2022
 */
public class NotificationFragment extends Fragment {

    private final String TAG = "NotificationFragment";

    private AppCompatButton btnSet;
    private AppCompatButton btnCancel;

    private GlobalVariable globalVariable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        setupComponent(view);
        setupViewModel();
        setupEvent();

        return view;
    }




    private void setupComponent(View view)
    {
        btnSet = view.findViewById(R.id.btnSet);
        btnCancel = view.findViewById(R.id.btnCancel);

        globalVariable = (GlobalVariable) requireActivity().getApplication();
    }


    private void setupViewModel()
    {
        /*Step 1 - declare*/
        HomepageViewModel viewModel = new ViewModelProvider(this).get(HomepageViewModel.class);
        viewModel.instantiate();

        /*Step 2 - prepare header & parameters*/
        Map<String, String> header = globalVariable.getHeaders();
;

        /*Step 3 - listen speciality Read All */
        Map<String, String> paramsSpeciality = new HashMap<>();
        viewModel.specialityReadAll(header, paramsSpeciality);
        viewModel.getSpecialityReadAllResponse().observe(getViewLifecycleOwner(), response->{
            int result = response.getResult();
            int quantity = response.getQuantity();
            System.out.println(TAG);
            System.out.println("result: " +result);
            System.out.println("quantity: " + quantity);

        });

    }



    private void setupEvent()
    {
        btnSet.setOnClickListener(view->{
            System.out.println("BUTTON SET CLICKED");
        });

        /*BUTTON CANCEL*/
        btnCancel.setOnClickListener(view->{
            System.out.println("BUTTON CANCEL CLICKED");
        });
    }





}