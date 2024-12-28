package com.kaushikVishal479.myexpenses.Entities;

import androidx.room.ColumnInfo;

public class MonthYearTotal {
    @ColumnInfo(name = "monthYear")
    private String monthYear;

    @ColumnInfo(name = "totalSpendings")
    private double totalSpendings;

    public MonthYearTotal(String monthYear, double totalSpendings) {
        this.monthYear = monthYear;
        this.totalSpendings = totalSpendings;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public double getTotalSpendings() {
        return totalSpendings;
    }

    public void setTotalSpendings(double totalSpendings) {
        this.totalSpendings = totalSpendings;
    }
}


