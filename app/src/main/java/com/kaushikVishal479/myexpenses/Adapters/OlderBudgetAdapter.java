package com.kaushikVishal479.myexpenses.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kaushikVishal479.myexpenses.Entities.MonthYearTotal;
import com.kaushikVishal479.myexpenses.R;

import java.util.ArrayList;

public class OlderBudgetAdapter extends RecyclerView.Adapter<OlderBudgetAdapter.viewHolder> {
    ArrayList<MonthYearTotal> list;
    Context context;

    public OlderBudgetAdapter(ArrayList<MonthYearTotal> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_show_older_budget,parent,false);
        return new OlderBudgetAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        MonthYearTotal monthYearTotal = list.get(position);
        holder.itemCount.setText(String.valueOf(position+1));
        holder.monthYear.setText(monthYearTotal.getMonthYear());
        holder.price.setText("₹ "+String.valueOf(monthYearTotal.getTotalSpendings()));

        //holder.options
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView monthYear, price, itemCount;
        ImageView options;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.cost_1);
            options = itemView.findViewById(R.id.options_1);
            monthYear = itemView.findViewById(R.id.month_year);
            itemCount = itemView.findViewById(R.id.item_count);
        }
    }
}