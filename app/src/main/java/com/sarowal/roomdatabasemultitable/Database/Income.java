package com.sarowal.roomdatabasemultitable.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.sarowal.roomdatabasemultitable.DateTimeConverter;

import java.util.Date;

@Entity(tableName = "income_table")
public class Income {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String incomeSource;
    //@ColumnInfo(name = "description_column") //optional default is variable(description)
    @TypeConverters(DateTimeConverter.class)
    private Date incomeDate;
    private int incomeAmount;

    public Income(String incomeSource, Date incomeDate, int incomeAmount) {
        this.incomeSource = incomeSource;
        this.incomeDate = incomeDate;
        this.incomeAmount = incomeAmount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getIncomeSource() {
        return incomeSource;
    }

    public Date getIncomeDate() {
        return incomeDate;
    }

    public int getIncomeAmount() {
        return incomeAmount;
    }
}