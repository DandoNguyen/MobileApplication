package com.example.demosqlite.models;

public class searchFilter {
    String tripName;
    String tripDestination;

    //region $getter setter

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDestination() {
        return tripDestination;
    }

    public void setTripDestination(String tripDestination) {
        this.tripDestination = tripDestination;
    }

    //endregion

    //region #constructor

    public searchFilter(String tripName, String tripDestination) {
        this.tripName = tripName;
        this.tripDestination = tripDestination;
    }

    //endregion
}
