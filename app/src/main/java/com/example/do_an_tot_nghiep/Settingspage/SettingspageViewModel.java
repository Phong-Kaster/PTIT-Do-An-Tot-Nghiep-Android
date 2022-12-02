package com.example.do_an_tot_nghiep.Settingspage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Container.AppointmentReadAll;
import com.example.do_an_tot_nghiep.Repository.AppointmentQueueRepository;
import com.example.do_an_tot_nghiep.Repository.AppointmentRepository;

import java.util.Map;

/**
 * @since 01-12-2022
 * settings-page view model
 */
public class SettingspageViewModel extends ViewModel {

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

    /************************ APPOINTMENTS - READ ALL ***************************/
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
