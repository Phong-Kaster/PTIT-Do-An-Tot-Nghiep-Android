package com.example.do_an_tot_nghiep.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.DoctorReadAll;
import com.example.do_an_tot_nghiep.Container.ServiceReadAll;
import com.example.do_an_tot_nghiep.Container.ServiceReadByID;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Phong-Kaster
 * @since 21-11-2022
 */
public class ServiceRepository{
    private final String TAG = "ServiceRepository";

    /*ANIMATION*/
    private MutableLiveData<Boolean> animation = new MutableLiveData<>();
    public MutableLiveData<Boolean> getAnimation()
    {
        return animation;
    }

    /*********************************READ ALL*********************************/
    /*GETTER*/
    private final MutableLiveData<ServiceReadAll> readAllResponse = new MutableLiveData<>();
    /*FUNCTION*/
    public MutableLiveData<ServiceReadAll> readAll(Map<String, String> headers,
                                                  Map<String,String> parameters)
    {
        /*Step 1*/
        animation.setValue(true);


        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<ServiceReadAll> container = api.serviceReadAll(headers, parameters);

        /*Step 4*/
        container.enqueue(new Callback<ServiceReadAll>() {
            @Override
            public void onResponse(@NonNull Call<ServiceReadAll> call, @NonNull Response<ServiceReadAll> response) {
                if(response.isSuccessful())
                {
                    ServiceReadAll content = response.body();
                    assert content != null;
                    readAllResponse.setValue(content);
                    animation.setValue(false);
//                    System.out.println(TAG);
//                    System.out.println("result: " + content.getResult());
//                    System.out.println("quantity" + content.getQuantity());
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
            public void onFailure(@NonNull Call<ServiceReadAll> call, @NonNull Throwable t) {
                System.out.println("Service Repository - Read All - error: " + t.getMessage());
                //readAllResponse.setValue(null);
                animation.setValue(false);
            }
        });

        return readAllResponse;
    }

    /*********************************READ BY ID*********************************/
    /*GETTER*/
    private final MutableLiveData<ServiceReadByID> readByID = new MutableLiveData<>();
    public MutableLiveData<ServiceReadByID> readByID(Map<String, String> headers,
                                                   String serviceId)
    {
        /*Step 1*/
        animation.setValue(true);


        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<ServiceReadByID> container = api.serviceReadByID(headers, serviceId);

        /*Step 4*/
        container.enqueue(new Callback<ServiceReadByID>() {
            @Override
            public void onResponse(@NonNull Call<ServiceReadByID> call, @NonNull Response<ServiceReadByID> response) {
                if(response.isSuccessful())
                {
                    ServiceReadByID content = response.body();
                    assert content != null;
                    readByID.setValue(content);
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
                    readAllResponse.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServiceReadByID> call, @NonNull Throwable t) {
                System.out.println("Service Repository - Read By ID - error: " + t.getMessage());
                //readAllResponse.setValue(null);
                animation.setValue(false);
            }
        });

        return readByID;
    }
}
