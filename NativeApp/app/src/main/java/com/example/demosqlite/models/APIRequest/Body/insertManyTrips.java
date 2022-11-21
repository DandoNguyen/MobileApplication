package com.example.demosqlite.models.APIRequest.Body;

import com.example.demosqlite.models.APIRequest.Model.ModelTripRequest;

import java.util.ArrayList;

public class insertManyTrips extends DefaultRequestBody {

    ArrayList<ModelTripRequest> documents;

    //region getter and setter

    public ArrayList<ModelTripRequest> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<ModelTripRequest> documents) {
        this.documents = documents;
    }

    //endregion

    //region $constructor

    public insertManyTrips(String dataSource, String database, String collection, ArrayList<ModelTripRequest> documents) {
        super();
        this.dataSource = dataSource;
        this.database = database;
        this.collection = collection;
        this.documents = documents;
    }

    //endregion
}
