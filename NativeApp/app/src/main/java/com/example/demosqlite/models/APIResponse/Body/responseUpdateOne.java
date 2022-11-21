package com.example.demosqlite.models.APIResponse.Body;

public class responseUpdateOne {
    int matchedCount;
    int modifiedCount;

    //region $getter and setter

    public int getMatchedCount() {
        return matchedCount;
    }

    public void setMatchedCount(int matchedCount) {
        this.matchedCount = matchedCount;
    }

    public int getModifiedCount() {
        return modifiedCount;
    }

    public void setModifiedCount(int modifiedCount) {
        this.modifiedCount = modifiedCount;
    }

    //endregion

    //region $constructor

    public responseUpdateOne(int matchedCount, int modifiedCount) {
        this.matchedCount = matchedCount;
        this.modifiedCount = modifiedCount;
    }

    //endregion
}
