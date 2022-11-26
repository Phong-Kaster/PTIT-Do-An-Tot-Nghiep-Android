package com.example.do_an_tot_nghiep.Appointmentpage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Container.AppointmentReadAll;
import com.example.do_an_tot_nghiep.Repository.AppointmentRepository;

import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 26-11-2022
 * Appointment-page view model
 */
public class AppointmentpageViewModel extends ViewModel {


    private MutableLiveData<Boolean> animation = new MutableLiveData<>();
    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }

    private AppointmentRepository repository;
    public void instantiate()
    {
        if( repository == null)
        {
            repository = new AppointmentRepository();
        }
    }

    /************************  READ ALL ***************************/
    private MutableLiveData<AppointmentReadAll> readAllResponse = new MutableLiveData<>();
    public MutableLiveData<AppointmentReadAll> getReadAllResponse() {
        return readAllResponse;
    }
    public void readAll(Map<String, String> header, Map<String, String> parameters)
    {
        animation = repository.getAnimation();
        readAllResponse = repository.readAll(header, parameters);
    }
}