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

import com.example.do_an_tot_nghiep.Model.Handbook;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.Webpage.WebpageActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HandbookRecyclerView extends RecyclerView.Adapter<HandbookRecyclerView.ViewHolder> {

    private Context context;
    private List<Handbook> list;
    private int layout;

    public HandbookRecyclerView(Context context, List<Handbook> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Handbook handbook = list.get(position);
        String image = handbook.getImage();
        String title = handbook.getTitle();
        String url = handbook.getUrl();


        Picasso.get().load(image).into(holder.image);
        holder.name.setText(title);

        holder.name.setOnClickListener(view->{
            Intent intent = new Intent(context, WebpageActivity.class);
            intent.putExtra("url", url);
            context.startActivity(intent);
        });
        holder.image.setOnClickListener(view->{
            Intent intent = new Intent(context, WebpageActivity.class);
            intent.putExtra("url", url);
            context.startActivity(intent);
        });
        holder.layout.setOnClickListener(view->{
            Intent intent = new Intent(context, WebpageActivity.class);
            intent.putExtra("url", url);
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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.elementLayout);
            image = itemView.findViewById(R.id.elementImage);
            name = itemView.findViewById(R.id.elementName);
        }
    }
}
