package com.example.myexpenses.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.myexpenses.Entities.Expense;

import java.util.List;

@Dao
public interface Expensedao {

    @Query("select * from expense")
    List<Expense>getAllExpenses();

    @Query("select sum(price) from expense")
    String getPriceSum();

    @Query("delete from expense")
    void clr();

    @Insert
    void addExpense(Expense expense);

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

}
