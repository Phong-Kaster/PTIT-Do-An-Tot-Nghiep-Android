package com.example.do_an_tot_nghiep.Configuration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.Notification;
import com.example.do_an_tot_nghiep.Homepage.HomeFragment;
import com.example.do_an_tot_nghiep.Homepage.HomepageViewModel;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.Repository.SpecialityRepository;

import java.util.HashMap;
import java.util.Map;

public class QueueBroadcastReceiver extends BroadcastReceiver {

    private final String TAG = "Queue Broadcast Receiver";
    private MediaPlayer mediaPlayer;

    SpecialityRepository repository = new SpecialityRepository();
    GlobalVariable globalVariable;


    @Override
    public void onReceive(Context context, Intent intent) {
        mediaPlayer=MediaPlayer.create(context, R.raw.alarm_sound_3);


        /*Step 2 - prepare header & parameters*/
        Map<String, String> header = new HashMap<>();
        header.put("accessToken", Constant.getAccessToken());
        header.put("type", "patient");


        /*Step 3 - listen speciality Read All */
        Map<String, String> paramsSpeciality = new HashMap<>();
        repository.readAll(header, paramsSpeciality);




        /*tao noi dung cho Notification*/
        Notification notification = new Notification(context);
        String title = context.getString(R.string.app_name);
        String text =  context.getString(R.string.it_is_your_turn);
        String bigText = context.getString(R.string.it_is_your_turn);
        notification.setup(title, text, bigText );
        notification.show();


        /*chay MediaPlayer trong vong 10 giay?*/
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                mediaPlayer.start();
            }

            public void onFinish() {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }.start();
    }

}
