package com.example.do_an_tot_nghiep.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Model.Doctor;
import com.example.do_an_tot_nghiep.Model.Speciality;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class DoctorRecyclerView extends RecyclerView.Adapter<DoctorRecyclerView.ViewHolder> {

    private Context context;
    private List<Doctor> list;

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


        String price = "Giá khám: " + element.getPrice();
        String name = element.getName();
        @SuppressLint("ResourceType") String image = element.getAvatar().length() > 0 ?
                uploadUri + element.getAvatar() : context.getString(R.drawable.default_speciality);


        if( element.getAvatar().length() > 0)
        {
            Picasso.get().load(image).into(holder.image);
        }

        holder.price.setText(price);
        holder.name.setText(name);
        holder.layout.setOnClickListener(view->{
            Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout layout;
        private ImageView image;
        private TextView name;
        private TextView price;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.elementLayout);
            image = itemView.findViewById(R.id.elementImage);
            name = itemView.findViewById(R.id.elementName);
            price = itemView.findViewById(R.id.elementPrice);
        }
    }
}
