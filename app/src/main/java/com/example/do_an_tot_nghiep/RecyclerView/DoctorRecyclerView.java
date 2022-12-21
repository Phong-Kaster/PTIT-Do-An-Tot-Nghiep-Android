package com.example.do_an_tot_nghiep.RecyclerView;

import android.annotation.SuppressLint;
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

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Doctorpage.DoctorpageActivity;
import com.example.do_an_tot_nghiep.Model.Doctor;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DoctorRecyclerView extends RecyclerView.Adapter<DoctorRecyclerView.ViewHolder> {

    private final Context context;
    private final List<Doctor> list;

    public DoctorRecyclerView(Context context, List<Doctor> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_view_element_doctor, parent, false);

        return new DoctorRecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doctor element = list.get(position);
        String uploadUri = Constant.UPLOAD_URI();

        int id = element.getId();
        String speciality = context.getString(R.string.speciality) + " " + element.getSpeciality().getName();
        String name = context.getString(R.string.doctor) + " " + element.getName();
        @SuppressLint("ResourceType") String image = element.getAvatar().length() > 0 ?
                uploadUri + element.getAvatar() : context.getString(R.drawable.default_speciality);


        if( element.getAvatar().length() > 0)
        {
            Picasso.get().load(image).into(holder.image);
        }

        holder.speciality.setText(speciality);
        holder.name.setText(name);
        holder.layout.setOnClickListener(view->{
            Intent intent = new Intent(context, DoctorpageActivity.class);
            intent.putExtra("doctorId", String.valueOf(id));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{

        private final LinearLayout layout;
        private final ImageView image;
        private final TextView name;
        private final TextView speciality;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.elementLayout);
            image = itemView.findViewById(R.id.elementImage);
            name = itemView.findViewById(R.id.elementName);
            speciality = itemView.findViewById(R.id.elementSpeciality);
        }
    }
}
