package com.example.do_an_tot_nghiep.Configuration;

import com.example.do_an_tot_nghiep.Container.Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HTTPRequest {

    @FormUrlEncoded
    @POST("api/login")
    Call<Login> login(@Field("phone") String phone, @Field("password") String password, @Field("type") String type);

    @FormUrlEncoded
    @POST("login/google")
    Call<Login> authWithGoogleAccount(@Field("id_token") String idToken);
}
