package com.example.do_an_tot_nghiep.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.do_an_tot_nghiep.Model.Option;
import com.example.do_an_tot_nghiep.R;

import java.util.List;

public class FilterOptionAdapter extends BaseAdapter {

    private Context context;
    private List<Option> options;

    public FilterOptionAdapter(Context context, List<Option> options){
        this.context = context;
        this.options = options;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View rootView = LayoutInflater.from(context)
                .inflate(R.layout.adapter_element_filter_option, viewGroup, false);

        TextView optionName = rootView.findViewById(R.id.elementName);
        ImageView optionIcon = rootView.findViewById(R.id.elementIcon);

        String name = options.get(i).getName();
        int icon = options.get(i).getIcon();


        optionName.setText(name);
        optionIcon.setImageResource(icon);


        return rootView;
    }
}
