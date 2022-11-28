package com.example.do_an_tot_nghiep.Recordpage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Container.NotificationReadAll;
import com.example.do_an_tot_nghiep.Container.RecordReadByID;
import com.example.do_an_tot_nghiep.Repository.NotificationRepository;
import com.example.do_an_tot_nghiep.Repository.RecordRepository;

import java.util.Map;

/**
 * @author  Phong-Kaster
 * @since 28-11-2022
 * Record-page View model
 */
public class RecordpageViewModel extends ViewModel {

    private RecordRepository repository;

    public void instantiate()
    {
        if( repository == null)
        {
            repository = new RecordRepository();
        }
    }

    /***************** ANIMATION*****************/
    private MutableLiveData<Boolean> animation = new MutableLiveData<>();
    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }


    /**************** READ BY ID ***********************/
    private MutableLiveData<RecordReadByID> readByIDResponse = new MutableLiveData<>();
    public MutableLiveData<RecordReadByID> getReadByIDResponse() {
        return readByIDResponse;
    }
    public void readByID(Map<String, String> header, String appointmentId)
    {
        animation = repository.getAnimation();
        readByIDResponse = repository.readByID(header, appointmentId);
    }
}
