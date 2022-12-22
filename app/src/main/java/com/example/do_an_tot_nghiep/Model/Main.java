package com.example.do_an_tot_nghiep.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @since 22-12-2022
 *  dây là một class để biên dịch API của open weather map.org
 */
public class Main {

    @SerializedName("temp")
    @Expose
    private float temp;

    @SerializedName("feels_like")
    @Expose
    private float feelsLike;

    @SerializedName("temp_min")
    @Expose
    private float tempMin;

    @SerializedName("pressure")
    @Expose
    private float pressure;

    @SerializedName("humidity")
    @Expose
    private float humidity;

    public float getTemp() {
        return temp;
    }

    public float getFeelsLike() {
        return feelsLike;
    }

    public float getTempMin() {
        return tempMin;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }
}
