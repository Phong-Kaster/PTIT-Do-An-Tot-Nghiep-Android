package com.example.do_an_tot_nghiep.Container;

import com.example.do_an_tot_nghiep.Model.Service;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Phong-Kaster
 * @since 22-11-2022
 * service read by id
 */
public class ServiceReadByID {
    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private Service data;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public Service getData() {
        return data;
    }
}
