package com.example.do_an_tot_nghiep.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Speciality {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("doctor_quantity")
    @Expose
    private int doctorQuantity;

    @SerializedName("image")
    @Expose
    private String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
