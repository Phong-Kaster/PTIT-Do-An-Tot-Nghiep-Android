package com.example.do_an_tot_nghiep.Doctorpage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Container.DoctorReadByID;
import com.example.do_an_tot_nghiep.Repository.DoctorRepository;
import com.example.do_an_tot_nghiep.Repository.SpecialityRepository;

import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 20-11-2022
 */
public class DoctorpageViewModel extends ViewModel {

    private MutableLiveData<DoctorReadByID> response;
    private MutableLiveData<Boolean> animation;
    private DoctorRepository repository;

    public MutableLiveData<DoctorReadByID> getResponse() {
        if( response == null)
        {
            response = new MutableLiveData<>();
        }
        return response;
    }

    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }

    /**
     * @since 20-11-2022
     * create repository
     */
    public void instantiate()
    {
        if(repository == null)
        {
            repository = new DoctorRepository();
        }
    }

    /**
     * @since 20-11-2022
     * read by id
     */
    public void readById(Map<String, String> headers, String id)
    {
        response = repository.readById(headers, id);
        animation = repository.getAnimation();
    }
}
