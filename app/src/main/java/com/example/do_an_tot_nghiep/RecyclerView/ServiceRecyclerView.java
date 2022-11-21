package com.example.do_an_tot_nghiep.RecyclerView;

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
import com.example.do_an_tot_nghiep.Model.Service;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ServiceRecyclerView extends RecyclerView.Adapter<ServiceRecyclerView.ViewHolder> {

    private Context context;
    private List<Service> list;

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

        String name = element.getName();
        String image = Constant.UPLOAD_URI() + element.getImage();

        holder.name.setText(name);
        if( element.getImage().length() > 0)
        {
            Picasso.get().load(image).into(holder.image);
        }
        holder.layout.setOnClickListener(view->{
            Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
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
