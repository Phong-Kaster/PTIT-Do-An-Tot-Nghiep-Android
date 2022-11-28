package com.example.do_an_tot_nghiep.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.AppointmentReadByID;
import com.example.do_an_tot_nghiep.Container.RecordReadByID;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Phong-Kaster
 * @since 28-11-2022
 * record repository is used to get appointment record from table TN_APPOINTMENT_RECORDS
 */
public class RecordRepository {

    private final String TAG = "Record Repository";

    /******** ANIMATION *********/
    private MutableLiveData<Boolean> animation = new MutableLiveData<>();
    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }

    /******* READ BY ID *********/
    private MutableLiveData<RecordReadByID> readByIDResponse = new MutableLiveData<>();
    public MutableLiveData<RecordReadByID> readByID(Map<String, String> header, String appointmentID)
    {
        /*Step 1*/
        animation.setValue(true);


        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<RecordReadByID> container = api.recordReadById(header, appointmentID);

        /*Step 4*/
        container.enqueue(new Callback<RecordReadByID>() {
            @Override
            public void onResponse(@NonNull Call<RecordReadByID> call, @NonNull Response<RecordReadByID> response) {
                if(response.isSuccessful())
                {
                    RecordReadByID content = response.body();
                    assert content != null;
                    readByIDResponse.setValue(content);
                    animation.setValue(false);
//                    System.out.println("==============================");
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
            public void onFailure(@NonNull Call<RecordReadByID> call, @NonNull Throwable t) {
                System.out.println("Record Repository - Read By ID - error: " + t.getMessage());
                //readAllResponse.setValue(null);
                animation.setValue(false);
            }
        });

        return readByIDResponse;
    }
}
