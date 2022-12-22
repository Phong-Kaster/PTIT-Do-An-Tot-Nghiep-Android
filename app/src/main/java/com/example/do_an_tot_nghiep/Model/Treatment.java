package com.example.do_an_tot_nghiep.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Treatment {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("appointment_id")
    @Expose
    private int appointmentId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("times")
    @Expose
    private int times;

    @SerializedName("purpose")
    @Expose
    private String purpose;

    @SerializedName("instruction")
    @Expose
    private String instruction;

    @SerializedName("repeat_days")
    @Expose
    private String repeatDays;

    @SerializedName("repeat_time")
    @Expose
    private String repeatTime;

    public int getId() {
        return id;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getTimes() {
        return times;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getRepeatDays() {
        return repeatDays;
    }

    public String getRepeatTime() {
        return repeatTime;
    }
}
