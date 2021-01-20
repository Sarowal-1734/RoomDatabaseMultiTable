package com.sarowal.roomdatabasemultitable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditIncomeActivity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener myDateSetLister;

    Calendar calendar = Calendar.getInstance();
    private EditText etTitle;
    private TextView tvDate;
    private NumberPicker numberPickerPriority;

    public static final String EXTRA_ID = "com.sarowal.roomdatabase.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.sarowal.roomdatabase.EXTRA_TITLE";
    public static final String EXTRA_DATE = "com.sarowal.roomdatabase.EXTRA_DESCRIPTION";
    public static final String EXTRA_AMOUNT = "com.sarowal.roomdatabase.EXTRA_PRIORITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_income);

        etTitle = findViewById(R.id.etTitle);
        tvDate = findViewById(R.id.tvDate);
        numberPickerPriority = findViewById(R.id.npPriority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddEditIncomeActivity.this,
                        android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,
                        myDateSetLister, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        myDateSetLister = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                String formatDate = simpleDateFormat.format(calendar.getTime());
                //Date date = simpleDateFormat.parse(formatDate);
                Toast.makeText(AddEditIncomeActivity.this, ""+calendar.getTime(), Toast.LENGTH_SHORT).show();
                //tvDate.setText(formatDate);
            }
        };


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Income");

            Long l = intent.getLongExtra(EXTRA_DATE,0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = simpleDateFormat.format(l);

            etTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            tvDate.setText("Date: "+date);
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_AMOUNT, 1));
        } else {
            setTitle("Add Income");
            Date currentDate = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = simpleDateFormat.format(currentDate);
            tvDate.setText("Date: "+date);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = etTitle.getText().toString();
        String date = tvDate.getText().toString();
        int priority = numberPickerPriority.getValue();
        if (title.trim().isEmpty() || date.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and date", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        //data.putExtra(EXTRA_DESCRIPTION,date);
        data.putExtra(EXTRA_AMOUNT, priority);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }
}