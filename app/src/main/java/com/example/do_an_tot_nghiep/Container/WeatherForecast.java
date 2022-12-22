package com.example.do_an_tot_nghiep.Container;

import com.example.do_an_tot_nghiep.Model.Main;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherForecast {

    @SerializedName("timezone")
    @Expose
    private int timeZone;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("cod")
    @Expose
    private String cod;

    @SerializedName("main")
    @Expose
    private Main main;

    public int getTimeZone() {
        return timeZone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCod() {
        return cod;
    }

    public Main getMain() {
        return main;
    }
}
