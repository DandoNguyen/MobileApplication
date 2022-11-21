package com.example.demosqlite.models.APIRequest.Body;

import com.example.demosqlite.models.APIRequest.Model.FilterCreatedDateRange;

public class filterDateRange extends DefaultRequestBody {
    FilterCreatedDateRange filter;

    //region $getter and setter

    public FilterCreatedDateRange getFilter() {
        return filter;
    }

    public void setFilter(FilterCreatedDateRange filter) {
        this.filter = filter;
    }

    //endregion

    //region $constructor

    public filterDateRange(FilterCreatedDateRange filter) {
        this.filter = filter;
    }

    public filterDateRange(String dataSource, String database, String collection, FilterCreatedDateRange filter) {
        super(dataSource, database, collection);
        this.filter = filter;
    }

    //endregion
}
