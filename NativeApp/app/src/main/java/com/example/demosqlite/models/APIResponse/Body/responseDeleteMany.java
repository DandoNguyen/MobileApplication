package com.example.demosqlite.models.APIResponse.Body;

public class responseDeleteMany {
    int deletedCount;

    public int getDeletedCount() {
        return deletedCount;
    }

    public void setDeletedCount(int deletedCount) {
        this.deletedCount = deletedCount;
    }

    public responseDeleteMany(int deletedCount) {
        this.deletedCount = deletedCount;
    }
}
