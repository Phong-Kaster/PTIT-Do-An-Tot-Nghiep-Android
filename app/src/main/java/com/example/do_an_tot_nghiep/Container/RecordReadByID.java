package com.example.do_an_tot_nghiep.Container;

import com.example.do_an_tot_nghiep.Model.Record;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecordReadByID {

    @SerializedName("result")
    @Expose
    private int result;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    private Record data;

    public int getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public Record getData() {
        return data;
    }
}
