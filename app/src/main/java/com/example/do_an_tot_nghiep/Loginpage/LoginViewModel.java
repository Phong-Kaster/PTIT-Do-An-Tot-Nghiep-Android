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
    private MutableLiveData<Login> loginWithPhoneResponse;
    private MutableLiveData<Boolean> animation;

    /*************** GETTER ******************/
    public MutableLiveData<Login> getLoginWithPhoneResponse() {
        if( loginWithPhoneResponse == null ){
            loginWithPhoneResponse = new MutableLiveData<>();
        }
        return loginWithPhoneResponse;
    }

    public MutableLiveData<Boolean> getAnimation() {
        if( animation == null ){
            animation = new MutableLiveData<>();
        }
        return animation;
    }

    /*************** FUNCTION ******************/
    public void loginWithPhone(String phone, String password){
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
                    loginWithPhoneResponse.setValue(content);
                }else
                {
                    loginWithPhoneResponse.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                animation.setValue(false);
                loginWithPhoneResponse.setValue(null);
                System.out.println("Login with Phone Number - throwable: " + t.getMessage());
            }
        });
    }


    /**
     * auth with google account
     * @param idToken is the id token from google API returns when
     * users authorize us to access their information from their google account
     */
    private MutableLiveData<Login> loginWithGoogleResponse;
    public MutableLiveData<Login> getLoginWithGoogleResponse()
    {
        if(loginWithGoogleResponse == null)
        {
            loginWithGoogleResponse = new MutableLiveData<>();
        }
        return loginWithGoogleResponse;
    }

    /**
     * @author Phong-Kaster
     * @since 16-11-2022
     * @param email
     * @param password
     */
    public void loginWithGoogle(String email, String password)
    {
        animation.setValue(true);
        /*Step 1 - create api connection */
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);


        /*Step 2 - auth with google account*/
        Call<Login> container = api.loginWithGoogle(email, password, "patient");
        container.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call,
                                   @NonNull Response<Login> dataResponse) {
                if(dataResponse.isSuccessful())
                {
                    Login content = dataResponse.body();
                    assert content != null;
                    loginWithGoogleResponse.setValue(content);
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

                    loginWithGoogleResponse.setValue(null);
                    animation.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Login> call,
                                  @NonNull Throwable t) {
                System.out.println("Login With Google - throwable: " + t.getMessage());
                animation.setValue(false);
                loginWithGoogleResponse.setValue(null);
            }
        });
    }
}
