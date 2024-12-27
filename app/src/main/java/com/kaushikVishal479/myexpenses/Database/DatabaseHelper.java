package com.kaushikVishal479.myexpenses.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.kaushikVishal479.myexpenses.Entities.Expense;
import com.kaushikVishal479.myexpenses.Interface.Expensedao;

import java.util.Date;

@Database(entities={Expense.class},exportSchema = false,version = 1)
@TypeConverters({Converters.class})
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "expense";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getDB(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context,DatabaseHelper.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract Expensedao expensedao();
}

