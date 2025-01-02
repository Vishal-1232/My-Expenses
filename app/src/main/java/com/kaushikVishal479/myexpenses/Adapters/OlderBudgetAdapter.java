package com.kaushikVishal479.myexpenses.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kaushikVishal479.myexpenses.Entities.MonthYearTotal;
import com.kaushikVishal479.myexpenses.MainActivity2;
import com.kaushikVishal479.myexpenses.R;
import com.kaushikVishal479.myexpenses.Utils.Utils;

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
        holder.monthYear.setText(formatMonthYear(monthYearTotal.getMonthYear()));
        holder.price.setText("₹ "+ Utils.doubleToString(monthYearTotal.getTotalSpendings()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass selected monthYear to MainActivity2 using Intent
                Intent intent = new Intent(holder.itemView.getContext(), MainActivity2.class);

                // Sending the month-year and totalSpendings data
                intent.putExtra("SELECTED_MONTH_YEAR", monthYearTotal.getMonthYear());
                intent.putExtra("SELECTED_TOTAL_SPENDINGS", monthYearTotal.getTotalSpendings());
                intent.putExtra("TITLE_KEY", formatMonthYear(monthYearTotal.getMonthYear()));
                // Start MainActivity2 with the data
                holder.itemView.getContext().startActivity(intent);
            }
        });
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

    String formatMonthYear(String monthYear){
        String[] parts = monthYear.split("-");
        String month = parts[0];
        String year = parts[1];

        String formattedMonth;
        switch (month) {
            case "01": formattedMonth = "Jan"; break;
            case "02": formattedMonth = "Feb"; break;
            case "03": formattedMonth = "Mar"; break;
            case "04": formattedMonth = "Apr"; break;
            case "05": formattedMonth = "May"; break;
            case "06": formattedMonth = "Jun"; break;
            case "07": formattedMonth = "Jul"; break;
            case "08": formattedMonth = "Aug"; break;
            case "09": formattedMonth = "Sep"; break;
            case "10": formattedMonth = "Oct"; break;
            case "11": formattedMonth = "Nov"; break;
            case "12": formattedMonth = "Dec"; break;
            default: formattedMonth = "Invalid"; // Handle unexpected values
        }

        // Return formatted "Month YYYY"
        return formattedMonth + " " + year;
    }
}
