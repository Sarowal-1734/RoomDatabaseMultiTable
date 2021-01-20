package com.sarowal.roomdatabasemultitable.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IncomeDao {

    @Insert
    void insert(Income income);

    @Update
    void update(Income income);

    @Delete
    void delete(Income income);

    @Query("DELETE FROM income_table")
    void deleteAllIncomes();

    @Query("SELECT * FROM income_table ORDER BY incomeDate DESC")
    LiveData<List<Income>> getAllIncomes();
}