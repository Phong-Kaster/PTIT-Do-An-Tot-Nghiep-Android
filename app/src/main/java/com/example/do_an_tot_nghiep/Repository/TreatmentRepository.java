package com.example.do_an_tot_nghiep.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.TreatmentReadAll;
import com.example.do_an_tot_nghiep.Container.TreatmentReadByID;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Phong-Kaster
 * @since 30-11-2022
 * Treatment Repository is used to get query from table TN_TREATMENTS for PATIENT
 */
public class TreatmentRepository {

    private final String TAG = "Treatment Repository";

    /*ANIMATION*/
    private final MutableLiveData<Boolean> animation = new MutableLiveData<>();
    public MutableLiveData<Boolean> getAnimation()
    {
        return animation;
    }

    /*********************************READ ALL*********************************/
    /*GETTER*/
    private final MutableLiveData<TreatmentReadAll> readAllResponse = new MutableLiveData<>();
    /*FUNCTION*/
    public MutableLiveData<TreatmentReadAll> readAll(Map<String, String> headers,
                                                      String appointmentId)
    {
        /*Step 1*/
        animation.setValue(true);


        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<TreatmentReadAll> container = api.treatmentReadAll(headers, appointmentId);

        /*Step 4*/
        container.enqueue(new Callback<TreatmentReadAll>() {
            @Override
            public void onResponse(@NonNull Call<TreatmentReadAll> call, @NonNull Response<TreatmentReadAll> response) {
                if(response.isSuccessful())
                {
                    TreatmentReadAll content = response.body();
                    assert content != null;
                    readAllResponse.setValue(content);
                    animation.setValue(false);
//                    System.out.println("===================================");
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
            public void onFailure(@NonNull Call<TreatmentReadAll> call, @NonNull Throwable t) {
                System.out.println("Treatment Repository - Read All - error: " + t.getMessage());
                readAllResponse.setValue(null);
                animation.setValue(false);
            }
        });

        return readAllResponse;
    }

    /*********************************READ BY ID*********************************/
    /*GETTER*/
    private final MutableLiveData<TreatmentReadByID> readByIDResponse = new MutableLiveData<>();
    /*FUNCTION*/
    public MutableLiveData<TreatmentReadByID> readByID(Map<String, String> headers,
                                                     String treatmentId)
    {
        /*Step 1*/
        animation.setValue(true);


        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<TreatmentReadByID> container = api.treatmentReadByID(headers, treatmentId);

        /*Step 4*/
        container.enqueue(new Callback<TreatmentReadByID>() {
            @Override
            public void onResponse(@NonNull Call<TreatmentReadByID> call, @NonNull Response<TreatmentReadByID> response) {
                if(response.isSuccessful())
                {
                    TreatmentReadByID content = response.body();
                    assert content != null;
                    readByIDResponse.setValue(content);
                    animation.setValue(false);
//                    System.out.println("===================================");
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
                    readByIDResponse.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TreatmentReadByID> call, @NonNull Throwable t) {
                System.out.println("Treatment Repository - Read By ID - error: " + t.getMessage());
                readByIDResponse.setValue(null);
                animation.setValue(false);
            }
        });

        return readByIDResponse;
    }
}
