package com.example.do_an_tot_nghiep.Settingspage;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.do_an_tot_nghiep.Model.Setting;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.RecyclerView.SettingRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Phong-Kaster
 * @since 30-11-2022
 * Settings Fragment shows all settings: language, appearance,.....
 */
public class SettingsFragment extends Fragment {

    private RecyclerView settingRecyclerView;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        setupComponent(view);
        setupRecyclerView();
        return view;
    }

    /**
     * @since 30-11-2022
     * setup component
     */
    private void setupComponent(View view)
    {
        context = requireContext();
        settingRecyclerView = view.findViewById(R.id.settingRecyclerView);
    }

    /**
     * @since 30-11-2022
     * setup recycler view
     */
    private void setupRecyclerView()
    {
        Setting setting0 = new Setting(R.drawable.ic_personal_information, "personalInformation", getString(R.string.personal_information) );
        Setting setting1 = new Setting(R.drawable.ic_appearance, "appearance", getString(R.string.appearance) );
        Setting setting2 = new Setting(R.drawable.ic_appointment_history, "appointmentHistory", getString(R.string.appointment_history) );
        Setting setting3 = new Setting(R.drawable.ic_exit, "exit", getString(R.string.exit) );
        Setting setting4 = new Setting(R.drawable.ic_umbrella_round, "aboutUs", getString(R.string.about_us) );
        Setting setting5 = new Setting(R.drawable.ic_email_us, "emailUs", context.getString(R.string.email_us));

        List<Setting> list = new ArrayList<>();
        list.add(setting0);
        list.add(setting1);
        list.add(setting2);
        list.add(setting3);
        list.add(setting4);
        list.add(setting5);

        SettingRecyclerView settingAdapter = new SettingRecyclerView(context, list);
        settingRecyclerView.setAdapter(settingAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        settingRecyclerView.setLayoutManager(manager);
    }
}