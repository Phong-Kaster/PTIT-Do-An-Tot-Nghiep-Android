package com.example.do_an_tot_nghiep.Container;

import com.example.do_an_tot_nghiep.Model.Doctor;
import com.example.do_an_tot_nghiep.Model.Service;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceReadAll {
    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("msg")
    @Expose
    private String msg;


    @SerializedName("data")
    @Expose
    private List<Service> data;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public List<Service> getData() {
        return data;
    }
}
