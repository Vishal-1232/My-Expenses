package com.example.myexpenses.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myexpenses.Entities.Expense;
import com.example.myexpenses.R;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.viewHolder> {
    ArrayList<Expense> list;
    Context context;

    public ExpenseAdapter(ArrayList<Expense> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_show_expenses,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Expense expense = list.get(position);
        holder.itemName.setText(expense.getItemName());
        holder.date.setText(expense.getDate());
        String amt = String.valueOf(expense.getPrice())+" ₹";
        holder.price.setText(amt);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView itemName, date, price;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            date = itemView.findViewById(R.id.dateOfPurchase);
            price = itemView.findViewById(R.id.cost);
        }
    }
}
