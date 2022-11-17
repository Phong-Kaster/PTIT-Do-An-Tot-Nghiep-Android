package com.example.do_an_tot_nghiep.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doctor {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("active")
    @Expose
    private String active;

    @SerializedName("create_at")
    @Expose
    private String createAt;

    @SerializedName("update_at")
    @Expose
    private String updateAt;

    @SerializedName("speciality")
    @Expose
    private Speciality speciality;

    @SerializedName("room")
    @Expose
    private Room room;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getRole() {
        return role;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getActive() {
        return active;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public Room getRoom() {
        return room;
    }
}
