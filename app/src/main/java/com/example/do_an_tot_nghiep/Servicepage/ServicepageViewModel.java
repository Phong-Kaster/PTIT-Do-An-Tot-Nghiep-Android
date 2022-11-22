package com.example.do_an_tot_nghiep.Servicepage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Container.DoctorReadAll;
import com.example.do_an_tot_nghiep.Container.ServiceReadByID;
import com.example.do_an_tot_nghiep.Container.SpecialityReadByID;
import com.example.do_an_tot_nghiep.Repository.DoctorRepository;
import com.example.do_an_tot_nghiep.Repository.ServiceRepository;
import com.example.do_an_tot_nghiep.Repository.SpecialityRepository;

import java.util.Map;

/**
 * @author  Phong-Kaster
 * @since 22-11-2022
 * Service-page view model
 */
public class ServicepageViewModel extends ViewModel {

    private MutableLiveData<Boolean> animation;
    private MutableLiveData<ServiceReadByID> response;
    private ServiceRepository repository;

    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }

    public MutableLiveData<ServiceReadByID> getResponse() {
        return response;
    }

    /**
     * @since 21-11-2022
     * create repository
     */
    public void instantiate()
    {
        if(repository == null)
        {
            repository = new ServiceRepository();
        }
    }

    /**
     * @since 22-11-2022
     * @param header is the header of HTTP request
     * @param serviceId is the id of service
     */
    public void readById(Map<String, String> header, String serviceId)
    {
        response = repository.readByID(header, serviceId);
        animation = repository.getAnimation();
    }
}
