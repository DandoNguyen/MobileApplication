package com.example.demosqlite.models.APIRequest.Body;

import com.example.demosqlite.models.APIRequest.Model.FilterCreatedDateRange;
import com.example.demosqlite.models.APIRequest.Model.updateTripModel;

public class filterDateRange_update extends DefaultRequestBody {
    FilterCreatedDateRange filter;
    updateTripModel update;

    //region $getter setter
    public FilterCreatedDateRange getFilter() {
        return filter;
    }

    public void setFilter(FilterCreatedDateRange filter) {
        this.filter = filter;
    }

    public updateTripModel getUpdate() {
        return update;
    }

    public void setUpdate(updateTripModel update) {
        this.update = update;
    }
    //endregion

    //region $constructor
    public filterDateRange_update(FilterCreatedDateRange filter, updateTripModel update) {
        this.filter = filter;
        this.update = update;
    }

    public filterDateRange_update(String dataSource, String database, String collection, FilterCreatedDateRange filter, updateTripModel update) {
        super(dataSource, database, collection);
        this.filter = filter;
        this.update = update;
    }
    //endregion
}
