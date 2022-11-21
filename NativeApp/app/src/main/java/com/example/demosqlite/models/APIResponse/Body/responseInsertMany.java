package com.example.demosqlite.models.APIResponse.Body;

public class responseInsertMany {
    String[] insertedIds;

    public String[] getInsertedIds() {
        return insertedIds;
    }

    public void setInsertedIds(String[] insertedIds) {
        this.insertedIds = insertedIds;
    }

    public responseInsertMany(String[] insertedIds) {
        this.insertedIds = insertedIds;
    }
}
