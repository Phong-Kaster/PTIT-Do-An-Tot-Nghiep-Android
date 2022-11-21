package com.example.do_an_tot_nghiep.Searchpage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Container.DoctorReadAll;
import com.example.do_an_tot_nghiep.Container.ServiceReadAll;
import com.example.do_an_tot_nghiep.Container.SpecialityReadAll;
import com.example.do_an_tot_nghiep.Repository.DoctorRepository;
import com.example.do_an_tot_nghiep.Repository.ServiceRepository;
import com.example.do_an_tot_nghiep.Repository.SpecialityRepository;

import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 21-11-2022
 * Search-page view model
 */
public class SearchpageViewModel extends ViewModel {

    private MutableLiveData<SpecialityReadAll> specialityReadAll = new MutableLiveData<>();
    private MutableLiveData<DoctorReadAll> doctorReadAllResponse = new MutableLiveData<>();
    private MutableLiveData<ServiceReadAll> serviceReadAllResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> animation = new MutableLiveData<>();


    private SpecialityRepository specialityRepository;
    private DoctorRepository doctorRepository;
    private ServiceRepository serviceRepository;

    /*GETTER*/
    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }

    public MutableLiveData<SpecialityReadAll> getSpecialityReadAll() {
        return specialityReadAll;
    }

    public MutableLiveData<DoctorReadAll> getDoctorReadAllResponse() {
        return doctorReadAllResponse;
    }

    public MutableLiveData<ServiceReadAll> getServiceReadAllResponse() {
        return serviceReadAllResponse;
    }

    /**
     * @since 21-11-2022
     * create repository
     */
    public void instantiate()
    {
        if(specialityRepository == null)
        {
            specialityRepository = new SpecialityRepository();
        }
        if( doctorRepository == null)
        {
            doctorRepository = new DoctorRepository();
        }
        if( serviceRepository == null )
        {
            serviceRepository = new ServiceRepository();
        }
    }


    /* ******************** DOCTOR READ ALL ********************/
    public void doctorReadAll(Map<String, String> headers, Map<String, String> parameters)
    {
        doctorReadAllResponse = doctorRepository.readAll(headers, parameters);
        animation = doctorRepository.getAnimation();
    }

    /* ******************** SPECIALITY READ ALL ********************/
    public void specialityReadAll(Map<String, String> headers, Map<String, String> parameters)
    {
        specialityReadAll = specialityRepository.readAll(headers, parameters);
        animation = doctorRepository.getAnimation();
    }

    /* ******************** SERVICE READ ALL ********************/
    public void serviceReadAll(Map<String, String> headers, Map<String, String> parameters)
    {
        serviceReadAllResponse = serviceRepository.readAll(headers, parameters);
        animation = serviceRepository.getAnimation();
    }
}
