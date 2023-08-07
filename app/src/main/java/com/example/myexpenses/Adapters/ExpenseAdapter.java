package com.example.myexpenses.Adapters;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myexpenses.Database.DatabaseHelper;
import com.example.myexpenses.Entities.Expense;
import com.example.myexpenses.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
        holder.itemId.setText(String.valueOf(expense.getId()));
        if (expense.getExtra().isEmpty()) {
            holder.extra.setVisibility(View.GONE);
        }else {
            holder.extra.setText(expense.getExtra());
        }
        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.options);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                Dialog updateDialog = new Dialog(context);
                                updateDialog.setContentView(R.layout.custom_dialog_update);
                                Button update = updateDialog.findViewById(R.id.save);
                                Button cancel = updateDialog.findViewById(R.id.cancel);
                                EditText itemName = updateDialog.findViewById(R.id.item);
                                itemName.setText(expense.getItemName());
                                EditText date = updateDialog.findViewById(R.id.date);
                                date.setText(expense.getDate());
                                EditText price = updateDialog.findViewById(R.id.price);
                                price.setText(String.valueOf(expense.getPrice()));
                                EditText extra = updateDialog.findViewById(R.id.opt);
                                extra.setText(expense.getExtra());
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        updateDialog.dismiss();
                                    }
                                });
                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        updateDialog.dismiss();
                                        Expense updatedExpense = new Expense(Integer.parseInt(price.getText().toString()),date.getText().toString(),itemName.getText().toString(),extra.getText().toString());
                                        updatedExpense.setId(expense.getId());
                                        DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);
                                        databaseHelper.expensedao().updateExpense(updatedExpense);
                                        list.remove(position);
                                        list.add(position,updatedExpense);
                                        notifyItemChanged(position);
                                        // updating total
                                        TextView total = ((Activity)context).findViewById(R.id.total);
                                        String totalCost = "Total : "+databaseHelper.expensedao().getPriceSum()+" ₹";
                                        total.setText(totalCost);
                                    }
                                });
                                updateDialog.show();
                                return true;
                            case R.id.delete:
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                                alertDialog.setTitle("Delete?");
                                alertDialog.setMessage("Are you sure want to delete this expense?");
                                alertDialog.setIcon(R.drawable.delete);
                                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DatabaseHelper databaseHelper = DatabaseHelper.getDB(context);
                                        databaseHelper.expensedao().deleteExpense(expense);
                                        list.remove(position);
                                        notifyItemRemoved(position);
                                        // updating total
                                        TextView total = ((Activity)context).findViewById(R.id.total);
                                        String totalCost = "Total : "+databaseHelper.expensedao().getPriceSum()+" ₹";
                                        total.setText(totalCost);
                                    }
                                });
                                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alertDialog.show();
                                return true;
//                            case R.id.menu3:
//                                //handle menu3 click
//                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView itemName, date, price,itemId,extra;
        ImageView options;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            date = itemView.findViewById(R.id.dateOfPurchase);
            price = itemView.findViewById(R.id.cost);
            itemId = itemView.findViewById(R.id.itmid);
            options = itemView.findViewById(R.id.options);
            extra = itemView.findViewById(R.id.extraDetail);
        }
    }
}
