package com.example.do_an_tot_nghiep.Bookingpage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.do_an_tot_nghiep.R;

/**
 * @author Phong-Kaster
 * @since 09-12-2022
 *
 * flow: Fragment 1 -> Fragment 3 -> Fragment 2
 */
public class BookingFragment3 extends Fragment {

    private final String TAG = "Booking Fragment 3";
    private String bookingId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking3, container, false);

        Bundle bundle = getArguments();
        assert bundle != null;
        bookingId = (String) bundle.get("bookingId");

        return view;
    }
}