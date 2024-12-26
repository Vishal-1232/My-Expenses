package com.kaushikVishal479.myexpenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
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
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        show_expense = findViewById(R.id.show_expense);
        total = findViewById(R.id.total);
        reset = findViewById(R.id.reset);

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(MainActivity2.this);
        String totalCost = "Total : "+databaseHelper.expensedao().getPriceSum()+" ₹";
        total.setText(totalCost);
        ArrayList<Expense> list= (ArrayList<Expense>) databaseHelper.expensedao().getAllExpenses();
        expenseAdapter = new ExpenseAdapter(list,MainActivity2.this);
        show_expense.setAdapter(expenseAdapter);
        show_expense.setLayoutManager(new LinearLayoutManager(MainActivity2.this));

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
    }
}