package com.kaushikVishal479.myexpenses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaushikVishal479.myexpenses.Adapters.ExpenseAdapter;
import com.kaushikVishal479.myexpenses.Database.DatabaseHelper;
import com.kaushikVishal479.myexpenses.Entities.Expense;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView show_expense;
    ExpenseAdapter expenseAdapter;
    TextView total;
    DatabaseHelper databaseHelper;
    ArrayList<Expense> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        show_expense = findViewById(R.id.show_expense);
        total = findViewById(R.id.total);

        databaseHelper = DatabaseHelper.getDB(MainActivity2.this);
        String totalCost = "Total : "+databaseHelper.expensedao().getPriceSum()+" ₹";
        total.setText(totalCost);
        list= (ArrayList<Expense>) databaseHelper.expensedao().getAllExpensesByItemName();
        expenseAdapter = new ExpenseAdapter(list,MainActivity2.this);
        show_expense.setAdapter(expenseAdapter);
        show_expense.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity_2, menu);
        MenuCompat.setGroupDividerEnabled(menu, true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_name:
                list = (ArrayList<Expense>) databaseHelper.expensedao().getAllExpensesByItemName();
                expenseAdapter.setList(list);
                return true;
            case R.id.action_sort_date_old_to_new:
                list = (ArrayList<Expense>) databaseHelper.expensedao().getAllExpensesByDateOldToNew();
                expenseAdapter.setList(list);
                return true;
            case R.id.action_sort_date_new_to_old:
                list = (ArrayList<Expense>) databaseHelper.expensedao().getAllExpensesByDateNewToOld();
                expenseAdapter.setList(list);
                return true;
            case R.id.action_sort_price_low_to_high:
                list = (ArrayList<Expense>) databaseHelper.expensedao().getAllExpensesByPriceLowToHigh();
                expenseAdapter.setList(list);
                return true;
            case R.id.action_sort_price_high_to_low:
                list = (ArrayList<Expense>) databaseHelper.expensedao().getAllExpensesByPriceHighToLow();
                expenseAdapter.setList(list);
                return true;
            case R.id.action_clear:
                clear();
                return true;
            case R.id.action_view_older_budgets:
                startActivity(new Intent(MainActivity2.this,OlderBudgetScreen.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void clear(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity2.this);
        alertDialog.setTitle("Reset?");
        alertDialog.setMessage("Are you sure want to Reset the data?");
        alertDialog.setIcon(R.drawable.reset);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseHelper.expensedao().clr();
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(200);
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }
}