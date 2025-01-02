package com.kaushikVishal479.myexpenses;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Date;
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

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (!input.isEmpty() && !input.matches("^\\d+(\\.\\d{1,2})?$")) {
                    price.setError("Enter a valid amount (e.g. 50, 50.99)");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        itemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>=20){
                    itemName.setError("Maximum 20 characters allowed");
                }
            }
        });
        extra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>=500){
                    extra.setError("Maximum 500 characters allowed");
                }
            }
        });
        dateEdt.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
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

                databaseHelper.expensedao().addExpense(new Expense((Double.parseDouble(price.getText().toString())), Utils.stringToDate(dateEdt.getText().toString()),itemName.getText().toString(),extra.getText().toString()));

                price.setText("");
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