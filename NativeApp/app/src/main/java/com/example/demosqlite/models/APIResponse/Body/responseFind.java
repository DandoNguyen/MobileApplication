package com.example.demosqlite.models.APIResponse.Body;

import com.example.demosqlite.models.APIResponse.Model.responseModelFind;

import java.util.ArrayList;

public class responseFind {
    ArrayList<responseModelFind> documents;

    //region $getter and setter

    public ArrayList<responseModelFind> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<responseModelFind> documents) {
        this.documents = documents;
    }

    //endregion

    //region $constructor

    public responseFind(ArrayList<responseModelFind> documents) {
        this.documents = documents;
    }

    //endregion
}
