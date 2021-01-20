package com.sarowal.roomdatabasemultitable.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Calendar;

@androidx.room.Database(entities = {Income.class, Expense.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public abstract ExpenseDao expenseDao();
    public abstract IncomeDao incomeDao();

    public static synchronized Database getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class,"income_expense_databse")
                    .fallbackToDestructiveMigration()
                    //.addCallback(roomCallBack)  //Optional
                    .build();
        }
        return instance;
    }
//
//    //Optional
//    private static Callback roomCallBack = new Callback(){
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAsynctask(instance).execute();
//        }
//    };
//    //Optional
//    private static class PopulateDbAsynctask extends AsyncTask<Void,Void,Void> {
//        private ExpenseDao expenseDao;
//        private IncomeDao incomeDao;
//        public PopulateDbAsynctask(Database db) {
//            expenseDao = db.expenseDao();
//            incomeDao = db.incomeDao();
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            incomeDao.insert(new Income("Income Source 1", Calendar.getInstance().getTime(),10000));
//            incomeDao.insert(new Income("Income Source 2",Calendar.getInstance().getTime(),20000));
//            expenseDao.insert(new Expense("Expense Item 1",Calendar.getInstance().getTime(),20000));
//            return null;
//        }
//    }


}
