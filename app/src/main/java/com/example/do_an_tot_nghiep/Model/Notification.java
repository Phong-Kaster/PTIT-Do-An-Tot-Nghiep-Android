package com.example.do_an_tot_nghiep.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("record_id")
    @Expose
    private int recordId;

    @SerializedName("record_type")
    @Expose
    private String recordType;


    @SerializedName("is_read")
    @Expose
    private int isRead;

    @SerializedName("create_at")
    @Expose
    private String createAt;

    @SerializedName("update_at")
    @Expose
    private String updateAt;


    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getRecordId() {
        return recordId;
    }

    public String getRecordType() {
        return recordType;
    }


    public int getIsRead() {
        return isRead;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }
}
