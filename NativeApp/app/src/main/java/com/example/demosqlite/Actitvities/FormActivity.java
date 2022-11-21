package com.example.demosqlite.Actitvities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.transition.Visibility;

import com.example.demosqlite.R;
import com.example.demosqlite.models.TripModel;
import com.example.demosqlite.services.DatabaseHelper;
import com.example.demosqlite.services.DateConversionHelper;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

public class FormActivity extends AppCompatActivity {

    private DatePickerDialog StartDatePickerDialog, EndDatePickerDialog;
    AppCompatEditText et_tripName, et_tripDestination, et_tripDesc;
    AppCompatCheckBox cb_isRequiredRA;
    AppCompatButton btn_addTrip, et_tripStartDate, et_tripEndDate, btn_takeImage;
    AppCompatImageButton bt_backToMain;
    byte[] imageByteArray;
    String tripImageFilePath;
    ImageView iv_tripImage;
    AlertDialog confirmDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_trip_form);

        iv_tripImage = findViewById(R.id.iv_trip_image);
        et_tripName = findViewById(R.id.et_trip_name);
        et_tripDesc = findViewById(R.id.et_trip_desc);
        et_tripDestination = findViewById(R.id.et_trip_destination);
        cb_isRequiredRA = findViewById(R.id.cb_risk_assessment);
        btn_addTrip = findViewById(R.id.bt_add_trip);
        btn_takeImage = findViewById(R.id.btn_take_image);
        et_tripStartDate = findViewById(R.id.et_trip_date);
        et_tripEndDate = findViewById(R.id.bt_trip_end_date);
        bt_backToMain = findViewById(R.id.bt_back_to_main);

        et_tripStartDate.setText(R.string.defaultDate);
        et_tripStartDate.setOnClickListener(view -> {
            OpenDatePicker(1);
        });


        DateConversionHelper dateConversionHelper = new DateConversionHelper();
        et_tripEndDate.setText(dateConversionHelper.GetTodayDate());
        et_tripEndDate.setOnClickListener(view -> {
            OpenDatePicker(2);
        });


        //Initialization
        InitDatePicker();
        bundleExtract();


        et_tripName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_tripName.setHintTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_tripDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_tripDestination.setHintTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_tripStartDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                et_tripStartDate.setTextColor(getResources().getColor(R.color.black));
            }
        });

        et_tripEndDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                et_tripEndDate.setTextColor(getResources().getColor(R.color.black));
            }
        });

        btn_takeImage.setOnClickListener(view -> {
            Intent i = new Intent(FormActivity.this, CameraActivity.class);
            startActivityForResult(i, 1001);
        });


        bt_backToMain.setOnClickListener(view -> {
            Intent i = new Intent(FormActivity.this, MainActivity.class);
            finish();
            startActivity(i);
        });

        btn_addTrip.setOnClickListener(view -> {
            boolean requirementCheck = checkForFieldRequirements();
            if (!requirementCheck) {
                Toast.makeText(FormActivity.this, R.string.requiredFieldMissing, Toast.LENGTH_SHORT).show();
            } else {
                confirmPopup();
                confirmDialog.show();
            }
        });
    }

    private void confirmPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Trip Confirmation");
        builder.setMessage("Do you want to add this trip detail\n" +
                        "\n\tTrip Name: " + et_tripName.getText().toString() +
                        "\n\tDestination: " + et_tripDestination.getText().toString() +
                        "\n\tRequire Risk Assessment: " + (cb_isRequiredRA.isChecked() ? "Yes!" : "No!") +
                        "\n\tStart Date: " + et_tripStartDate.getText().toString() +
                        "\n\tEnd Date: " + et_tripEndDate.getText().toString());
        builder.setPositiveButton("Confirm", (dialog, which) -> {
            buttonAddTrip();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            dialog = confirmDialog;
            dialog.cancel();
        });

        confirmDialog = builder.create();

    }

    private boolean checkForFieldRequirements() {
        boolean result = true;

        if (validateEmptyString(et_tripName.getText().toString())){
            et_tripName.setHintTextColor(getResources().getColor(R.color.red));
            result = false;
        }
        if (validateEmptyString(et_tripDestination.getText().toString())){
            et_tripDestination.setHintTextColor(getResources().getColor(R.color.red));
            result = false;
        }
        if (et_tripStartDate.getText().toString().equals(getString(R.string.defaultDate))){
            et_tripStartDate.setTextColor(getResources().getColor(R.color.red));
            result = false;
        }
        return result;
    }

    private boolean validateEmptyString(String string){
        return string.equals("") || string == null;
    }

    private void buttonAddTrip() {
        TripModel newTrip = new TripModel(
                UUID.randomUUID(),
                Objects.requireNonNull(et_tripName.getText()).toString(),
                Objects.requireNonNull(et_tripDestination.getText()).toString(),
                et_tripStartDate.getText().toString(),
                et_tripEndDate.getText().toString(),
                cb_isRequiredRA.isChecked(),
                et_tripDesc.getText().toString().equals("") ? "" : et_tripDesc.getText().toString(),
                imageByteArray
        );
        newTrip.setTripImageFilePath(tripImageFilePath);

        InsertNewTripModelMethod(newTrip);
    }

    private void bundleExtract() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            imageByteArray = bundle.getByteArray("imageByteArray");
            tripImageFilePath = bundle.getString("imageURI");
            Toast.makeText(FormActivity.this, "Image Accepted", Toast.LENGTH_SHORT).show();

            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            iv_tripImage.setImageBitmap(bitmap);
        }
    }

    public  void InitDatePicker() {

        DateConversionHelper dateConversionHelper = new DateConversionHelper();
        OnDateSetListener StartDateSetListener = (datePicker, year, month, day) -> {
            month++;
            String date = dateConversionHelper.makeDateString(day, month, year);
            et_tripStartDate.setText(date);
        };

        OnDateSetListener EndDateSetListener = (datePicker, year, month, day) -> {
            month++;
            String endDate = dateConversionHelper.makeDateString(day, month, year);
            et_tripEndDate.setText(endDate);
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int Style = AlertDialog.THEME_HOLO_LIGHT;

        StartDatePickerDialog = new DatePickerDialog(FormActivity.this, Style, StartDateSetListener, year, month, day);
        EndDatePickerDialog = new DatePickerDialog(FormActivity.this, Style, EndDateSetListener, year, month, day);
    }

    private void OpenDatePicker(int DateType) {
        switch (DateType){
            case 1: StartDatePickerDialog.show(); break;
            case 2: EndDatePickerDialog.show(); break;
        }
    }

    private void InsertNewTripModelMethod(TripModel TripModel) {
        try {
            DatabaseHelper db = new DatabaseHelper(FormActivity.this);

            if (!db.checkEndDateValue(TripModel.getTripStartDate(), TripModel.getTripEndDate())){
                et_tripEndDate.setTextColor(getResources().getColor(R.color.red));
                Toast.makeText(FormActivity.this, R.string.endDateInvalid, Toast.LENGTH_SHORT).show();
            } else {
                boolean isSucceed = db.addOneToTripTable(TripModel);
                if (!isSucceed) {
                    Toast.makeText(FormActivity.this, R.string.ERROR_ADD_TRIP_MSG, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FormActivity.this, R.string.TOAST_TRIP_ADDED, Toast.LENGTH_SHORT).show();
                }
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}