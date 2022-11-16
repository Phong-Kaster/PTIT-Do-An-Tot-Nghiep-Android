package com.example.do_an_tot_nghiep.Helper;

import android.app.Application;

import com.example.do_an_tot_nghiep.Model.User;

import java.util.HashMap;
import java.util.Map;

public class GlobalVariable extends Application {
    private String accessToken;
    private User AuthUser;
    private final String SHARED_PREFERENCE_KEY = "doantotnghiep";
    private String contentType = "application/x-www-form-urlencoded";
    private Map<String, String> headers;

    /***
     * @author Phong-Kaster
     *
     * this functions supports us establish a header which is used in a HTTP request
     *
     * @return
     */
    public Map<String, String> getHeaders() {

        this.headers = new HashMap<>();
        this.headers.put("Content-Type", contentType );
        this.headers.put("Authorization", accessToken);

        return headers;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getAuthUser() {
        return AuthUser;
    }

    public void setAuthUser(User authUser) {
        AuthUser = authUser;
    }

    public String getSharedReferenceKey() {
        return SHARED_PREFERENCE_KEY;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
