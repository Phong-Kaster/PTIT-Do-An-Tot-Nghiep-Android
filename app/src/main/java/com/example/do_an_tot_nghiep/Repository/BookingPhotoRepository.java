package com.example.do_an_tot_nghiep.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.BookingPhotoReadAll;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookingPhotoRepository {

    private final String TAG = "Booking Photo Repository";
    private final MutableLiveData<Boolean> animation = new MutableLiveData<>();
    public MutableLiveData<Boolean> getAnimation() {
        return animation;
    }


    /************************** CREATE*******************************/
    private final MutableLiveData<BookingPhotoReadAll> readAllResponse = new MutableLiveData<>();
    public MutableLiveData<BookingPhotoReadAll> readAll (Map<String, String> header, String bookingId)
    {
        /*Step 1*/
        animation.setValue(true);


        /*Step 2*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 3*/
        Call<BookingPhotoReadAll> container = api.bookingPhotoReadAll(header, bookingId);

        /*Step 4*/
        container.enqueue(new Callback<BookingPhotoReadAll>() {
            @Override
            public void onResponse(@NonNull Call<BookingPhotoReadAll> call, @NonNull Response<BookingPhotoReadAll> response) {
                if(response.isSuccessful())
                {
                    BookingPhotoReadAll content = response.body();
                    assert content != null;
                    readAllResponse.postValue(content);
                    animation.setValue(false);
//                    System.out.println(TAG);
//                    System.out.println("result: " + content.getResult());
//                    System.out.println("msg: " + content.getMsg());
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
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookingPhotoReadAll> call, @NonNull Throwable t) {
                System.out.println(TAG);
                System.out.println("Booking Photo Repository - Read All - error: " + t.getMessage());
                animation.postValue(false);
            }
        });
        return readAllResponse;
    }

}
