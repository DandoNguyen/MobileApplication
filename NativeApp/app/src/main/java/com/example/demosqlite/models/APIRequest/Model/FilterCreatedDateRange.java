package com.example.demosqlite.models.APIRequest.Model;

import java.util.ArrayList;
import java.util.Date;

public class FilterCreatedDateRange {
    DateRange createdDate;

    //region $getter and setter

    public DateRange getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateRange createdDate) {
        this.createdDate = createdDate;
    }

    //endregion

    //region $constructor

    public FilterCreatedDateRange(DateRange createdDate) {
        this.createdDate = createdDate;
    }

    //endregion
}
