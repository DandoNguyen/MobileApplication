package com.example.demosqlite.models.APIRequest.Body;

public class deleteManyAll extends DefaultRequestBody {
    Object filter;

    public Object getFilter() {
        return filter;
    }

    public void setFilter(Object filter) {
        this.filter = filter;
    }

    public deleteManyAll(String dataSource, String database, String collection, Object filter) {
        super(dataSource, database, collection);
        this.filter = filter;
    }
}
