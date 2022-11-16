package com.example.do_an_tot_nghiep.Helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.do_an_tot_nghiep.R;

public class LoadingScreen {
    private Activity activity;
    private AlertDialog dialog;

    public LoadingScreen(Activity activity){
        this.activity = activity;
    }

    @SuppressLint("InflateParams")
    public void start(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_screen, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public void stop(){
        if(dialog == null) return;
        dialog.dismiss();
    }
}
