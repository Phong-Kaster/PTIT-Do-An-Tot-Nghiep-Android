package com.example.do_an_tot_nghiep.Configuration;

import com.example.do_an_tot_nghiep.Container.AppointmentQueue;
import com.example.do_an_tot_nghiep.Container.AppointmentReadAll;
import com.example.do_an_tot_nghiep.Container.AppointmentReadByID;
import com.example.do_an_tot_nghiep.Container.BookingCancel;
import com.example.do_an_tot_nghiep.Container.BookingCreate;
import com.example.do_an_tot_nghiep.Container.BookingReadByID;
import com.example.do_an_tot_nghiep.Container.DoctorReadAll;
import com.example.do_an_tot_nghiep.Container.DoctorReadByID;
import com.example.do_an_tot_nghiep.Container.Login;
import com.example.do_an_tot_nghiep.Container.NotificationCreate;
import com.example.do_an_tot_nghiep.Container.NotificationMarkAllAsRead;
import com.example.do_an_tot_nghiep.Container.NotificationMarkAsRead;
import com.example.do_an_tot_nghiep.Container.NotificationReadAll;
import com.example.do_an_tot_nghiep.Container.PatientProfile;
import com.example.do_an_tot_nghiep.Container.ServiceReadAll;
import com.example.do_an_tot_nghiep.Container.ServiceReadByID;
import com.example.do_an_tot_nghiep.Container.SpecialityReadAll;
import com.example.do_an_tot_nghiep.Container.SpecialityReadByID;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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

    @GET("api/specialities/{id}")
    Call<SpecialityReadByID> specialityReadByID(@HeaderMap Map<String, String> headers, @Path("id") String id);


    /**********************DOCTOR**********************/
    @GET("api/doctors")
    Call<DoctorReadAll> doctorReadAll(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> parameters);

    @GET("api/doctors/{id}")
    Call<DoctorReadByID> doctorReadByID(@HeaderMap Map<String, String> headers, @Path("id") String id);


    /**********************SERVICE**********************/
    @GET("api/services")
    Call<ServiceReadAll> serviceReadAll(@HeaderMap Map<String, String> headers, @QueryMap Map<String, String> parameters);

    @GET("api/services/{id}")
    Call<ServiceReadByID> serviceReadByID(@HeaderMap Map<String, String> headers, @Path("id") String id);


    /**********************BOOKING**********************/
    @FormUrlEncoded
    @POST("api/patient/booking")
    Call<BookingCreate> bookingCreate(@HeaderMap Map<String, String> headers,
                                      @Field("service_id") String serviceId,
                                      @Field("booking_name") String bookingName,
                                      @Field("booking_phone") String bookingPhone,
                                      @Field("name") String name,
                                      @Field("gender") String gender,
                                      @Field("address") String address,
                                      @Field("reason") String reason,
                                      @Field("birthday") String birthday,
                                      @Field("appointment_time") String appointmentTime,
                                      @Field("appointment_date") String appointmentDate);


    /**********************NOTIFICATION**********************/
    @GET("api/patient/notifications")
    Call<NotificationReadAll> notificationReadAll(@HeaderMap Map<String, String> header);


    @POST("api/patient/notifications/mark-as-read/{id}")
    Call<NotificationMarkAsRead> notificationMarkAsRead(@HeaderMap Map<String, String> header, @Path("id") String notificationId);


    @POST("api/patient/notifications")
    Call<NotificationMarkAllAsRead> notificationMarkAllAsRead(@HeaderMap Map <String, String> header);

    @FormUrlEncoded
    @PUT("api/patient/notifications")
    Call<NotificationCreate> notificationCreate(@HeaderMap Map <String, String> header,
                                                @Field("message") String message,
                                                @Field("record_id") String recordId,
                                                @Field("record_type") String recordType);



    /************** BOOKING **********/
    @GET("api/patient/booking/{id}")
    Call<BookingReadByID> bookingReadByID(@HeaderMap Map <String, String> header, @Path("id") String bookingId);

    @DELETE("api/patient/booking/{id}")
    Call<BookingCancel> bookingCancel(@HeaderMap Map <String, String> header, @Path("id") String bookingId);


    /************** BOOKING **********/
    @GET("api/patient/appointments")
    Call<AppointmentReadAll> appointmentReadAll(@HeaderMap Map <String, String> header, @QueryMap Map<String, String> parameters);

    @GET("api/patient/appointments/{id}")
    Call<AppointmentReadByID> appointmentReadByID(@HeaderMap Map <String, String> header, @Path("id") String appointmentId);


    /************** QUEUE **********/
    @GET("api/appointment-queue")
    Call<AppointmentQueue> appointmentQueue(@HeaderMap Map <String, String> header, @QueryMap Map<String, String> parameters);
}
