package com.example.do_an_tot_nghiep.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Queue {

    @SerializedName("position")
    @Expose
    private int position;

    @SerializedName("numerical_order")
    @Expose
    private int numericalOrder;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("patient_id")
    @Expose
    private int patientId;

    @SerializedName("patient_name")
    @Expose
    private String patientName;


    @SerializedName("doctor_id")
    @Expose
    private int doctorId;

    @SerializedName("appointment_time")
    @Expose
    private String appointmentTime;

    @SerializedName("status")
    @Expose
    private String status;

    public int getPosition() {
        return position;
    }

    public String getPatientName() {
        return patientName;
    }

    public int getNumericalOrder() {
        return numericalOrder;
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getStatus() {
        return status;
    }
}
