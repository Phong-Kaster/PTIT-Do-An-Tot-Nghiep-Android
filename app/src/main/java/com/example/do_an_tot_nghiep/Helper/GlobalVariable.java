package com.example.do_an_tot_nghiep.Helper;

import android.app.Application;

import com.example.do_an_tot_nghiep.Model.Option;
import com.example.do_an_tot_nghiep.Model.User;
import com.example.do_an_tot_nghiep.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * this.headers.put("type", "patient"); because there are 2 types of account: DOCTOR and PATIENT
     * so that set type is the key to distinguish a request from DOCTOR or PATIENT
     * @return
     */
    public Map<String, String> getHeaders() {

        this.headers = new HashMap<>();
        this.headers.put("Content-Type", contentType );
        this.headers.put("Authorization", accessToken);
        this.headers.put("type", "patient");

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


    /**
     * @since 21-11-2022
     */
    public List<Option> getFilterOptions()
    {
        List<Option> list = new ArrayList<>();

//        Option option0 = new Option();
//        option0.setIcon(R.drawable.ic_all);
//        option0.setName(getString(R.string.all));

        Option option1 = new Option();
        option1.setIcon(R.drawable.ic_service);
        option1.setName(getString(R.string.service));

        Option option2 = new Option();
        option2.setIcon(R.drawable.ic_speciality);
        option2.setName(getString(R.string.speciality));

        Option option3 = new Option();
        option3.setIcon(R.drawable.ic_doctor);
        option3.setName(getString(R.string.doctor));


        list.add(option1);
        list.add(option2);
        list.add(option3);


        return list;
    }
}
