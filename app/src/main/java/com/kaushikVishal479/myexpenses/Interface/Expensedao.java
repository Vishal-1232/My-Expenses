package com.kaushikVishal479.myexpenses.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kaushikVishal479.myexpenses.Entities.Expense;
import com.kaushikVishal479.myexpenses.Entities.MonthYearTotal;

import java.util.List;

@Dao
public interface Expensedao {

    @Query("select * from expense where strftime('%Y-%m', DATETIME(date / 1000, 'unixepoch', 'localtime')) = strftime('%Y-%m', 'now') ORDER BY itemName")
    List<Expense>getAllExpensesByItemName();

    @Query("select * from expense where strftime('%Y-%m', DATETIME(date / 1000, 'unixepoch', 'localtime')) = strftime('%Y-%m', 'now') ORDER BY date")
    List<Expense>getAllExpensesByDateOldToNew();

    @Query("select * from expense where strftime('%Y-%m', DATETIME(date / 1000, 'unixepoch', 'localtime')) = strftime('%Y-%m', 'now') ORDER BY date DESC")
    List<Expense>getAllExpensesByDateNewToOld();

    @Query("select * from expense where strftime('%Y-%m', DATETIME(date / 1000, 'unixepoch', 'localtime')) = strftime('%Y-%m', 'now') ORDER BY price")
    List<Expense>getAllExpensesByPriceLowToHigh();

    @Query("select * from expense where strftime('%Y-%m', DATETIME(date / 1000, 'unixepoch', 'localtime')) = strftime('%Y-%m', 'now') ORDER BY price DESC")
    List<Expense>getAllExpensesByPriceHighToLow();

    @Query("select sum(price) from expense where strftime('%Y-%m', DATETIME(date / 1000, 'unixepoch', 'localtime')) = strftime('%Y-%m', 'now')")
    String getPriceSum();

    @Query("delete from expense")
    void clr();

    @Query("Select strftime('%m-%Y', DATETIME(date / 1000, 'unixepoch', 'localtime')) as monthYear, SUM(price) as totalSpendings from expense group by strftime('%m-%Y', DATETIME(date / 1000, 'unixepoch', 'localtime')) order by monthYear DESC")
    List<MonthYearTotal> getMonthYearWithTotalPrice();

    @Insert
    void addExpense(Expense expense);

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

}
