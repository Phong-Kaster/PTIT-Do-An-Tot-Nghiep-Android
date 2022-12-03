package com.example.do_an_tot_nghiep.Emailpage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.do_an_tot_nghiep.Helper.Dialog;
import com.example.do_an_tot_nghiep.R;

/**
 * @author Phong-Kaster
 * @since 03-12-2022
 */
public class EmailFragment2 extends Fragment {

    private String txtTitle;
    private String txtDescription;
    private String txtContent;
    private AppCompatButton btnSend;

    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_email2, container, false);

        setupComponent(view);

        /*GET DATA FROM EMAIL FRAGMENT 1*/
        dialog = new Dialog(requireContext());
        dialog.announce();
        dialog.btnOK.setOnClickListener(view1->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        Bundle bundle = this.getArguments();
        if( bundle == null)
        {
            dialog.show(R.string.attention, requireContext().getString(R.string.oops_there_is_an_issue), R.drawable.ic_close);
        }
        else
        {
            txtTitle = bundle.getString("title");
            txtDescription = bundle.getString("description");
            txtContent = bundle.getString("content");
        }/*end GET DATA FROM EMAIL FRAGMENT 1*/

        setupEvent();
        return view;
    }

    /**
     * @since 03-12-2022
     */
    private void setupComponent(View view)
    {
        btnSend = view.findViewById(R.id.btnSend);
    }

    /**
     * @since 03-12-2022
     */
    private void setupEvent()
    {
        btnSend.setOnClickListener(view -> {

            String body = requireContext().getString(R.string.description2) + ": " + txtDescription +
                    "\n\n "+ requireContext().getString(R.string.content) + ": " + txtContent;

            Uri uri = Uri.parse("mailto:")
                    .buildUpon()
                    .appendQueryParameter("to", "phongkaster@gmail.com")
                    .appendQueryParameter("subject", txtTitle)
                    .appendQueryParameter("body", body)
                    .build();


            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            String chosenTitle = requireContext().getString(R.string.select_application_to_send);
            startActivity(Intent.createChooser(emailIntent, chosenTitle));
        });
    }
}