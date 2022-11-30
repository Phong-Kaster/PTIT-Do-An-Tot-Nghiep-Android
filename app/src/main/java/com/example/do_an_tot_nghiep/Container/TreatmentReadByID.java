package com.example.do_an_tot_nghiep.Container;

import com.example.do_an_tot_nghiep.Model.Treatment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TreatmentReadByID {

    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private Treatment data;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public Treatment getData() {
        return data;
    }
}
