package com.example.do_an_tot_nghiep.Searchpage;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_tot_nghiep.Adapter.FilterOptionAdapter;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.Model.Doctor;
import com.example.do_an_tot_nghiep.Model.Option;
import com.example.do_an_tot_nghiep.Model.Service;
import com.example.do_an_tot_nghiep.Model.Speciality;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.RecyclerView.DoctorRecyclerView;
import com.example.do_an_tot_nghiep.RecyclerView.ServiceRecyclerView;
import com.example.do_an_tot_nghiep.RecyclerView.SpecialityRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Phong-Kaster
 * @since 21-11-2022
 * Search page activity
 */
public class SearchpageActivity extends AppCompatActivity {

    private final String TAG = "Search-page Activity";
    private ImageButton btnBack;
    private Spinner sprFilter;
    private SearchView searchView;

    private GlobalVariable globalVariable;
    private SearchpageViewModel viewModel;

    private Map<String, String> header;
    private final Map<String, String> paramsDoctor = new HashMap<>();
    private final Map<String, String> paramsSpeciality = new HashMap<>();
    private final Map<String, String> paramsService = new HashMap<>();

    /*filterKey is the way to know what kind of information users wanna search*/
    private String filterKey;

    private RecyclerView doctorRecyclerView;
    private RecyclerView specialityRecyclerView;
    private RecyclerView serviceRecyclerView;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);

        setupComponent();
        setupFilterSpinner();

        /*this block of code below - it handles event click on "READ MORE" button from HomepageActivity*/
        if(getIntent().getStringExtra("filterKey") != null)
        {
            filterKey = getIntent().getStringExtra("filterKey");

            String service = this.getString(R.string.service);
            String speciality = this.getString(R.string.speciality);
            String doctor = this.getString(R.string.doctor);

            int position = 0;
            if(Objects.equals(filterKey, service))
            {
                // do nothing
            }
            else if(Objects.equals(filterKey, speciality))
            {
                position = 1;
            }
            else
            {
                position = 2;
            }
            sprFilter.setSelection(position);
        }

        setupViewModel();
        setupEvent();

    }


    /**
     * @since 21-11-2022
     * setup component
     */
    private void setupComponent()
    {
        btnBack = findViewById(R.id.btnBack);
        sprFilter = findViewById(R.id.sprFilter);
        searchView = findViewById(R.id.searchView);

        globalVariable =  (GlobalVariable) this.getApplication();
        sharedPreferences = this.getApplication()
                .getSharedPreferences(globalVariable.getSharedReferenceKey(), MODE_PRIVATE);
        header = globalVariable.getHeaders();

        filterKey = this.getString(R.string.service);



        doctorRecyclerView = findViewById(R.id.doctorRecyclerView);
        specialityRecyclerView = findViewById(R.id.specialityRecyclerView);
        serviceRecyclerView = findViewById(R.id.serviceRecyclerView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Tooltip.setLocale(this, sharedPreferences);
    }

    /**
     * @since 21-11-2022
     * set options for spinner
     */
    private void setupFilterSpinner()
    {
        List<Option> filterOptions = globalVariable.getFilterOptions();
        FilterOptionAdapter filterAdapter = new FilterOptionAdapter(this, filterOptions);
        sprFilter.setAdapter(filterAdapter);
        sprFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterKey = filterOptions.get(position).getName();
                sendRequestWithFilterKey();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }


    /**
     * @since 21-11-2022
     * setup event
     */
    private void setupEvent()
    {
        /*BUTTON BACK*/
        btnBack.setOnClickListener(view -> finish());

        /*BUTTON SEARCH*/
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                paramsDoctor.put("search", query);
                paramsService.put("search", query);
                paramsSpeciality.put("search", query);
                sendRequestWithFilterKey();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if( newText.equals(""))
                {
                    paramsDoctor.put("search", "");
                    paramsService.put("search", "");
                    paramsSpeciality.put("search", "");
                    sendRequestWithFilterKey();
                    searchView.clearFocus();
                }
                return false;
            }
        });
    }


    /**
     * @since 21-11-2022
     */
    private void setupViewModel()
    {
        viewModel = new ViewModelProvider(this).get(SearchpageViewModel.class);
        viewModel.instantiate();

        /* *****************DOCTOR******************/
        paramsDoctor.put("length", "100");// day la mot params co the truyen trong API nay
        viewModel.doctorReadAll(header, paramsDoctor);
        viewModel.getDoctorReadAllResponse().observe(this, response->{
            int result = response.getResult();
            if( result == 1)
            {

                List<Doctor> list = response.getData();
                setupRecyclerViewDoctor(list);
            }
        });

        /* *****************SPECIALITY******************/
        paramsSpeciality.put("length", "100");// day la mot params co the truyen trong API nay
        viewModel.specialityReadAll(header, paramsSpeciality);
        viewModel.getSpecialityReadAll().observe(this, response->{
            int result = response.getResult();
            if(result == 1)
            {
                List<Speciality> list = response.getData();
                setupRecyclerViewSpeciality(list);
            }
        });

        /* *****************SERVICE******************/
        paramsService.put("length", "100");// day la mot params co the truyen trong API nay
        viewModel.serviceReadAll(header, paramsService);
        viewModel.getServiceReadAllResponse().observe(this, response->{
            int result = response.getResult();
            if(result == 1)
            {
                List<Service> list = response.getData();
                setupRecyclerViewService(list);
            }
        });
    }

    /**
     * @since 21-11-2022
     * filterKey is the way to know what kind of information users wanna search
     */
    @SuppressLint("NotifyDataSetChanged")
    private void sendRequestWithFilterKey()
    {
        List<Option> options = globalVariable.getFilterOptions();
        String option1 = options.get(0).getName();// service
        String option2 = options.get(1).getName();// speciality
        String option3 = options.get(2).getName();// doctor


        if( filterKey.equals(option2))
        {
            viewModel.specialityReadAll(header, paramsSpeciality);
            doctorRecyclerView.setVisibility(View.GONE);
            specialityRecyclerView.setVisibility(View.VISIBLE);
            serviceRecyclerView.setVisibility(View.GONE);
        }
        else if( filterKey.equals(option3))
        {
            viewModel.serviceReadAll(header, paramsService);
            doctorRecyclerView.setVisibility(View.VISIBLE);
            specialityRecyclerView.setVisibility(View.GONE);
            serviceRecyclerView.setVisibility(View.GONE);
        }
        else// by default, find service
        {
            viewModel.doctorReadAll(header, paramsDoctor);
            doctorRecyclerView.setVisibility(View.GONE);
            specialityRecyclerView.setVisibility(View.GONE);
            serviceRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * @since 21-11-2022
     * @param list is the list of doctor
     */
    private void setupRecyclerViewDoctor(List<Doctor> list)
    {
        DoctorRecyclerView doctorAdapter = new DoctorRecyclerView(this, list);
        doctorRecyclerView.setAdapter(doctorAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        doctorRecyclerView.setLayoutManager(manager);
    }

    /**
     * @since 21-11-2022
     * @param list is the list of speciality
     */
    private void setupRecyclerViewSpeciality(List<Speciality> list)
    {
        SpecialityRecyclerView specialityAdapter = new SpecialityRecyclerView(this, list, R.layout.recycler_view_element_speciality_2);
        specialityRecyclerView.setAdapter(specialityAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        specialityRecyclerView.setLayoutManager(manager);
    }

    /**
     * since 21-11-2022
     * @param list is the list of service
     */
    private void setupRecyclerViewService(List<Service> list)
    {
        ServiceRecyclerView serviceAdapter = new ServiceRecyclerView(this, list);
        serviceRecyclerView.setAdapter(serviceAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        serviceRecyclerView.setLayoutManager(manager);
    }
}