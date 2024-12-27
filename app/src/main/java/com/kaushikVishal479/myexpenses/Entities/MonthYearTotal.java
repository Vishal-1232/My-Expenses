package com.kaushikVishal479.myexpenses.Entities;

import androidx.room.ColumnInfo;

public class MonthYearTotal {
    @ColumnInfo(name = "monthYear")
    private String monthYear;

    @ColumnInfo(name = "totalSpendings")
    private int totalSpendings;

    public MonthYearTotal(String monthYear, int totalSpendings) {
        this.monthYear = monthYear;
        this.totalSpendings = totalSpendings;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public int getTotalSpendings() {
        return totalSpendings;
    }

    public void setTotalSpendings(int totalSpendings) {
        this.totalSpendings = totalSpendings;
    }
}


