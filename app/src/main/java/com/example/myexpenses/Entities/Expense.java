package com.example.myexpenses.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "price")
    private int price;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "itemName")
    private String itemName;

    public Expense(int id, int price, String date, String itemName) {
        this.id = id;
        this.price = price;
        this.date = date;
        this.itemName = itemName;
    }

    @Ignore
    public Expense(int price, String date, String itemName) {
        this.price = price;
        this.date = date;
        this.itemName = itemName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
