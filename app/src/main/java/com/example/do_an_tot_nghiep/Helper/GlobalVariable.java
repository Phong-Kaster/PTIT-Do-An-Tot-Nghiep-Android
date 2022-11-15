package com.example.do_an_tot_nghiep.Helper;

import android.app.Application;

import com.example.do_an_tot_nghiep.Model.User;

import java.util.Map;

public class GlobalVariable extends Application {
    private String accessToken;
    private User AuthUser;
    private final String SHARED_PREFERENCE_KEY = "doantotnghiep";
    private String contentType = "application/x-www-form-urlencoded";
    private Map<String, String> headers;
}
