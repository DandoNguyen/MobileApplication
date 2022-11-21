package com.example.demosqlite.models.APIRequest.Model;

import java.util.Date;

public class DateRange {
    Date $gte;
    Date $lte;

    //region $getter and setter

    public Date get$gte() {
        return $gte;
    }

    public void set$gte(Date $gte) {
        this.$gte = $gte;
    }

    public Date get$lte() {
        return $lte;
    }

    public void set$lte(Date $lte) {
        this.$lte = $lte;
    }


    //endregion

    //region $constructor

    public DateRange(Date $gte, Date $lte) {
        this.$gte = $gte;
        this.$lte = $lte;
    }


    //endregion
}
