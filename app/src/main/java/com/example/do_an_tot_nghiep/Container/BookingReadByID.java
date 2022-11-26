package com.example.do_an_tot_nghiep.Container;

import com.example.do_an_tot_nghiep.Model.Booking;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingReadByID {

    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("msg")
    @Expose
    private String msg;


    @SerializedName("data")
    @Expose
    private Booking data;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public Booking getData() {
        return data;
    }
}
