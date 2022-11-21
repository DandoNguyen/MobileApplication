package com.example.demosqlite.Actitvities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demosqlite.R;
import com.example.demosqlite.models.ExpenseModel;
import com.example.demosqlite.models.TripModel;
import com.example.demosqlite.services.DatabaseHelper;
import com.example.demosqlite.services.DateConversionHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class ExpenseFormActivity extends AppCompatActivity {


    EditText et_expenseType, et_expenseAmount, et_expenseComment;
    AppCompatButton btn_expenseDate, btn_add_expense, popup_btn_expenseDate, btn_expenseTime, popup_btn_expenseTime;
    TextView tv_tripName, popup_tv_expenseType, pop_tv_expenseAmount, popup_tv_expenseComment;

    DatePickerDialog globalDatePicker;
    AlertDialog globalAlertDialog;

    String tripID;
    TripModel selectedTrip;

    int selectedHour, selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_form);

        variablesInit();
        bundleExtract();

        DateConversionHelper dateConversionHelper = new DateConversionHelper();
        Date defaultTripStartDate = dateConversionHelper.convertToDateTime(selectedTrip.getTripStartDate());
        btn_expenseDate.setText(dateConversionHelper.convertToUIDateString(defaultTripStartDate));
        btn_expenseDate.setOnClickListener(view -> {
            createDatePickerDialog();
        });

        btn_expenseTime.setOnClickListener(view -> {
            createTimePickerDialog();
        });

        btn_add_expense.setOnClickListener(view -> {
            boolean isSucceed = requirementCheck();
            if (isSucceed) {
                createAddDialog();
            }
        });

        et_expenseType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_expenseType.setHintTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_expenseAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_expenseAmount.setHintTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void createTimePickerDialog() {

        TimePickerDialog.OnTimeSetListener builder = (timePicker, hour, minute) -> {
            selectedHour = hour;
            selectedMinute = minute;
            btn_expenseTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, builder, selectedHour, selectedMinute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private boolean requirementCheck() {

        return edittextCheck() && timeButtonCheck();

    }

    private boolean timeButtonCheck() {
        DateConversionHelper dateConversionHelper = new DateConversionHelper();

        Date startDate = dateConversionHelper.convertToDateTime(selectedTrip.getTripStartDate());
        Date endDate = dateConversionHelper.convertToDateTime(selectedTrip.getTripEndDate());

        long startDateLong = startDate.getTime();
        long endDateLong = endDate.getTime();
        long expenseDate = dateConversionHelper.convertToDate(btn_expenseDate.getText().toString()).getTime();

        if (expenseDate >= startDateLong && expenseDate <= endDateLong) {
            return true;
        } else {
            btn_expenseDate.setTextColor(getResources().getColor(R.color.red));
            Toast.makeText(this, "Date must be between " + dateConversionHelper.convertToUIDateString(startDate)
                    + " and " + dateConversionHelper.convertToUIDateString(endDate), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean edittextCheck() {
        String expenseTypeContent = et_expenseType.getText().toString();
        String expenseAmount = et_expenseAmount.getText().toString();

        if (!expenseAmount.equals("") && !expenseTypeContent.equals("")) {
            return true;
        }

        et_expenseType.setHintTextColor(getResources().getColor(R.color.red));
        et_expenseAmount.setHintTextColor(getResources().getColor(R.color.red));

        Toast.makeText(this, R.string.requiredFieldMissing, Toast.LENGTH_SHORT).show();
        return false;
    }

    private void bundleExtract() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tripID = bundle.getString(getString(R.string.TripID));
        }

        getTripName();
    }

    private void getTripName() {
        DatabaseHelper db = new DatabaseHelper(this);
        String tripName = db.getTripNameByID(UUID.fromString(tripID));
        if (tripName == null) {
            finish();
            Toast.makeText(this, R.string.cannotFindTrip, Toast.LENGTH_SHORT).show();
        }

        tv_tripName.setText(tripName);
        selectedTrip = db.GetTripDetailByID(tripID);

    }

    private void createAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        @SuppressLint("InflateParams")
        final View layout = getLayoutInflater().inflate(R.layout.popup_expense, null);

        popup_tv_expenseType = layout.findViewById(R.id.popup_tv_expense_type);
        popup_tv_expenseComment = layout.findViewById(R.id.popup_tv_expense_comment);
        pop_tv_expenseAmount = layout.findViewById(R.id.popup_tv_expense_amount);
        popup_btn_expenseDate = layout.findViewById(R.id.popup_btn_expense_date);
        popup_btn_expenseTime = layout.findViewById(R.id.popup_btn_expense_time);

        popup_tv_expenseType.setText(et_expenseType.getText());
        popup_tv_expenseComment.setText(et_expenseComment.getText());
        pop_tv_expenseAmount.setText(et_expenseAmount.getText());
        popup_btn_expenseDate.setText(btn_expenseDate.getText());
        popup_btn_expenseTime.setText(btn_expenseTime.getText());

        builder.setView(layout);

        builder.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
            addButton();
        });
        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
           dialogInterface.cancel();
        });

        globalAlertDialog = builder.create();
        globalAlertDialog.show();
    }

    private void addButton() {

        boolean isSucceed = addOneExpenseToDB();
        if (isSucceed){
            Toast.makeText(this, R.string.expenseAdded, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.errorAddExpense, Toast.LENGTH_LONG).show();
        }
    }

    private boolean addOneExpenseToDB() {

        //reformat expense amount
        String expenseAmountContent = et_expenseAmount.getText().toString();
        String[] arrayString = expenseAmountContent.split(" ");

        ExpenseModel newExpense = new ExpenseModel(
                UUID.randomUUID(),
                et_expenseType.getText().toString(),
                Integer.parseInt(arrayString[0]),
                btn_expenseDate.getText().toString() + " " + btn_expenseTime.getText().toString(),
                et_expenseComment.getText().toString()
        );

        //add to db
        DatabaseHelper db = new DatabaseHelper(this);
        return db.addOneToTableExpense(newExpense, tripID);
    }

    private void createDatePickerDialog() {

        DateConversionHelper dateConversionHelper = new DateConversionHelper();
        DatePickerDialog.OnDateSetListener DatePickerListener = (datePicker, year, month, day) -> {
            month++;
            String Date = dateConversionHelper.makeDateString(day, month, year);
            btn_expenseDate.setText(Date);
            btn_expenseDate.setTextColor(getResources().getColor(R.color.black));
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int Style = AlertDialog.THEME_HOLO_LIGHT;

        globalDatePicker = new DatePickerDialog(this, Style, DatePickerListener, year, month, day);
        globalDatePicker.show();
    }

    private void variablesInit() {
        btn_expenseTime = findViewById(R.id.btn_expense_time);
        tv_tripName = findViewById(R.id.tv_trip_name);
        et_expenseAmount = findViewById(R.id.et_expense_amount);
        et_expenseComment = findViewById(R.id.et_expense_comment);
        et_expenseType = findViewById(R.id.et_expense_type);
        btn_expenseDate = findViewById(R.id.btn_expense_date);
        btn_add_expense = findViewById(R.id.btn_add_expense);
    }
}