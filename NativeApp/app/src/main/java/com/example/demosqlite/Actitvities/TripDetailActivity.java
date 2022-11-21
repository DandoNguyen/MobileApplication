package com.example.demosqlite.Actitvities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demosqlite.R;
import com.example.demosqlite.models.TripModel;
import com.example.demosqlite.services.DatabaseHelper;
import com.example.demosqlite.services.DateConversionHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class TripDetailActivity extends AppCompatActivity {

    DatePickerDialog startDatePickerDialog, endDatePickerDialog;

    TextView tv_tripName, tv_tripDestination, tv_tripDesc;
    CheckBox cb_tripAssessment;
    ImageView iv_tripImage;
    ImageButton btn_editTrip, btn_addTrip, btn_deleteTrip, btn_addExpense;

    AlertDialog.Builder builder;
    AlertDialog editDialog;

    EditText popup_et_tripName, popup_et_tripDestination, popup_et_tripDescription;
    AppCompatButton popup_btn_tripStartDate, popup_btn_tripEndDate, btn_tripStartDate, btn_tripEndDate;
    CheckBox popup_cb_trip_require_assessment;


    String selectedTripID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        btn_addExpense = findViewById(R.id.btn_list_expense);
        btn_addTrip = findViewById(R.id.btn_add_trip);
        btn_editTrip = findViewById(R.id.btn_edit_trip);
        btn_deleteTrip = findViewById(R.id.btn_delete_trip);
        tv_tripName = findViewById(R.id.tv_trip_name);
        tv_tripDestination = findViewById(R.id.tv_trip_dest);
        tv_tripDesc = findViewById(R.id.tv_trip_desc);
        cb_tripAssessment = findViewById(R.id.cb_risk_assessment);
        iv_tripImage = findViewById(R.id.iv_trip_image);
        btn_tripEndDate = findViewById(R.id.btn_trip_end);
        btn_tripStartDate = findViewById(R.id.btn_trip_start);



        //Init
        BundleExtract();
        LoadTripDetail();


        //Button function
        btn_addTrip.setOnClickListener(view -> {
            goToTripForm();
        });

        btn_deleteTrip.setOnClickListener(view -> {
            createDeletePopup();
        });

        btn_editTrip.setOnClickListener(view -> {
            editThisTrip();
        });

        btn_addExpense.setOnClickListener(view -> {
            startListExpenseActivity();
        });
    }

    private void startListExpenseActivity() {
        Intent i = new Intent(this, ListExpenseActivity.class);
        i.putExtra(getString(R.string.TripID), selectedTripID);
        startActivity(i);
    }

    private void editThisTrip() {
        createEditPopup();
    }

    private void createDeletePopup(){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setMessage("Do you want to delete trip " + "'" + tv_tripName.getText().toString() + "'?");
        builder.setPositiveButton("Confirm", ((dialogInterface, i) -> {
            deleteThisTrip();
        }));
        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
           dialogInterface.cancel();
        });

        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();
    }

    private void createEditPopup(){
        builder = new AlertDialog.Builder(this);
        final View editPopupView = getLayoutInflater().inflate(R.layout.popup_edit, null);
        popup_et_tripName = editPopupView.findViewById(R.id.et_trip_name);
        popup_et_tripDestination = editPopupView.findViewById(R.id.et_trip_destination);
        popup_et_tripDescription = editPopupView.findViewById(R.id.et_trip_desc);
        popup_btn_tripStartDate = editPopupView.findViewById(R.id.btn_trip_start);
        popup_btn_tripEndDate = editPopupView.findViewById(R.id.btn_trip_end);
        popup_cb_trip_require_assessment = editPopupView.findViewById(R.id.cb_risk_assessment);
        builder.setPositiveButton("Confirm", (dialog, which) -> {
            updateTripDetail();
            editDialog.dismiss();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            dialog.cancel();
        });

        builder.setView(editPopupView);


        InitDatePicker();
        initPopupData();


        editDialog = builder.create();
        editDialog.show();
    }

    private void updateTripDetail() {
        DatabaseHelper db = new DatabaseHelper(this);

        byte[] emptyByte = new byte[10];

        TripModel newTrip = new TripModel(
                UUID.fromString(selectedTripID),
                Objects.requireNonNull(popup_et_tripName.getText()).toString(),
                popup_et_tripDestination.getText().toString(),
                popup_btn_tripStartDate.getText().toString(),
                popup_btn_tripEndDate.getText().toString(),
                popup_cb_trip_require_assessment.isChecked(),
                popup_et_tripDescription.getText().toString(),
                emptyByte
        );

        newTrip.setUpdatedDate(new Date(System.currentTimeMillis()));

        boolean isSucceed = db.updateTripModel(newTrip);
        if (isSucceed) {
            Intent i = new Intent(this, TripDetailActivity.class);
            i.putExtra(getString(R.string.TripID), selectedTripID);
            finish();
            startActivity(i);
        } else {
            Toast.makeText(this, "Error update trip", Toast.LENGTH_SHORT).show();
        }
    }

    private void initPopupData() {
        popup_et_tripName.setText(tv_tripName.getText());
        popup_et_tripDestination.setText(tv_tripDestination.getText());
        popup_et_tripDescription.setText(tv_tripDesc.getText());
        popup_cb_trip_require_assessment.setChecked(cb_tripAssessment.isChecked());
        popup_btn_tripStartDate.setText(btn_tripStartDate.getText());
        popup_btn_tripEndDate.setText(btn_tripEndDate.getText());

        popup_btn_tripStartDate.setOnClickListener(view -> {
            startDatePickerDialog.show();
        });
        popup_btn_tripEndDate.setOnClickListener(view -> {
            endDatePickerDialog.show();
        });
    }

    public  void InitDatePicker() {

        DateConversionHelper dateConversionHelper = new DateConversionHelper();

        DatePickerDialog.OnDateSetListener StartDateSetListener = (datePicker, year, month, day) -> {
            month++;
            String date = dateConversionHelper.makeDateString(day, month, year);
            popup_btn_tripStartDate.setText(date);
        };

        DatePickerDialog.OnDateSetListener EndDateSetListener = (datePicker, year, month, day) -> {
            month++;
            String endDate = dateConversionHelper.makeDateString(day, month, year);
            popup_btn_tripEndDate.setText(endDate);
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int Style = android.app.AlertDialog.THEME_HOLO_LIGHT;

        startDatePickerDialog = new DatePickerDialog(this, Style, StartDateSetListener, year, month, day);
        endDatePickerDialog = new DatePickerDialog(this, Style, EndDateSetListener, year, month, day);
    }

    private void deleteThisTrip() {
        DatabaseHelper db = new DatabaseHelper(TripDetailActivity.this);
        TripModel existTrip = db.GetTripDetailByID(selectedTripID);
        Intent i = new Intent(TripDetailActivity.this, ListTripActivity.class);

        db.DeleteTrip(existTrip);
        finish();
        startActivity(i);

    }

    private void goToTripForm() {
        Intent i = new Intent(TripDetailActivity.this, FormActivity.class);
        finish();
        startActivity(i);
    }

    private void LoadTripDetail() {
        DatabaseHelper db = new DatabaseHelper(TripDetailActivity.this);
        DateConversionHelper dateConversionHelper = new DateConversionHelper();
        //call trip detail
        TripModel existTrip = db.GetTripDetailByID(selectedTripID);
        tv_tripName.setText(existTrip.getTripName());
        tv_tripDestination.setText(existTrip.getTripDest());
        tv_tripDesc.setText(existTrip.getTripDesc());
        cb_tripAssessment.setChecked(existTrip.isRiskAssessmentChecked());

        Date startDate = dateConversionHelper.convertToDateTime(existTrip.getTripStartDate());
        btn_tripStartDate.setText(dateConversionHelper.convertToUIDateString(startDate));

        Date endDate = dateConversionHelper.convertToDateTime(existTrip.getTripEndDate());
        btn_tripEndDate.setText(dateConversionHelper.convertToUIDateString(endDate));

        if(existTrip.getTripImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(existTrip.getTripImage(), 0, existTrip.getTripImage().length);
            iv_tripImage.setImageBitmap(bitmap);
        }

    }

    private void BundleExtract() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selectedTripID = bundle.getString(getString(R.string.TripID));
        }
    }
}