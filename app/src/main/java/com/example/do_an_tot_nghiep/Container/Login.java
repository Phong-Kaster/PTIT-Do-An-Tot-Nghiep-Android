package com.example.do_an_tot_nghiep.Container;

import com.example.do_an_tot_nghiep.Model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("result")
    @Expose
    private Integer result;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("accessToken")
    @Expose
    private String accessToken;

    @SerializedName("data")
    @Expose
    private User data;

    public Integer getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public User getData() {
        return data;
    }
}
