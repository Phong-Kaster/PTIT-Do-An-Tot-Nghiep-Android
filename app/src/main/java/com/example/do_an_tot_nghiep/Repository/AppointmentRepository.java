package com.example.do_an_tot_nghiep.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.AppointmentReadAll;
import com.example.do_an_tot_nghiep.Container.AppointmentReadByID;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AppointmentRepository {

    private final String TAG = "Appointment Repository";
    private final MutableLiveData<Boolean> animation = new MutableLiveData<>();
    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }

    /************************** READ ALL *******************************/
    private final MutableLiveData<AppointmentReadAll> readAllResponse = new MutableLiveData<>();

    public MutableLiveData<AppointmentReadAll> readAll(Map<String, String> header, Map<String, String> parameters)
    {
        /*Step 1*/
        animation.setValue(true);


        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<AppointmentReadAll> container = api.appointmentReadAll(header, parameters);

        /*Step 4*/
        container.enqueue(new Callback<AppointmentReadAll>() {
            @Override
            public void onResponse(@NonNull Call<AppointmentReadAll> call, @NonNull Response<AppointmentReadAll> response) {
                if(response.isSuccessful())
                {
                    AppointmentReadAll content = response.body();
                    assert content != null;
                    readAllResponse.setValue(content);
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
            public void onFailure(@NonNull Call<AppointmentReadAll> call, @NonNull Throwable t) {
                System.out.println("Appointment Repository - Read All - error: " + t.getMessage());
                //readAllResponse.setValue(null);
                animation.setValue(false);
            }
        });

        return readAllResponse;
    }

    /************************** READ BY ID *******************************/
    private final MutableLiveData<AppointmentReadByID> readByIDResponse = new MutableLiveData<>();
    public MutableLiveData<AppointmentReadByID> readByID(Map<String, String> header, String appointmentID)
    {
        /*Step 1*/
        animation.setValue(true);


        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<AppointmentReadByID> container = api.appointmentReadByID(header, appointmentID);

        /*Step 4*/
        container.enqueue(new Callback<AppointmentReadByID>() {
            @Override
            public void onResponse(@NonNull Call<AppointmentReadByID> call, @NonNull Response<AppointmentReadByID> response) {
                if(response.isSuccessful())
                {
                    AppointmentReadByID content = response.body();
                    assert content != null;
                    readByIDResponse.setValue(content);
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
                    readByIDResponse.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AppointmentReadByID> call, @NonNull Throwable t) {
                System.out.println("Appointment Repository - Read By ID - error: " + t.getMessage());
                //readAllResponse.setValue(null);
                animation.setValue(false);
            }
        });

        return readByIDResponse;
    }
}
