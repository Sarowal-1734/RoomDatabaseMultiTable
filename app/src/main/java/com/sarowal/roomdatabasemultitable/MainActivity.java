package com.sarowal.roomdatabasemultitable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sarowal.roomdatabasemultitable.Database.Expense;
import com.sarowal.roomdatabasemultitable.Database.Income;
import com.sarowal.roomdatabasemultitable.Database.ViewModel;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewModel viewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private Button btAddIncome, btAddExpense;

    private Boolean clicked = false;
    // Animations
    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    public static int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.btAddIncomeExpense);
        btAddIncome = findViewById(R.id.btAddIncome);
        btAddExpense = findViewById(R.id.btAddExpense);

        // Animations
        rotateOpen = AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(MainActivity.this,R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(MainActivity.this,R.anim.to_bottom_anim);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked == false){
                    btAddIncome.setVisibility(View.VISIBLE);
                    btAddExpense.setVisibility(View.VISIBLE);
                    // Animations
                    btAddIncome.startAnimation(fromBottom);
                    btAddExpense.startAnimation(fromBottom);
                    floatingActionButton.startAnimation(rotateOpen);
                }else {
                    btAddIncome.setVisibility(View.INVISIBLE);
                    btAddExpense.setVisibility(View.INVISIBLE);
                    // Animations
                    btAddIncome.startAnimation(toBottom);
                    btAddExpense.startAnimation(toBottom);
                    floatingActionButton.startAnimation(rotateClose);
                }
                clicked = !clicked;
            }
        });

        btAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditIncomeActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST); // Insert or Update data from MainActivity
            }
        });

        btAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddEditExpenseActivity.class);
                startActivity(intent);
            }
        });

        display(); //To display into recyclerview
    }

    //To display into recyclerview
    private void display() {
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        if (n == 1) {
            IncomeAdapter adapter = new IncomeAdapter();
            recyclerView.setAdapter(adapter);
            viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ViewModel.class);
            viewModel.getAllIncomes().observe(this, new Observer<List<Income>>() {
                @Override
                public void onChanged(List<Income> incomeList) {
                    adapter.setIncomes(incomeList);
                }
            });
            adapter.setOnItemClickListener(new IncomeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Income income) {
                    Intent intent = new Intent(MainActivity.this, AddEditIncomeActivity.class);
                    Long l = DateTimeConverter.fromDate(income.getIncomeDate());

                    intent.putExtra(AddEditIncomeActivity.EXTRA_ID,income.getId());
                    intent.putExtra(AddEditIncomeActivity.EXTRA_TITLE,income.getIncomeSource());
                    intent.putExtra(AddEditIncomeActivity.EXTRA_DATE,l);
                    intent.putExtra(AddEditIncomeActivity.EXTRA_AMOUNT,income.getIncomeAmount());
                    startActivityForResult(intent, EDIT_NOTE_REQUEST);
                }
            });
        } else if (n == 2){
            ExpenseAdapter adapter = new ExpenseAdapter();
            recyclerView.setAdapter(adapter);
            viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ViewModel.class);
            viewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
                @Override
                public void onChanged(List<Expense> expenseList) {
                    adapter.setExpenses(expenseList);
                }
            });
            adapter.setOnItemClickListener(new ExpenseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Expense expense) {
                    Intent intent = new Intent(MainActivity.this, AddEditExpenseActivity.class);
                    Long l = DateTimeConverter.fromDate(expense.getExpenseDate());

                    intent.putExtra(AddEditExpenseActivity.EXTRA_EXPENSE_ID,expense.getId());
                    intent.putExtra(AddEditExpenseActivity.EXTRA_EXPENSE_TITLE,expense.getExpenseItem());
                    intent.putExtra(AddEditExpenseActivity.EXTRA_EXPENSE_DATE,l);
                    intent.putExtra(AddEditExpenseActivity.EXTRA_EXPENSE_AMOUNT,expense.getExpenseAmount());
                    startActivity(intent);
                }
            });
        }

    }

    //Insert or Update Data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditIncomeActivity.EXTRA_TITLE);
            //String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditIncomeActivity.EXTRA_AMOUNT,1);

            Income income = new Income(title, Calendar.getInstance().getTime(),priority);
            viewModel.insertIncome(income);
            Toast.makeText(this, "Income added", Toast.LENGTH_SHORT).show();
        }else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditIncomeActivity.EXTRA_ID,-1);
            if (id == -1){
                Toast.makeText(this, "Income can't be added", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditIncomeActivity.EXTRA_TITLE);
            //String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditIncomeActivity.EXTRA_AMOUNT,1);

            Income income = new Income(title,Calendar.getInstance().getTime(),priority);
            income.setId(id);
            viewModel.updateIncome(income);
            Toast.makeText(this, "Income updated", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Income not updated", Toast.LENGTH_SHORT).show();
        }
    }

    //on Radio button clicked
    public void onRadioButtobnClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbIncome:
                if (checked) {
                    n = 1;
                    display();
                }
                break;
            case R.id.rbExpense:
                if (checked) {
                    n = 2;
                    display();
                }
                break;
        }
    }
}