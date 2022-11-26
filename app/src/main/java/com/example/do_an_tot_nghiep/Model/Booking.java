package com.example.do_an_tot_nghiep.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("booking_name")
    @Expose
    private String bookingName;

    @SerializedName("booking_phone")
    @Expose
    private String bookingPhone;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("gender")
    @Expose
    private int gender;

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("reason")
    @Expose
    private String reason;

    @SerializedName("appointment_time")
    @Expose
    private String appointmentTime;

    @SerializedName("appointment_date")
    @Expose
    private String appointmentDate;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("create_at")
    @Expose
    private String createAt;

    @SerializedName("update_at")
    @Expose
    private String updateAt;

    @SerializedName("service")
    @Expose
    private Service service;

    public int getId() {
        return id;
    }

    public String getBookingName() {
        return bookingName;
    }

    public String getBookingPhone() {
        return bookingPhone;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public String getReason() {
        return reason;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public Service getService() {
        return service;
    }
}
