package com.example.do_an_tot_nghiep.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_tot_nghiep.Appointmentpage.AppointmentpageInfoActivity;
import com.example.do_an_tot_nghiep.Appointmentpage.AppointmentpageService;
import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Model.Appointment;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AppointmentRecyclerView extends RecyclerView.Adapter<AppointmentRecyclerView.ViewHolder> {

    private final Context context;
    private final List<Appointment> list;

    public AppointmentRecyclerView(Context context, List<Appointment> list)
    {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_view_element_appointment, parent, false);

        return new AppointmentRecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int index) {
        Appointment appointment = list.get(index);

        int recordId = appointment.getId();
        int doctorId = appointment.getDoctor().getId();
        int patientPosition = appointment.getPosition();
        String doctorName = context.getString(R.string.doctor) + " " + appointment.getDoctor().getName();
        String doctorAvatar = Constant.UPLOAD_URI() + appointment.getDoctor().getAvatar();
        String location = appointment.getRoom().getLocation() + ", " + appointment.getRoom().getName();

        String position = context.getString(R.string.your_position) + " " + appointment.getPosition();
        String reason = context.getString(R.string.your_reason) + " " + appointment.getPatientReason();

        String status = appointment.getStatus();

        /*GET NOTIFICATION WHEN CURRENT POSITION IN QUEUE EQUALS WITH USER'S POSITION*/
        holder.btnRemindMe.setOnClickListener(view -> {

            Toast.makeText(context, context.getString(R.string.you_will_get_notification_as_soon_as_your_turn), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(context, AppointmentpageService.class);
            intent.putExtra("recordId", String.valueOf(recordId));
            intent.putExtra("recordType", "appointment");
            intent.putExtra("doctorName", doctorName);
            intent.putExtra("doctorId", String.valueOf(doctorId));
            intent.putExtra("position", String.valueOf(patientPosition));
            ContextCompat.startForegroundService(context, intent);
        });

        /*Show appointment status or button remind me*/
        switch (status){
            case "processing":
                holder.btnRemindMe.setVisibility(View.VISIBLE);
                holder.statusDone.setVisibility(View.GONE);
                holder.statusCancel.setVisibility(ViewGroup.GONE);
                break;
            case "done":
                holder.btnRemindMe.setVisibility(View.GONE);
                holder.statusDone.setVisibility(View.VISIBLE);
                holder.statusCancel.setVisibility(ViewGroup.GONE);
                break;
            case "cancelled":
                holder.btnRemindMe.setVisibility(View.GONE);
                holder.statusDone.setVisibility(View.GONE);
                holder.statusCancel.setVisibility(ViewGroup.VISIBLE);
                break;
        }


        if(appointment.getDoctor().getAvatar().length() > 0)
        {
            Picasso.get().load(doctorAvatar).into(holder.doctorAvatar);
        }

        holder.reason.setText(reason);
        holder.position.setText(position);
        holder.location.setText(location);
        holder.doctorName.setText(doctorName);
        holder.layout.setOnClickListener(view->{
            Intent intent = new Intent(context, AppointmentpageInfoActivity.class);
            intent.putExtra("id", String.valueOf(recordId));
            intent.putExtra("position", String.valueOf(patientPosition));
            intent.putExtra("doctorId", String.valueOf(doctorId));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{

        private final LinearLayout layout;
        private final ImageView doctorAvatar;
        private final TextView doctorName;

        private final TextView location;
        private final TextView position;
        private final TextView reason;

        private final AppCompatButton btnRemindMe;
        private final TextView statusDone;
        private final TextView statusCancel;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.elementLayout);
            doctorAvatar = itemView.findViewById(R.id.elementDoctorImage);
            doctorName = itemView.findViewById(R.id.elementDoctorName);

            location = itemView.findViewById(R.id.elementLocation);
            position = itemView.findViewById(R.id.elementPosition);
            reason = itemView.findViewById(R.id.elementReason);

            btnRemindMe = itemView.findViewById(R.id.elementBtnRemindMe);

            statusDone = itemView.findViewById(R.id.elementStatusDone);
            statusCancel = itemView.findViewById(R.id.elementStatusCancel);
        }
    }
}
