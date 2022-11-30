package com.example.do_an_tot_nghiep.Treatmentpage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Container.AppointmentReadAll;
import com.example.do_an_tot_nghiep.Container.TreatmentReadAll;
import com.example.do_an_tot_nghiep.Container.TreatmentReadByID;
import com.example.do_an_tot_nghiep.Repository.AppointmentRepository;
import com.example.do_an_tot_nghiep.Repository.TreatmentRepository;

import java.util.Map;

public class TreatmentpageViewModel extends ViewModel {

    private AppointmentRepository appointmentRepository;
    private TreatmentRepository treatmentRepository;
    public void instantiate()
    {
        if( treatmentRepository == null)
        {
            treatmentRepository = new TreatmentRepository();
        }
        if( appointmentRepository == null)
        {
            appointmentRepository = new AppointmentRepository();
        }
    }


    private MutableLiveData<Boolean> animation = new MutableLiveData<>();
    public MutableLiveData<Boolean> getAnimation(){
        return animation;
    }

    /**************** APPOINTMENT - READ ALL********************/
    private MutableLiveData<AppointmentReadAll> appointmentReadAllResponse = new MutableLiveData<>();
    public MutableLiveData<AppointmentReadAll> getAppointmentReadAllResponse()
    {
        return appointmentReadAllResponse;
    }
    public void appointmentReadAll(Map<String, String> header, Map<String, String> parameters)
    {
        animation = appointmentRepository.getAnimation();
        appointmentReadAllResponse = appointmentRepository.readAll(header, parameters);
    }

    /**************** TREATMENT - READ ALL of AN APPOINTMENT********************/
    private MutableLiveData<TreatmentReadAll> treatmentReadAllResponse = new MutableLiveData<>();
    public MutableLiveData<TreatmentReadAll> getTreatmentReadAllResponse() {
        return treatmentReadAllResponse;
    }
    public void treatmentReadAll(Map<String, String> header, String appointmentId)
    {
        animation = treatmentRepository.getAnimation();
        treatmentReadAllResponse = treatmentRepository.readAll(header, appointmentId);
    }


    /**************** TREATMENT - READ BY ID of a treatment from AN APPOINTMENT********************/
    private MutableLiveData<TreatmentReadByID> treatmentReadByIDResponse = new MutableLiveData<>();
    public MutableLiveData<TreatmentReadByID> getTreatmentReadByIDResponse() {
        return treatmentReadByIDResponse;
    }
    public void treatmentReadByID(Map<String, String> header, String treatmentId)
    {
        animation = treatmentRepository.getAnimation();
        treatmentReadByIDResponse = treatmentRepository.readByID(header, treatmentId);
    }
}
