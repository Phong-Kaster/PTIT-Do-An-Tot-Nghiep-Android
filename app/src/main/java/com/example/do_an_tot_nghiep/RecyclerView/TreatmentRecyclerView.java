package com.example.do_an_tot_nghiep.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an_tot_nghiep.Model.Treatment;
import com.example.do_an_tot_nghiep.R;

import org.w3c.dom.Text;

import java.util.List;

public class TreatmentRecyclerView extends RecyclerView.Adapter<TreatmentRecyclerView.ViewHolder> {

    private final Context context;
    private final List<Treatment> list;

    public TreatmentRecyclerView(Context context, List<Treatment> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recycler_view_element_treatment, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Treatment treatment = list.get(position);

        String name = context.getString(R.string.name) + ": "+ treatment.getName();
        String type = context.getString(R.string.type) + ": " + treatment.getType();
        String times = context.getString(R.string.times) + ": " + treatment.getTimes();
        String purpose = context.getString(R.string.purpose) + ": " + treatment.getPurpose();
        String instruction = context.getString(R.string.instruction) + ": " + treatment.getInstruction();
        String repeatDays = context.getString(R.string.repeat) + ": " + treatment.getRepeatDays();
        String repeatTime = context.getString(R.string.time) + ": " + treatment.getRepeatTime();

        holder.repeatDays.setText(repeatDays);
        holder.repeatTime.setText(repeatTime);
        holder.name.setText(name);
        holder.type.setText(type);
        holder.times.setText(times);
        holder.purpose.setText(purpose);
        holder.instruction.setText(instruction);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView type;
        private final TextView times;
        private final TextView purpose;
        private final TextView instruction;
        private final TextView repeatDays;
        private final TextView repeatTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.elementName);
            type = itemView.findViewById(R.id.elementType);
            times = itemView.findViewById(R.id.elementTimes);
            purpose = itemView.findViewById(R.id.elementPurpose);
            instruction = itemView.findViewById(R.id.elementInstruction);
            repeatDays = itemView.findViewById(R.id.elementRepeatDays);
            repeatTime = itemView.findViewById(R.id.elementRepeatTime);
        }
    }
}
