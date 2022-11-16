package com.example.do_an_tot_nghiep.Loginpage;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an_tot_nghiep.Configuration.HTTPRequest;
import com.example.do_an_tot_nghiep.Configuration.HTTPService;
import com.example.do_an_tot_nghiep.Container.Login;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Phong-Kaster
 * @since 16-11-2022
 * login view model
 */
public class LoginViewModel extends ViewModel {

    private String TAG = "LoginViewModel";
    private MutableLiveData<Login> response;
    private MutableLiveData<Boolean> animation;

    /*************** GETTER ******************/
    public MutableLiveData<Login> getResponse() {
        if( response == null ){
            response = new MutableLiveData<>();
        }
        return response;
    }

    public MutableLiveData<Boolean> getAnimation() {
        if( animation == null ){
            animation = new MutableLiveData<>();
        }
        return animation;
    }

    /*************** FUNCTION ******************/
    public void login(String phone, String password){

        Log.d(TAG,"login view model receive");
        Log.d(TAG,phone);
        Log.d(TAG,password);

        animation.setValue(true);
        /*Step 1*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 2*/
        Call<Login> container = api.login( phone, password, "patient");

        /*Step 3*/
        container.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> result) {
                animation.setValue(false);
                if(result.isSuccessful())
                {
                    Login content = result.body();
                    assert content != null;
                    Log.d(TAG, "result: "+ content.getResult());
                    Log.d(TAG, "accessToken: "+ content.getAccessToken());
                    Log.d(TAG, "msg: "+ content.getMsg());
                    response.setValue(content);
                }else
                {
                    response.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                //animation.setValue(false);
                //response.setValue(null);
                System.out.println("Login - throwable: " + t.getMessage());
                //animation.setValue(false);
            }
        });
    }


    /**
     * auth with google account
     * @param idToken is the id token from google API returns when
     * users authorize us to access their information from their google account
     */
    private MutableLiveData<Login> authWithGoogleResponse;
    public MutableLiveData<Login> getAuthWithGoogleResponse()
    {
        if(authWithGoogleResponse == null)
        {
            authWithGoogleResponse = new MutableLiveData<>();
        }
        return authWithGoogleResponse;
    }

    public void authWithGoogleAccount(String idToken)
    {
        if(authWithGoogleResponse == null)
        {
            authWithGoogleResponse = new MutableLiveData<>();
        }
        if( animation == null )
        {
            animation = new MutableLiveData<>();
        }

        animation.setValue(true);
        /*Step 1 - create api connection */
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 2 - auth with google account*/
        Call<Login> container = api.authWithGoogleAccount(idToken);
        container.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call,
                                   @NonNull Response<Login> dataResponse) {
                if(dataResponse.isSuccessful())
                {
                    Login content = dataResponse.body();
                    assert content != null;
                    authWithGoogleResponse.setValue(content);
                    animation.setValue(false);
                }
                if(dataResponse.errorBody() != null)
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(dataResponse.errorBody().string());
                        System.out.println( jObjError );
                    }
                    catch (Exception e)
                    {
                        System.out.println( e.getMessage() );
                    }

                    authWithGoogleResponse.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Login> call,
                                  @NonNull Throwable t) {
                System.out.println("Auth with Google account - throwable: " + t.getMessage());
                animation.setValue(false);
            }
        });
    }
}
