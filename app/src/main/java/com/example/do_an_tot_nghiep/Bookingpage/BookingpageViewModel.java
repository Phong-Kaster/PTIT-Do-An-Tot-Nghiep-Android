package com.example.do_an_tot_nghiep.Bookingpage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Container.ServiceReadByID;
import com.example.do_an_tot_nghiep.Repository.ServiceRepository;

import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 23-11-2022
 * Booking-page ViewModel
 */
public class BookingpageViewModel extends ViewModel {

    private MutableLiveData<ServiceReadByID> serviceReadByIdResponse;
    private MutableLiveData<Boolean> animation;
    private ServiceRepository serviceRepository;

    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }

    /**
     * @since 23-11-2022
     * instantiate repository
     */
    public void instantiate()
    {
        if( serviceRepository == null)
        {
            serviceRepository = new ServiceRepository();
        }
    }

    /************************SERVICE READ BY ID***************************/
    public MutableLiveData<ServiceReadByID> getServiceReadByIdResponse() {
        if( serviceReadByIdResponse == null)
        {
            serviceReadByIdResponse = new MutableLiveData<>();
        }
        return serviceReadByIdResponse;
    }

    public void serviceReadById(Map<String, String> header, String serviceId)
    {
        serviceReadByIdResponse = serviceRepository.readByID(header, serviceId);
        animation = serviceRepository.getAnimation();
    }
}
