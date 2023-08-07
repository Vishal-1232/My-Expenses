package com.example.myexpenses;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myexpenses.Database.DatabaseHelper;
import com.example.myexpenses.Entities.Expense;

import java.util.ArrayList;
import java.util.Calendar;

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
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                dateEdt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
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
                databaseHelper.expensedao().addExpense(new Expense((Integer.parseInt(price.getText().toString())), dateEdt.getText().toString(),itemName.getText().toString(),extra.getText().toString()));
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