package com.example.do_an_tot_nghiep.Appointmentpage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Container.AppointmentQueue;
import com.example.do_an_tot_nghiep.Container.AppointmentReadAll;
import com.example.do_an_tot_nghiep.Container.AppointmentReadByID;
import com.example.do_an_tot_nghiep.Repository.AppointmentQueueRepository;
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
    private AppointmentQueueRepository queueRepository;
    public void instantiate()
    {
        if( repository == null)
        {
            repository = new AppointmentRepository();
        }
        if( queueRepository == null)
        {
            queueRepository = new AppointmentQueueRepository();
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

    /************************ APPOINTMENTS - READ BY ID ***************************/
    private MutableLiveData<AppointmentReadByID> readByIDResponse = new MutableLiveData<>();
    public MutableLiveData<AppointmentReadByID> getReadByIDResponse(){return readByIDResponse;}
    public void readByID(Map<String, String> header, String appointmentID)
    {
        //animation = repository.getAnimation();
        readByIDResponse = repository.readByID(header, appointmentID);
    }

    /************************ QUEUE - READ BY ID ***************************/
    private MutableLiveData<AppointmentQueue> appointmentQueueResponse = new MutableLiveData<>();
    public MutableLiveData<AppointmentQueue> getAppointmentQueueResponse(){ return appointmentQueueResponse;}
    public void getQueue(Map<String, String> header, Map<String, String> parameter)
    {
//        animation = repository.getAnimation();
        appointmentQueueResponse = queueRepository.getAppointmentQueue(header, parameter);
    }
}
