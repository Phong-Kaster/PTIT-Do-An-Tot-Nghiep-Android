package com.example.do_an_tot_nghiep.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("reason")
    @Expose
    private String reason;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("status_before")
    @Expose
    private String statusBefore;

    @SerializedName("status_after")
    @Expose
    private String statusAfter;

    @SerializedName("create_at")
    @Expose
    private String createAt;

    @SerializedName("update_at")
    @Expose
    private String updateAt;

    @SerializedName("appointment")
    @Expose
    private Appointment appointment;

    @SerializedName("doctor")
    @Expose
    private Doctor doctor;

    @SerializedName("speciality")
    @Expose
    private Speciality speciality;

    public int getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusBefore() {
        return statusBefore;
    }

    public String getStatusAfter() {
        return statusAfter;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Speciality getSpeciality() {
        return speciality;
    }
}
