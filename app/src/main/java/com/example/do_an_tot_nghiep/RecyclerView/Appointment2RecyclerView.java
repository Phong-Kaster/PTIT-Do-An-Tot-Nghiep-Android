package com.example.do_an_tot_nghiep.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_tot_nghiep.Appointmentpage.AppointmentpageInfoActivity;
import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.Model.Appointment;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Appointment2RecyclerView extends RecyclerView.Adapter<Appointment2RecyclerView.ViewHolder> {

    private final Context context;
    private final List<Appointment> list;


    public Appointment2RecyclerView(Context context, List<Appointment> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_view_element_appointment_2, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int index) {
        Appointment appointment = list.get(index);

        int appointmentId = appointment.getId();
        int doctorId = appointment.getDoctor().getId();
        int myPosition = appointment.getPosition();

        String doctorName = context.getString(R.string.doctor) + " " + appointment.getDoctor().getName();
        String doctorAvatar = Constant.UPLOAD_URI() + appointment.getDoctor().getAvatar();
        String reason = appointment.getPatientReason();

        String datetime = Tooltip.beautifierDatetime(context, appointment.getUpdateAt());
        String status = appointment.getStatus();



        if(appointment.getDoctor().getAvatar().length() > 0)
        {
            Picasso.get().load(doctorAvatar).into(holder.doctorAvatar);
        }
        holder.doctorName.setText(doctorName);
        holder.datetime.setText(datetime);
        holder.reason.setText(reason);
        /*Show appointment status or button remind me*/
        switch (status){
            case "done":
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

        holder.layout.setOnClickListener(view->{
            Intent intent = new Intent(context, AppointmentpageInfoActivity.class);
            intent.putExtra("id", String.valueOf(appointmentId));
//            intent.putExtra("doctorId", String.valueOf(doctorId));
//            intent.putExtra("myPosition", String.valueOf(myPosition));
//            intent.putExtra("appointmentStatus", "false");
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends DoctorRecyclerView.ViewHolder
    {
        private final LinearLayout layout;
        private final CircleImageView doctorAvatar;
        private final TextView doctorName;

        private final TextView datetime;
        private final TextView reason;

        private final TextView statusCancel;
        private final TextView statusDone;
        private final TextView statusProcessing;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.elementLayout);
            doctorAvatar = itemView.findViewById(R.id.elementDoctorImage);
            doctorName = itemView.findViewById(R.id.elementDoctorName);
            datetime = itemView.findViewById(R.id.elementDatetime);
            reason = itemView.findViewById(R.id.elementReason);
            statusCancel = itemView.findViewById(R.id.elementStatusCancel);
            statusDone = itemView.findViewById(R.id.elementStatusDone);
            statusProcessing = itemView.findViewById(R.id.elementStatusProcessing);
        }
    }

}
