package com.example.myexpenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myexpenses.Adapters.ExpenseAdapter;
import com.example.myexpenses.Database.DatabaseHelper;
import com.example.myexpenses.Entities.Expense;
import com.example.myexpenses.Interface.Expensedao;

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
                databaseHelper.expensedao().clr();
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(200);
                finish();
            }
        });
    }
}