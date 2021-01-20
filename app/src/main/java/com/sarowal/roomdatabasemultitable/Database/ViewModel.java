package com.sarowal.roomdatabasemultitable.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Income>> allIncomes;
    private LiveData<List<Expense>> allExpenses;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allExpenses = repository.getAllExpenses();
        allIncomes = repository.getAllInomes();
    }

    // Income ViewModel
    public void insertIncome(Income income) {
        repository.insertIncome(income);
    }

    public void updateIncome(Income income) {
        repository.updateIncome(income);
    }

    public void deleteIncome(Income income) {
        repository.deleteIncome(income);
    }

    public void deleteAllIncomes() {
        repository.deleteAllIncomes();
    }

    public LiveData<List<Income>> getAllIncomes() {
        return allIncomes;
    }


    // Expense ViewModel
    public void insertExpense(Expense expense) {
        repository.insertExpense(expense);
    }

    public void updateExpense(Expense expense) {
        repository.updateExpense(expense);
    }

    public void deleteExpense(Expense expense) {
        repository.deleteExpense(expense);
    }

    public void deleteAllExpenses() {
        repository.deleteAllExpenses();
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }
}
