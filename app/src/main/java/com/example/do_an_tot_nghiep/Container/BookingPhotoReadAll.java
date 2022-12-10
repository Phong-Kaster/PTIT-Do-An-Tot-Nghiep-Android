package com.example.do_an_tot_nghiep.Container;

import com.example.do_an_tot_nghiep.Model.Booking;
import com.example.do_an_tot_nghiep.Model.Photo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingPhotoReadAll {

    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("booking")
    @Expose
    private Booking booking;

    @SerializedName("data")
    @Expose
    private List<Photo> data;

    public String getMsg() {
        return msg;
    }

    public int getResult() {
        return result;
    }

    public int getQuantity() {
        return quantity;
    }

    public Booking getBooking() {
        return booking;
    }

    public List<Photo> getData() {
        return data;
    }
}
