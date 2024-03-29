package com.example.do_an_tot_nghiep.Container;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationMarkAsRead {

    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("msg")
    @Expose
    private String msg;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }
}
