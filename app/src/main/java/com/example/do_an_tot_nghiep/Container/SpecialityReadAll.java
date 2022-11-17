package com.example.do_an_tot_nghiep.Container;

import com.example.do_an_tot_nghiep.Model.Speciality;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpecialityReadAll {
    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("data")
    @Expose
    private List<Speciality> data;

    public int getResult() {
        return result;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<Speciality> getData() {
        return data;
    }
}
