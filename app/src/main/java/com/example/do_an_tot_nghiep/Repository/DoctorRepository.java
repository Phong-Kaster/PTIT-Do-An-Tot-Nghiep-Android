package com.example.do_an_tot_nghiep.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.DoctorReadAll;
import com.example.do_an_tot_nghiep.Container.DoctorReadByID;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Phong-Kaster
 * @since 17-11-2022
 * DOCTOR REPOSITORY
 * this repository handles all request relate to TABLE tn_doctors
 */
public class DoctorRepository {
    private final String TAG = "DoctorRepository";

    /*ANIMATION*/
    private final MutableLiveData<Boolean> animation = new MutableLiveData<>();
    public MutableLiveData<Boolean> getAnimation()
    {
        return animation;
    }

    /*********************************READ ALL*********************************/
    /*GETTER*/
    private final MutableLiveData<DoctorReadAll> readAllResponse = new MutableLiveData<>();
    public MutableLiveData<DoctorReadAll> getReadAllResponse()
    {
        return readAllResponse;
    }
    /*FUNCTION*/
    public MutableLiveData<DoctorReadAll> readAll(Map<String, String> headers,
                                                      Map<String,String> parameters)
    {
        /*Step 1*/
        animation.setValue(true);


        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<DoctorReadAll> container = api.doctorReadAll(headers, parameters);

        /*Step 4*/
        container.enqueue(new Callback<DoctorReadAll>() {
            @Override
            public void onResponse(@NonNull Call<DoctorReadAll> call, @NonNull Response<DoctorReadAll> response) {
                if(response.isSuccessful())
                {
                    DoctorReadAll content = response.body();
                    assert content != null;
                    readAllResponse.setValue(content);
                    animation.setValue(false);
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
            public void onFailure(@NonNull Call<DoctorReadAll> call, @NonNull Throwable t) {
                System.out.println("Doctor Repository - Read All - error: " + t.getMessage());
                //readAllResponse.setValue(null);
                animation.setValue(false);
            }
        });

        return readAllResponse;
    }

    /*********************************READ BY ID*********************************/
    /*GETTER*/
    private MutableLiveData<DoctorReadByID> readByIdResponse = new MutableLiveData<>();
    public MutableLiveData<DoctorReadByID> getReadByIdResponse()
    {
        return readByIdResponse;
    }
    /*FUNCTION*/
    public MutableLiveData<DoctorReadByID> readById(Map<String, String> headers,
                                                  String doctorId)
    {
        /*Step 1*/
        animation.setValue(true);


        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<DoctorReadByID> container = api.doctorReadByID(headers, doctorId);

        /*Step 4*/
        container.enqueue(new Callback<DoctorReadByID>() {
            @Override
            public void onResponse(@NonNull Call<DoctorReadByID> call, @NonNull Response<DoctorReadByID> response) {
                if(response.isSuccessful())
                {
                    DoctorReadByID content = response.body();
                    assert content != null;
                    readByIdResponse.setValue(content);
                    animation.setValue(false);
//                    System.out.println(TAG);
//                    System.out.println("result: " + content.getResult());
//                    System.out.println("msg: " + content.getMsg());
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
                    readByIdResponse.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoctorReadByID> call, @NonNull Throwable t) {
                System.out.println("Doctor Repository - Read By ID - error: " + t.getMessage());
                //readAllResponse.setValue(null);
                animation.setValue(false);
            }
        });

        return readByIdResponse;
    }
}
