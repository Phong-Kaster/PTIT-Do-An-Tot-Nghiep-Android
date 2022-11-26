package com.example.do_an_tot_nghiep.Notificationpage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Container.NotificationReadAll;
import com.example.do_an_tot_nghiep.Repository.NotificationRepository;

import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 24-11-2022
 * Notification View Model
 */
public class NotificationViewModel extends ViewModel {
    private NotificationRepository repository;
    private MutableLiveData<Boolean> animation = new MutableLiveData<>();
    private MutableLiveData<NotificationReadAll> readAllResponse = new MutableLiveData<>();

    /*ANIMATION*/
    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }

    public void instantiate()
    {
        if( repository == null)
        {
            repository = new NotificationRepository();
        }
    }

    /*GETTER*/
    public MutableLiveData<NotificationReadAll> getReadAllResponse() {
        return readAllResponse;
    }
    public void readAll(Map<String, String> header)
    {
        animation = repository.getAnimation();
        readAllResponse = repository.readAll(header);
    }
}
