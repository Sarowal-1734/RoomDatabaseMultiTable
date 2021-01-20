package com.sarowal.roomdatabasemultitable.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.sarowal.roomdatabasemultitable.DateTimeConverter;

import java.util.Date;

@Entity(tableName = "expense_table")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String expenseItem;
    //@ColumnInfo(name = "description_column") //optional default is variable(description)
    @TypeConverters(DateTimeConverter.class)
    private Date expenseDate;
    private int expenseAmount;

    public Expense(String expenseItem, Date expenseDate, int expenseAmount) {
        this.expenseItem = expenseItem;
        this.expenseDate = expenseDate;
        this.expenseAmount = expenseAmount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getExpenseItem() {
        return expenseItem;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public int getExpenseAmount() {
        return expenseAmount;
    }
}
