package com.example.do_an_tot_nghiep.Bookingpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.do_an_tot_nghiep.Guidepage.GuidepageActivity;
import com.example.do_an_tot_nghiep.Homepage.HomepageActivity;
import com.example.do_an_tot_nghiep.R;

/**
 * @author Phong Kaster
 * @since 23-11-2022
 * Booking Fragment 2
 * this fragment shows message and 2 buttons HOW TO EXAM & GO BACK TO HOMEPAGE
 * when users create booking successfully
 *
 * flow: Fragment 1 -> Fragment 3 -> Fragment 2
 */
public class BookingFragment2 extends Fragment {

    private AppCompatButton btnHowToExam;
    private AppCompatButton btnHomepage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking2, container, false);

        setupComponent(view);
        setupEvent();

        return view;
    }


    /**
     * @since 23-11-2022
     * setup component
     */
    private void setupComponent(View view)
    {
        btnHomepage = view.findViewById(R.id.btnHomepage);
        btnHowToExam = view.findViewById(R.id.btnHowToExam);
    }

    /**
     * @since 23-11-2022
     * setup event
     */
    private void setupEvent()
    {
        btnHomepage.setOnClickListener(view->{
            Intent intent = new Intent(requireContext(), HomepageActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        btnHowToExam.setOnClickListener(view->{
            Intent intent = new Intent(requireContext(), GuidepageActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}