package com.example.demosqlite.models.APIRequest.Model;

public class updateTripModel {
    ModelTripRequest $set;

    //region $getter setter
    public ModelTripRequest get$set() {
        return $set;
    }

    public void set$set(ModelTripRequest $set) {
        this.$set = $set;
    }
    //endregion

    //region $constructor
    public updateTripModel(ModelTripRequest $set) {
        this.$set = $set;
    }
    //endregion
}
