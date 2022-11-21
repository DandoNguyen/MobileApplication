package com.example.demosqlite.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.demosqlite.models.APIRequest.Body.deleteManyAll;
import com.example.demosqlite.models.APIRequest.Body.filterDateRange;
import com.example.demosqlite.models.APIRequest.Body.filterDateRange_update;
import com.example.demosqlite.models.APIRequest.Model.DateRange;
import com.example.demosqlite.models.APIRequest.Model.FilterCreatedDateRange;
import com.example.demosqlite.models.APIRequest.Model.ModelExpenseRequest;
import com.example.demosqlite.models.APIRequest.Model.ModelTripRequest;
import com.example.demosqlite.models.APIRequest.Body.insertManyTrips;
import com.example.demosqlite.models.APIRequest.Model.updateTripModel;
import com.example.demosqlite.models.APIResponse.Body.responseUpdateOne;
import com.example.demosqlite.models.APIResponse.Model.responseModelFind;
import com.example.demosqlite.models.APIResponse.Body.responseDeleteMany;
import com.example.demosqlite.models.APIResponse.Body.responseFind;
import com.example.demosqlite.models.APIResponse.Body.responseInsertMany;
import com.example.demosqlite.models.ExpenseModel;
import com.example.demosqlite.models.TripModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CloudDatabaseHelper {
    Context context;

    private final String TABLE_TRIP = "TABLE_TRIP";
    private final String TABLE_EXPENSE = "TABLE_EXPENSE";
    private final String DATABASE_NAME = "NativeApp";
    private final String ClusterName = "Cluster1";

    public CloudDatabaseHelper(Context context) {
        this.context = context;
    }

    public void Upload(List<TripModel> listTrips) {
        ArrayList<String> cloudListID = FindByCreatedDateRange();

        ArrayList<TripModel> listUpdateTrip = new ArrayList<>();
        ArrayList<TripModel> listInsertTrip = new ArrayList<>();

        for (TripModel localTrip: listTrips) {
            for (String cloudTripID: cloudListID) {
                if (localTrip.getTripID().toString().equals(cloudTripID)) {
                    listUpdateTrip.add(localTrip);
                    break;
                } else {
                    listInsertTrip.add(localTrip);
                    break;
                }
            }
        }

        if (listInsertTrip.size() != 0) {
            insertTrips(listInsertTrip);
        }
    }

    public void insertTrips(List<TripModel> listTrips) {

        deleteManyAll deleteManyAll = new deleteManyAll(ClusterName, DATABASE_NAME, TABLE_TRIP, new Object());
        API.apiService.dropAllData(deleteManyAll).enqueue(new Callback<responseDeleteMany>() {
            @Override
            public void onResponse(Call<responseDeleteMany> call, Response<responseDeleteMany> response) {

            }

            @Override
            public void onFailure(Call<responseDeleteMany> call, Throwable t) {

            }
        });

        DateConversionHelper dateConversionHelper = new DateConversionHelper();
        ArrayList<ModelTripRequest> newList = new ArrayList<>();
        for(TripModel trip: listTrips) {
            ModelTripRequest newTrip = new ModelTripRequest(
                    trip.getTripID(),
                    trip.getTripName(),
                    trip.getTripDest(),
                    dateConversionHelper.useMongoDate(trip.getTripStartDate()),
                    dateConversionHelper.useMongoDate(trip.getTripEndDate()),
                    trip.isRiskAssessmentChecked(),
                    trip.getTripDesc(),
                    "",
                    GetAllAvailableExpenses(trip.getTripID()),
                    trip.getCreatedDate(),
                    trip.getUpdatedDate()

            );
            newList.add(newTrip);
        }

        insertManyTrips insertManyTrips = new insertManyTrips(
                ClusterName,
                DATABASE_NAME,
                TABLE_TRIP,
                newList
        );

        API.apiService.insertManyTrips(insertManyTrips).enqueue(new Callback<responseInsertMany>() {
            @Override
            public void onResponse(Call<responseInsertMany> call, retrofit2.Response<responseInsertMany> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "Total records inserted: " + newList.size() + " records", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<responseInsertMany> call, Throwable t) {
                Log.e("TAG", "onFailure: ", t);
            }
        });
    }



    private ArrayList<String> FindByCreatedDateRange() {
        DatabaseHelper db = new DatabaseHelper(context);
        DateRange dateRange = db.GetLocalCreatedDateRange();
        FilterCreatedDateRange filter = new FilterCreatedDateRange(dateRange);
        filterDateRange body = new filterDateRange(
                ClusterName,
                DATABASE_NAME,
                TABLE_TRIP,
                filter
        );

        ArrayList<String> listUUID = new ArrayList<>();

        API.apiService.findTripByDateRange(body).enqueue(new Callback<responseFind>() {
            @Override
            public void onResponse(Call<responseFind> call, Response<responseFind> response) {
                if (response.isSuccessful()) {
                    for (responseModelFind trip: response.body().getDocuments()) {
                        listUUID.add(trip.getTripID().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<responseFind> call, Throwable t) {
                Toast.makeText(context, "Error call api", Toast.LENGTH_SHORT).show();
            }
        });

        return listUUID;
    }

    private ArrayList<ModelExpenseRequest> GetAllAvailableExpenses(UUID tripID) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<ExpenseModel> allExpenses = databaseHelper.getAllExpenseByTripID(tripID.toString());

        ArrayList<ModelExpenseRequest> newListExpenses = new ArrayList<>();
        if (allExpenses.size() != 0) {
            for (ExpenseModel expense: allExpenses) {
                ModelExpenseRequest newExpense = new ModelExpenseRequest(
                        expense.getExpenseType(),
                        expense.getExpenseAmount(),
                        expense.getExpenseTime(),
                        expense.getExpenseComment()
                );
                newListExpenses.add(newExpense);
            }
        }
        return newListExpenses;
    }
}
