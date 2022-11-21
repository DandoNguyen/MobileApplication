package com.example.demosqlite.models.APIRequest.Body;

public class DefaultRequestBody {

    //region $fields
    String dataSource;
    String database;
    String collection;

    //endregion

    //region getter setter

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    //endregion

    //region constructor

    public DefaultRequestBody() {

    }

    public DefaultRequestBody(String dataSource, String database, String collection) {
        this.dataSource = dataSource;
        this.database = database;
        this.collection = collection;
    }

    //endregion

}
