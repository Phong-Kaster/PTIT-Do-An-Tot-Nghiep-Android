package com.example.do_an_tot_nghiep.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_tot_nghiep.Configuration.Constant;
import com.example.do_an_tot_nghiep.Model.Photo;
import com.example.do_an_tot_nghiep.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookingPhotoRecyclerView extends RecyclerView.Adapter<BookingPhotoRecyclerView.ViewHolder> {

    private final Context context;
    private final List<Photo> list;

    public BookingPhotoRecyclerView(Context context, List<Photo> list)
    {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_view_element_booking_photo, parent, false);

        return new BookingPhotoRecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo element = list.get(position);
        String url = Constant.UPLOAD_URI() + element.getUrl();

        Picasso.get().load(url).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView photo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.elementPhoto);
        }
    }
}
