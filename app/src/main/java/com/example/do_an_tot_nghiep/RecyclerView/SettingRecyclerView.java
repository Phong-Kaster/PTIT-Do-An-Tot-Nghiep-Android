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
import com.example.do_an_tot_nghiep.Model.Setting;
import com.example.do_an_tot_nghiep.R;
import com.example.do_an_tot_nghiep.Webpage.WebpageActivity;

import java.util.List;

public class SettingRecyclerView extends RecyclerView.Adapter<SettingRecyclerView.ViewHolder> {

    private Context context;
    private List<Setting> list;

    public SettingRecyclerView(Context context, List<Setting> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_view_element_setting, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Setting element = list.get(position);


        holder.icon.setImageResource( element.getIcon() );
        holder.name.setText( element.getName() );
        holder.layout.setOnClickListener(view -> {
            switch (element.getId()){
                case "appearance":
                    break;
                case "appointmentHistory":
                    break;
                case "aboutUs":
                    Intent intent = new Intent(context, WebpageActivity.class);
                    intent.putExtra("url", Constant.VIDEO_PATH());
                    context.startActivity(intent);
                    break;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final ImageView icon;
        private final LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.elementName);
            icon = itemView.findViewById(R.id.elementIcon);
            layout = itemView.findViewById(R.id.elementLayout);
        }
    }

}
