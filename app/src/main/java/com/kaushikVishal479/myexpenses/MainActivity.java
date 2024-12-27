package com.kaushikVishal479.myexpenses;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.kaushikVishal479.myexpenses.Database.DatabaseHelper;
import com.kaushikVishal479.myexpenses.Entities.Expense;
import com.kaushikVishal479.myexpenses.Utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText dateEdt,itemName,price,extra;
    Button save,history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        dateEdt = findViewById(R.id.date);
        itemName = findViewById(R.id.item);
        price = findViewById(R.id.price);
        save = findViewById(R.id.save);
        history = findViewById(R.id.history);
        extra = findViewById(R.id.opt);
        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = Utils.getDatePickerDialog(v.getContext(),dateEdt);
                datePickerDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemName.getText().toString().isEmpty()) {
                    itemName.setError("Required");
                    return;
                }
                if (dateEdt.getText().toString().isEmpty()){
                    dateEdt.setError("Required");
                    return;
                }
                if (price.getText().toString().isEmpty()){
                    price.setError("Required");
                    return;
                }

                databaseHelper.expensedao().addExpense(new Expense((Integer.parseInt(price.getText().toString())), Utils.stringToDate(dateEdt.getText().toString()),itemName.getText().toString(),extra.getText().toString()));

                price.setText("");
                dateEdt.setText("");
                itemName.setText("");
                extra.setText("");
                Toast.makeText(MainActivity.this, "Expense Added", Toast.LENGTH_SHORT).show();
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,MainActivity2.class));
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);
            }
        });
    }
}