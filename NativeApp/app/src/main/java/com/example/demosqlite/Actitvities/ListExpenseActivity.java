package com.example.demosqlite.Actitvities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demosqlite.R;
import com.example.demosqlite.models.ExpenseModel;
import com.example.demosqlite.services.DatabaseHelper;
import com.example.demosqlite.services.DateConversionHelper;
import com.example.demosqlite.services.ListExpenseAdapter;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ListExpenseActivity extends AppCompatActivity {

    ListView lv_listExpense;
    ImageButton btn_addExpense, btn_editExpense, btn_deleteExpense, btn_deleteAllExpense;

    String selectedTripID;

    ExpenseModel selectedExpense;

    AlertDialog globalDialog;
    DatePickerDialog globalDatePicker;
    TimePickerDialog globalTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_expense);
        InitVariable();
        BundleExtract();
        InitListExpense();

        btn_addExpense.setOnClickListener(view -> {
            startExpenseFormActivity();
        });

        btn_editExpense.setOnClickListener(view -> {
            if (selectedExpense != null) {
                editOnClick();
            } else {
                Toast.makeText(this, "No Expense Selected", Toast.LENGTH_SHORT).show();
            }
        });

        btn_deleteExpense.setOnClickListener(view -> {
            if (selectedExpense != null) {
                deleteOnClick();
            } else {
                Toast.makeText(this, "No Expense Selected", Toast.LENGTH_SHORT).show();
            }
        });

        lv_listExpense.setOnItemClickListener((adapterView, view, i, l) -> {
            selectedExpense = (ExpenseModel) adapterView.getItemAtPosition(i);
        });
    }

    private void deleteOnClick() {
        DatabaseHelper db = new DatabaseHelper(this);
        boolean isSucceed = db.deleteExpense(selectedExpense.getExpenseID());
        if (isSucceed) {
            Toast.makeText(this, "Expense Deleted", Toast.LENGTH_SHORT).show();
            reloadActivity();
        } else {
            Toast.makeText(this, "Error Delete Expense", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void editOnClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View layoutView = getLayoutInflater().inflate(R.layout.popup_edit_expense, null);
        builder.setView(layoutView);

        EditText popup_et_type = layoutView.findViewById(R.id.et_expense_type);
        EditText popup_et_amount = layoutView.findViewById(R.id.et_expense_amount);
        EditText popup_et_comment = layoutView.findViewById(R.id.et_expense_comment);
        AppCompatButton popup_btn_date = layoutView.findViewById(R.id.btn_expense_date);
        AppCompatButton popup_btn_time = layoutView.findViewById(R.id.btn_expense_time);

        popup_et_type.setText(selectedExpense.getExpenseType());
        popup_et_amount.setText(Integer.toString(selectedExpense.getExpenseAmount()));
        popup_et_comment.setText(selectedExpense.getExpenseComment());
        DateConversionHelper dateConversionHelper = new DateConversionHelper();
        popup_btn_date.setText(dateConversionHelper.convertToUIDateFormat(selectedExpense.getExpenseTime().substring(0, 10)));
        popup_btn_time.setText(selectedExpense.getExpenseTime().substring(11));

        popup_btn_date.setOnClickListener(view -> {
            DatePickerDialog.OnDateSetListener dateBuilder = (datePicker, year, month, day) -> {
                month++;
                String date = dateConversionHelper.makeDateString(day, month, year);
                popup_btn_date.setText(date);
            };

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            int Style = android.app.AlertDialog.THEME_HOLO_LIGHT;

            globalDatePicker = new DatePickerDialog(this, Style, dateBuilder, year, month, day);
            globalDatePicker.show();
        });

        popup_btn_time.setOnClickListener(view -> {
            final int[] selectedHour = new int[1];
            final int[] selectedMinute = new int[1];
            TimePickerDialog.OnTimeSetListener timeBuilder = (timePicker, hour, minute) -> {
                selectedHour[0] = hour;
                selectedMinute[0] = minute;
                popup_btn_time.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            };

            int style = android.app.AlertDialog.THEME_HOLO_LIGHT;

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, timeBuilder, selectedHour[0], selectedMinute[0], true);
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();
        });

        builder.setTitle("Edit Expense");
        builder.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
            DatabaseHelper db = new DatabaseHelper(this);

            ExpenseModel newExpenseDetail = new ExpenseModel(
                    selectedExpense.getExpenseID(),
                    popup_et_type.getText().toString(),
                    Integer.parseInt(popup_et_amount.getText().toString()),
                    dateConversionHelper.convertToSQLiteDateTimeFormat(popup_btn_date.getText().toString() + " " + popup_btn_time.getText().toString()),
                    popup_et_comment.getText().toString()
                    );

            boolean isSucceed = db.updateExpense(newExpenseDetail);
            if (isSucceed) {
                Toast.makeText(this, "Expense Updated", Toast.LENGTH_SHORT).show();
                reloadActivity();
            } else {
                Toast.makeText(this, "Error update expense", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
           dialogInterface.cancel();
        });

        globalDialog = builder.create();
        globalDialog.show();
    }

    private void reloadActivity(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void startExpenseFormActivity() {
        Intent i = new Intent(this, ExpenseFormActivity.class);
        i.putExtra(getString(R.string.TripID), selectedTripID);
        finish();
        startActivity(i);
    }

    private void BundleExtract() {
        Bundle bundle = getIntent().getExtras();
        selectedTripID = bundle.getString(getString(R.string.TripID));
    }

    private void InitVariable() {
        btn_addExpense = findViewById(R.id.btn_add_expense);
        btn_editExpense = findViewById(R.id.btn_edit_expense);
        btn_deleteExpense = findViewById(R.id.btn_delete_expense);
        btn_deleteAllExpense = findViewById(R.id.btn_delete_all_expense);
        lv_listExpense = findViewById(R.id.lv_list_expense);
    }

    private void InitListExpense() {
        List<ExpenseModel> listExpense;
        DatabaseHelper db = new DatabaseHelper(this);
        listExpense = db.getAllExpenseByTripID(selectedTripID);
        if (listExpense.size() != 0) {
            ListExpenseAdapter listExpenseAdapter = new ListExpenseAdapter(this, listExpense);
            lv_listExpense.setAdapter(listExpenseAdapter);
        } else {
            Toast.makeText(this, getString(R.string.no_expense_data), Toast.LENGTH_SHORT).show();
        }

    }

}