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

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Model.Service;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.Servicepage.ServicepageActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServiceRecyclerView extends RecyclerView.Adapter<ServiceRecyclerView.ViewHolder> {

    private final Context context;
    private final List<Service> list;

    public ServiceRecyclerView(Context context, List<Service> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_view_element_service, parent, false);

        return new ServiceRecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service element = list.get(position);

        int serviceId = element.getId();
        String name = element.getName();
        String image = Constant.UPLOAD_URI() + element.getImage();

        holder.name.setText(name);
        if( element.getImage().length() > 0)
        {
            Picasso.get().load(image).into(holder.image);
        }
        holder.layout.setOnClickListener(view->{
            Intent intent = new Intent(context, ServicepageActivity.class);
            intent.putExtra("serviceId", String.valueOf(serviceId));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout layout;
        private TextView name;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.elementLayout);
            name = itemView.findViewById(R.id.elementName);
            image = itemView.findViewById(R.id.elementImage);
        }
    }
}
