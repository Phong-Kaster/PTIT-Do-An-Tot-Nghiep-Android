package com.example.do_an_tot_nghiep.Container;

import com.example.do_an_tot_nghiep.Model.Doctor;
import com.example.do_an_tot_nghiep.Model.Speciality;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Phong-Kaster
 * @since 21-11-2022
 * Specialty Read By ID
 */
public class SpecialityReadByID {
    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private Speciality data;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public Speciality getData() {
        return data;
    }
}
