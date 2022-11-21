package com.example.demosqlite.Actitvities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.demosqlite.R;
import com.example.demosqlite.databinding.ActivityMainBinding;
import com.example.demosqlite.models.TripModel;
import com.example.demosqlite.services.DatabaseHelper;
import com.example.demosqlite.services.ListTripItemAdapter;

import java.util.List;
import java.util.UUID;

public class ListTripActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    AlertDialog globalDialog;

    ImageButton bt_AddTrip, bt_delete, bt_tripDetail, bt_resetTableTrip;
    ListView lv_listAllTrips;
    TripModel selectedTrip;

    ListTripItemAdapter tripItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_list_trips);

        lv_listAllTrips = findViewById(R.id.lv_list_all_trips);
        bt_AddTrip = findViewById(R.id.btn_add_trip);
        bt_delete = findViewById(R.id.btn_delete_trip);
        bt_tripDetail = findViewById(R.id.btn_trip_detail);
        bt_resetTableTrip = findViewById(R.id.btn_reset_table_trip);



        //Activity method
        InitListTrip();

        bt_resetTableTrip.setOnClickListener(view -> {
            createResetConfirmDialog();
        });

        lv_listAllTrips.setOnItemClickListener((adapterView, view, i, l) -> {
            selectedTrip = (TripModel) adapterView.getItemAtPosition(i);
        });

        bt_delete.setOnClickListener(view -> {
            createDeleteDialog();
        });

        bt_AddTrip.setOnClickListener(view -> {
            Intent i = new Intent(ListTripActivity.this, FormActivity.class);
            finish();
            startActivity(i);
        });

        bt_tripDetail.setOnClickListener(view -> {
            goToTripDetailScreen();
        });
    }

    private void createDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Trip");
        builder.setMessage("Do you want to delete trip: " + selectedTrip.getTripName() + "?");
        builder.setPositiveButton("Confirm", (dialogInterface, i) -> {
            buttonDelete();
        });
        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
           dialogInterface.cancel();
        });

        globalDialog = builder.create();
        globalDialog.show();
    }

    private void buttonDelete() {
        if (selectedTrip != null){
            boolean isSucceed = deleteSelectedTrip(selectedTrip);
            if (isSucceed) {
                reloadActivity();
                Toast.makeText(ListTripActivity.this, R.string.TripDeletedMsg, Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(ListTripActivity.this, R.string.TripDeleteErrorMsg, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ListTripActivity.this, R.string.NoSelectedTripMsg, Toast.LENGTH_SHORT).show();
        }
    }


    private void createResetConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Trip Database");
        builder.setMessage("Do you want to delete all trips?");
        builder.setPositiveButton("Confirm", (dialogInterface, i) -> {
            resetTableTrip();
            dialogInterface.dismiss();
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

    private void goToTripDetailScreen() {
        Intent i = new Intent(ListTripActivity.this, TripDetailActivity.class);
        if (selectedTrip != null) {
            i.putExtra(getString(R.string.TripID), selectedTrip.getTripID().toString());
            finish();
            startActivity(i);
        } else {
            Toast.makeText(ListTripActivity.this, R.string.NoSelectedTripMsg, Toast.LENGTH_SHORT).show();
        }
    }

    private void resetTableTrip() {
        DatabaseHelper db = new DatabaseHelper(ListTripActivity.this);
        boolean isSucceed = db.resetTableTrip();
        if (isSucceed) {
            Toast.makeText(ListTripActivity.this, "Table Trip Clear!", Toast.LENGTH_SHORT).show();
            reloadActivity();
        }
        else {
            Toast.makeText(ListTripActivity.this, "Error resetting table", Toast.LENGTH_LONG).show();
        }
    }

    private boolean deleteSelectedTrip(TripModel selectedTrip){
        DatabaseHelper db = new DatabaseHelper(ListTripActivity.this);

        try {
            return db.DeleteTrip(selectedTrip);
        }
        catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(ListTripActivity.this, R.string.TripDeleteErrorMsg, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void InitListTrip() {
        DatabaseHelper db = new DatabaseHelper(ListTripActivity.this);
        List<TripModel> listTrips = db.GetAllTrips();

        if (listTrips.size() != 0) {

            tripItemAdapter = new ListTripItemAdapter(ListTripActivity.this, listTrips);

            //ArrayAdapter<TripModel> tripArrayAdapter = new ArrayAdapter<TripModel>(ListTripActivity.this, android.R.layout.simple_list_item_1, listTrips);
            try {
                lv_listAllTrips.setAdapter(tripItemAdapter);
            }
            catch (Exception ex){
                ex.printStackTrace();
                Toast.makeText(ListTripActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(ListTripActivity.this, R.string.NoTripDataMsg, Toast.LENGTH_LONG).show();
        }

    }


}