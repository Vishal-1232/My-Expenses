package com.kaushikVishal479.myexpenses.Adapters;


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

import com.kaushikVishal479.myexpenses.Database.DatabaseHelper;
import com.kaushikVishal479.myexpenses.Entities.Expense;
import com.kaushikVishal479.myexpenses.R;
import com.kaushikVishal479.myexpenses.Utils.Utils;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.viewHolder> {
    ArrayList<Expense> list;
    Context context;

    public ExpenseAdapter(ArrayList<Expense> list, Context context) {
        this.list = list;
        this.context = context;
    }

    // Add a setList method to update the list
    public void setList(ArrayList<Expense> newList) {
        this.list = newList;
        notifyDataSetChanged();  // Notify adapter that the data has changed
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
        holder.date.setText(Utils.dateToString(expense.getDate()));
        String amt = Utils.doubleToString(expense.getPrice())+" ₹";
        holder.price.setText(amt);
        holder.itemId.setText(String.valueOf(expense.getId()));
        if (expense.getExtra().isEmpty()) {
            holder.extra.setVisibility(View.GONE);
        }else {
            // Full text from the expense
            String fullText = expense.getExtra();

            // Truncated text (e.g., first 100 characters)
            int maxLength = 100;
            String truncatedText = fullText.length() > maxLength ? fullText.substring(0, maxLength) + " ..." : fullText;

            // Initially display truncated text
            holder.extra.setText(truncatedText);

            // Set a click listener on the TextView to toggle between truncated and full text
            holder.extra.setOnClickListener(new View.OnClickListener() {
                private boolean isExpanded = false; // Track whether the text is expanded or not

                @Override
                public void onClick(View v) {
                    if (isExpanded) {
                        // Collapse to truncated text
                        holder.extra.setText(truncatedText);
                    } else {
                        // Expand to full text
                        holder.extra.setText(fullText);
                    }
                    isExpanded = !isExpanded;
                }
            });
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
                                date.setText(Utils.dateToString(expense.getDate()));
                                date.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final DatePickerDialog datePickerDialog = Utils.getDatePickerDialog(view.getContext(),date);
                                        datePickerDialog.show();
                                    }
                                });
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

                                        Expense updatedExpense = new Expense(Double.parseDouble(price.getText().toString()), Utils.stringToDate(date.getText().toString()),itemName.getText().toString(),extra.getText().toString());
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
            price = itemView.findViewById(R.id.cost_1);
            itemId = itemView.findViewById(R.id.itmid);
            options = itemView.findViewById(R.id.options_1);
            extra = itemView.findViewById(R.id.extraDetail);
        }
    }
}
