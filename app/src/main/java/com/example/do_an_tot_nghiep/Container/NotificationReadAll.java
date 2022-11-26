package com.example.do_an_tot_nghiep.Container;

import com.example.do_an_tot_nghiep.Model.Notification;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationReadAll {

    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("msg")
    @Expose
    private String msg;


    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("quantityUnread")
    @Expose
    private int quantityUnread;

    @SerializedName("data")
    @Expose
    private List<Notification> data;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getQuantityUnread() {
        return quantityUnread;
    }

    public List<Notification> getData() {
        return data;
    }
}
