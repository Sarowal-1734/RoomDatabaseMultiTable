package com.sarowal.roomdatabasemultitable.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    private IncomeDao incomeDao;
    private LiveData<List<Income>> allInomes;

    private ExpenseDao expenseDao;
    private LiveData<List<Expense>> allExpenses;

    public Repository(Application application){
        Database database = Database.getInstance(application);
        //IncomeDao
        incomeDao = database.incomeDao();
        allInomes = incomeDao.getAllIncomes();

        //ExpenseDao
        expenseDao = database.expenseDao();
        allExpenses = expenseDao.getAllExpenses();
    }

    // IncomeDao
    public void insertIncome(Income income){
        new InsertIncomeAsynctask(incomeDao).execute(income);
    }

    public void updateIncome(Income income){
        new UpdateIncomeAsynctask(incomeDao).execute(income);
    }

    public void deleteIncome(Income income){
        new DeleteIncomeAsynctask(incomeDao).execute(income);
    }

    public void deleteAllIncomes(){
        new DeleteAllIncomesAsynctask(incomeDao).execute();
    }

    public LiveData<List<Income>> getAllInomes() {
        return allInomes;
    }


    //ExpenseDao
    public void insertExpense(Expense expense){
        new InsertExpenseAsynctask(expenseDao).execute(expense);
    }

    public void updateExpense(Expense expense){
        new UpdateExpenseAsynctask(expenseDao).execute(expense);
    }

    public void deleteExpense(Expense expense){
        new DeleteExpenseAsynctask(expenseDao).execute(expense);
    }

    public void deleteAllExpenses(){
        new DeleteAllExpenseAsynctask(expenseDao).execute();
    }

    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }

    private static class InsertIncomeAsynctask extends AsyncTask<Income,Void,Void>{
        private IncomeDao incomeDao;

        public InsertIncomeAsynctask(IncomeDao incomeDao) {
            this.incomeDao = incomeDao;
        }
        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDao.insert(incomes[0]);
            return null;
        }
    }

    private static class UpdateIncomeAsynctask extends AsyncTask<Income,Void,Void>{
        private IncomeDao incomeDao;

        public UpdateIncomeAsynctask(IncomeDao incomeDao) {
            this.incomeDao = incomeDao;
        }
        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDao.update(incomes[0]);
            return null;
        }
    }

    private static class DeleteIncomeAsynctask extends AsyncTask<Income,Void,Void>{
        private IncomeDao incomeDao;

        public DeleteIncomeAsynctask(IncomeDao incomeDao) {
            this.incomeDao = incomeDao;
        }
        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDao.delete(incomes[0]);
            return null;
        }
    }

    private static class DeleteAllIncomesAsynctask extends AsyncTask<Void,Void,Void>{
        private IncomeDao incomeDao;

        public DeleteAllIncomesAsynctask(IncomeDao incomeDao) {
            this.incomeDao = incomeDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            incomeDao.deleteAllIncomes();
            return null;
        }
    }


    //Expenses
    private static class InsertExpenseAsynctask extends AsyncTask<Expense,Void,Void> {
        private ExpenseDao expenseDao;

        public InsertExpenseAsynctask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }
        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.insert(expenses[0]);
            return null;
        }
    }

    private static class UpdateExpenseAsynctask extends AsyncTask<Expense,Void,Void>{
        private ExpenseDao expenseDao;

        public UpdateExpenseAsynctask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }
        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.update(expenses[0]);
            return null;
        }
    }

    private static class DeleteExpenseAsynctask extends AsyncTask<Expense,Void,Void>{
        private ExpenseDao expenseDao;

        public DeleteExpenseAsynctask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }
        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.delete(expenses[0]);
            return null;
        }
    }

    private static class DeleteAllExpenseAsynctask extends AsyncTask<Void,Void,Void>{
        private ExpenseDao expenseDao;

        public DeleteAllExpenseAsynctask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            expenseDao.deleteAllExpenses();
            return null;
        }
    }

}
