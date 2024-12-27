package com.kaushikVishal479.myexpenses;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kaushikVishal479.myexpenses.Adapters.OlderBudgetAdapter;
import com.kaushikVishal479.myexpenses.Database.DatabaseHelper;
import com.kaushikVishal479.myexpenses.Entities.Expense;
import com.kaushikVishal479.myexpenses.Entities.MonthYearTotal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OlderBudgetScreen extends AppCompatActivity {
    RecyclerView showOlderBudgetRecyclerView;
    OlderBudgetAdapter olderBudgetAdapter;
    DatabaseHelper databaseHelper;
    ArrayList<MonthYearTotal> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_older_budget_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        showOlderBudgetRecyclerView = findViewById(R.id.older_budget_recycler_view);
        databaseHelper = DatabaseHelper.getDB(OlderBudgetScreen.this);
        list= (ArrayList<MonthYearTotal>) databaseHelper.expensedao().getMonthYearWithTotalPrice();
        // Check if the list is not null and has items
        if (list != null && !list.isEmpty()) {
            for (MonthYearTotal monthYearTotal : list) {
                // Print each record to the log
                Log.d("TEST", "Month-Year: " + monthYearTotal.getMonthYear() + ", Total Spendings: " + monthYearTotal.getTotalSpendings());
            }
        } else {
            Log.d("TEST", "No data found.");
        }
        olderBudgetAdapter = new OlderBudgetAdapter(list,this);
        showOlderBudgetRecyclerView.setAdapter(olderBudgetAdapter);
        showOlderBudgetRecyclerView.setLayoutManager(new LinearLayoutManager(OlderBudgetScreen.this));
    }
}