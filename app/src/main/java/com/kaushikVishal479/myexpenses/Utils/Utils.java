package com.kaushikVishal479.myexpenses.Utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import com.kaushikVishal479.myexpenses.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static String dateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return formatter.format(date);
    }

    public static Date stringToDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatePickerDialog getDatePickerDialog(Context context, EditText editText){
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
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our edit text.
                        editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                },
                // on below line we are passing year,
                // month and day for selected date in our date picker.
                year, month, day);
        // Disable future dates
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        // Disable dates more than 30 days in the past
        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.DAY_OF_MONTH, -90);
        datePickerDialog.getDatePicker().setMinDate(pastDate.getTimeInMillis());

        return datePickerDialog;
    }
}
