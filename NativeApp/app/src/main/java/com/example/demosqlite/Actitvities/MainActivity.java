package com.example.demosqlite.Actitvities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demosqlite.R;
import com.example.demosqlite.models.TripModel;
import com.example.demosqlite.services.CloudDatabaseHelper;
import com.example.demosqlite.services.DatabaseHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference: https://stackoverflow.com/questions/22395417/error-strictmodeandroidblockguardpolicy-onnetwork
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button btn_viewAllTrip = findViewById(R.id.bt_view_all_trip);
        Button btn_addNewTrip = findViewById(R.id.bt_add_new_trip);
        Button btn_searchTrip = findViewById(R.id.btn_search_trip);
        Button btn_uploadData = findViewById(R.id.btn_upload_data);


        btn_addNewTrip.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, FormActivity.class);
            startActivity(i);
        });

        btn_viewAllTrip.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, ListTripActivity.class);
            startActivity(i);
        });

        btn_searchTrip.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, SearchTripActivity.class);
            startActivity(i);
        });

        btn_uploadData.setOnClickListener(view -> {
            createAlertDialog();
        });

    }

    private void createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload Data to Cloud");
        builder.setMessage("Do you want to upload all trips info to cloud");
        builder.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
            uploadAllTrips();
        });
        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
            dialogInterface.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void uploadAllTrips() {

        DatabaseHelper localDb = new DatabaseHelper(this);
        List<TripModel> listTrips = localDb.GetAllTrips();

        CloudDatabaseHelper databaseHelper = new CloudDatabaseHelper(this);
        databaseHelper.insertTrips(listTrips);
    }
}