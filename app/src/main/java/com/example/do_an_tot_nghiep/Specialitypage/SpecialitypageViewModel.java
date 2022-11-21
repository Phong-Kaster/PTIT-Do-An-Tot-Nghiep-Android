package com.example.do_an_tot_nghiep.Specialitypage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Container.DoctorReadAll;
import com.example.do_an_tot_nghiep.Container.DoctorReadByID;
import com.example.do_an_tot_nghiep.Container.SpecialityReadByID;
import com.example.do_an_tot_nghiep.Repository.DoctorRepository;
import com.example.do_an_tot_nghiep.Repository.SpecialityRepository;

import java.util.Map;

/**
 * @author Phong-Kaster
 * @since 21-11-2022
 */
public class SpecialitypageViewModel extends ViewModel {

    private MutableLiveData<SpecialityReadByID> specialityReadByIdResponse;
    private MutableLiveData<DoctorReadAll> doctorReadAllResponse;
    private MutableLiveData<Boolean> animation;

    private SpecialityRepository specialityRepository;
    private DoctorRepository doctorRepository;

    public MutableLiveData<SpecialityReadByID> getSpecialityReadByIdResponse() {
        if( specialityReadByIdResponse == null)
        {
            specialityReadByIdResponse = new MutableLiveData<>();
        }
        return specialityReadByIdResponse;
    }

    public MutableLiveData<DoctorReadAll> getDoctorReadAllResponse()
    {
        if( doctorReadAllResponse == null)
        {
            doctorReadAllResponse = new MutableLiveData<>();
        }
        return doctorReadAllResponse;
    }


    public MutableLiveData<Boolean> getAnimation() {
        return animation;
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
    }

    /**
     * @since 20-11-2022
     * read by id
     */
    public void specialityReadById(Map<String, String> headers, String id)
    {
        specialityReadByIdResponse = specialityRepository.readById(headers, id);
        animation = specialityRepository.getAnimation();
    }

    public void doctorReadAll(Map<String, String> headers, Map<String, String> parameters)
    {
        doctorReadAllResponse = doctorRepository.readAll(headers, parameters);
    }
}
