package com.example.do_an_tot_nghiep.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_tot_nghiep.Bookingpage.BookingpageInfoActivity;
import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Model.Booking;
import com.example.do_an_tot_nghiep.Model.Photo;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookingRecyclerView extends RecyclerView.Adapter<BookingRecyclerView.ViewHolder> {

    private final Context context;
    private final List<Booking> list;

    public BookingRecyclerView(Context context, List<Booking> list)
    {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_view_element_booking, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking element = list.get(position);

        String id = String.valueOf(element.getId());

        String serviceName = element.getService().getName();
        holder.serviceName.setText(serviceName);


        if( element.getService().getImage().length() > 0)
        {
            String serviceImage = Constant.UPLOAD_URI() + element.getService().getImage();
            Picasso.get().load(serviceImage).into(holder.serviceImage);
        }

        String datetime = element.getAppointmentDate() + " " + context.getString(R.string.at) + " " +  element.getAppointmentTime();
        holder.datetime.setText(datetime);

        String bookingName = context.getString(R.string.bookingName2) + ": " + element.getBookingName();
        holder.bookingName.setText(bookingName);

        String bookingPhone = context.getString(R.string.bookingPhone2) + ": " + element.getBookingPhone();
        holder.bookingPhone.setText(bookingPhone);

        String patientName = context.getString(R.string.patientName2) + ": " + element.getName();
        holder.patientName.setText(patientName);

        String patientReason = context.getString(R.string.patientReason) + ": " + element.getReason();
        holder.patientReason.setText(patientReason);


        String status = element.getStatus();
        /*Show appointment status or button remind me*/
        switch (status){
            case "verified":
                holder.statusDone.setVisibility(View.VISIBLE);
                holder.statusCancel.setVisibility(View.GONE);
                holder.statusProcessing.setVisibility(View.GONE);
                break;
            case "cancelled":
                holder.statusDone.setVisibility(View.GONE);
                holder.statusCancel.setVisibility(View.VISIBLE);
                holder.statusProcessing.setVisibility(View.GONE);
                break;
            case "processing":
                holder.statusDone.setVisibility(View.GONE);
                holder.statusCancel.setVisibility(View.GONE);
                holder.statusProcessing.setVisibility(View.VISIBLE);
        }


        holder.layout.setOnClickListener(view -> {
            Intent intent = new Intent(context, BookingpageInfoActivity.class);
            intent.putExtra("id", id);
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{

        private final LinearLayout layout;

        private final ImageView serviceImage;
        private final TextView serviceName;

        private final TextView datetime;
        private final TextView bookingName;
        private final TextView bookingPhone;
        private final TextView patientName;
        private final TextView patientReason;
        private final TextView statusDone;
        private final TextView statusCancel;
        private final TextView statusProcessing;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.elementLayout);

            serviceImage = itemView.findViewById(R.id.elementServiceImage);
            serviceName = itemView.findViewById(R.id.elementServiceName);

            datetime = itemView.findViewById(R.id.elementDatetime);
            bookingName = itemView.findViewById(R.id.elementBookingName);
            bookingPhone = itemView.findViewById(R.id.elementBookingPhone);
            patientName = itemView.findViewById(R.id.elementPatientName);
            patientReason = itemView.findViewById(R.id.elementPatientReason);
            statusCancel = itemView.findViewById(R.id.elementStatusCancel);
            statusDone = itemView.findViewById(R.id.elementStatusDone);
            statusProcessing = itemView.findViewById(R.id.elementStatusProcessing);
        }
    }
}
