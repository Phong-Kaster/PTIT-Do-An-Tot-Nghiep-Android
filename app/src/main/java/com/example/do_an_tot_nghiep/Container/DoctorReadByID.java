package com.example.do_an_tot_nghiep.Container;

import com.example.do_an_tot_nghiep.Model.Doctor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Phong-Kaster
 * @since 20-11-2022
 */
public class DoctorReadByID {
    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private Doctor data;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public Doctor getData() {
        return data;
    }
}
