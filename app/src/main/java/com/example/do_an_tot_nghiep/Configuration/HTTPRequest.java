package com.example.do_an_tot_nghiep.Configuration;

import com.example.do_an_tot_nghiep.Container.DoctorReadAll;
import com.example.do_an_tot_nghiep.Container.Login;
import com.example.do_an_tot_nghiep.Container.PatientProfile;
import com.example.do_an_tot_nghiep.Container.SpecialityReadAll;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface HTTPRequest {

    /**********************LOGIN WITH PHONE NUMBER**********************/
    @FormUrlEncoded
    @POST("api/login")
    Call<Login> login(@Field("phone") String phone, @Field("password") String password, @Field("type") String type);


    /**********************LOGIN WITH GOOGLE ACCOUNT**********************/
    @FormUrlEncoded
    @POST("api/login/google")
    Call<Login> loginWithGoogle(@Field("email") String email, @Field("password") String password, @Field("type") String type);





    /**********************PATIENT PROFILE - GET - READ PERSONAL INFORMATION**********************/
    @GET("api/patient/profile")
    Call<PatientProfile> readPersonalInformation(@HeaderMap Map<String, String> headers);



    /**********************SPECIALITY**********************/
    @GET("api/specialities")
    Call<SpecialityReadAll> specialityReadAll(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> parameters);



    /**********************DOCTOR**********************/
    @GET("api/doctors")
    Call<DoctorReadAll> doctorReadAll(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> parameters);
}
