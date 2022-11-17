package com.example.do_an_tot_nghiep.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.SpecialityReadAll;
import com.example.do_an_tot_nghiep.Model.Speciality;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Phong-Kaster
 * @since 17-11-2022
 * SPECIALITY
 * this repository handles all request relate to TABLE tn_specialities
 */
public class SpecialityRepository {

    private final String TAG = "SpecialityRepository";

    /*ANIMATION*/
    private MutableLiveData<Boolean> animation = new MutableLiveData<>();
    public MutableLiveData<Boolean> getAnimation()
    {
        return animation;
    }

    /*********************************READ ALL*********************************/

    /*GETTER*/
    private MutableLiveData<SpecialityReadAll> readAllResponse = new MutableLiveData<>();
    public MutableLiveData<SpecialityReadAll> getReadAllResponse()
    {
        return readAllResponse;
    }
    /*FUNCTION*/
    public MutableLiveData<SpecialityReadAll> readAll(Map<String, String> headers,
                                                      Map<String,String> parameters)
    {
        /*Step 1*/
        animation.setValue(true);


        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<SpecialityReadAll> container = api.specialityReadAll(headers, parameters);

        /*Step 4*/
        container.enqueue(new Callback<SpecialityReadAll>() {
            @Override
            public void onResponse(@NonNull Call<SpecialityReadAll> call, @NonNull Response<SpecialityReadAll> response) {
                if(response.isSuccessful())
                {
                    SpecialityReadAll content = response.body();
                    assert content != null;
                    readAllResponse.setValue(content);
                    animation.setValue(false);
//                    System.out.println(TAG);
//                    System.out.println("result: " + content.getResult());
//                    System.out.println("quantity: " + content.getQuantity());
                }
                if(response.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e) {
                        System.out.println( e.getMessage() );
                    }
                    readAllResponse.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SpecialityReadAll> call, @NonNull Throwable t) {
                System.out.println("Speciality Repository - Read All - error: " + t.getMessage());
                readAllResponse.setValue(null);
                animation.setValue(false);
            }
        });

        return readAllResponse;
    }
}
