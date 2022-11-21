package com.example.demosqlite.Actitvities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demosqlite.R;
import com.example.demosqlite.models.TripModel;
import com.example.demosqlite.models.searchFilter;
import com.example.demosqlite.services.DatabaseHelper;
import com.example.demosqlite.services.ListTripItemAdapter;

import java.util.List;

public class SearchTripActivity extends AppCompatActivity {

    ListView listView;
    EditText et_tripName, et_tripDestination;
    ImageButton btn_searchTrip, btn_tripDetail;
    List<TripModel> listTrips;
    ListTripItemAdapter tripItemAdapter;

    TripModel selectedTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip);

        et_tripName = findViewById(R.id.et_trip_name);
        et_tripDestination = findViewById(R.id.et_trip_destination);
        listView = findViewById(R.id.lv_list_all_trips);
        btn_searchTrip = findViewById(R.id.btn_search_trip);
        btn_tripDetail = findViewById(R.id.btn_trip_detail);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            selectedTrip = (TripModel) adapterView.getItemAtPosition(i);
        });

        btn_tripDetail.setOnClickListener(view -> {
            if (selectedTrip != null) {
                detailButton();
            } else {
                Toast.makeText(this, "No selected Trip", Toast.LENGTH_SHORT).show();
            }
        });

        btn_searchTrip.setOnClickListener(view -> {
            if (!et_tripName.getText().equals("")) {
                selectedTrip = null;
                searchButton();
            } else {
                Toast.makeText(this, "No search value input", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void detailButton() {
        Intent i = new Intent(this, TripDetailActivity.class);
        i.putExtra(getString(R.string.TripID), selectedTrip.getTripID().toString());
        finish();
        startActivity(i);
    }

    private void searchButton() {
        DatabaseHelper db = new DatabaseHelper(this);

        searchFilter searchFilter = new searchFilter(
                et_tripName.getText().toString(),
                et_tripDestination.getText().toString()
        );

        listTrips = db.searchByFilter(searchFilter);

        if (listTrips != null) {

            tripItemAdapter = new ListTripItemAdapter(this, listTrips);

            try {
                listView.setAdapter(tripItemAdapter);
            }
            catch (Exception ex){
                ex.printStackTrace();
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, R.string.NoTripDataMsg, Toast.LENGTH_LONG).show();
        }
    }
}