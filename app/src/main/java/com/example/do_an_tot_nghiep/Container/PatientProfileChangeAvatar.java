package com.example.do_an_tot_nghiep.Container;

import com.example.do_an_tot_nghiep.Model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientProfileChangeAvatar {
    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("data")
    @Expose
    private User data;

    public User getData() {
        return data;
    }

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public String getUrl() {
        return url;
    }
}
