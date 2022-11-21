package com.example.demosqlite.services;

import com.example.demosqlite.models.APIRequest.Body.deleteManyAll;
import com.example.demosqlite.models.APIRequest.Body.filterDateRange;
import com.example.demosqlite.models.APIRequest.Body.filterDateRange_update;
import com.example.demosqlite.models.APIRequest.Body.insertManyTrips;
import com.example.demosqlite.models.APIResponse.Body.responseDeleteMany;
import com.example.demosqlite.models.APIResponse.Body.responseFind;
import com.example.demosqlite.models.APIResponse.Body.responseInsertMany;
import com.example.demosqlite.models.APIResponse.Body.responseUpdateOne;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {
    final String URL = "https://data.mongodb-api.com/app/data-iwkiu/endpoint/data/v1/";
    final String API_KEY = "6a1i4lbnbzxB4x4Xbn8F3g4TtIv9nEP8Dty4k8KWW8UaotNTJv0P2QycfcIQOWnS";
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    API apiService = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(API.class);

    @Headers({
            "Access-Control-Request-Headers: *",
            "Content-Type: application/json",
            "api-key: " + API_KEY
    })
    @POST("action/insertMany")
    Call<responseInsertMany> insertManyTrips(@Body insertManyTrips insertManyTrips);

    @Headers({
            "Access-Control-Request-Headers: *",
            "Content-Type: application/json",
            "api-key: " + API_KEY
    })
    @POST("action/deleteMany")
    Call<responseDeleteMany> dropAllData(@Body deleteManyAll deleteManyAll);

    @Headers({
            "Access-Control-Request-Headers: *",
            "Content-Type: application/json",
            "api-key: " + API_KEY
    })
    @POST("action/find")
    Call<responseFind> findTripByDateRange(@Body filterDateRange filterDateRange);

    @Headers({
            "Access-Control-Request-Headers: *",
            "Content-Type: application/json",
            "api-key: " + API_KEY
    })
    @POST("action/updateOne")
    Call<responseUpdateOne> updateOneTrips(@Body filterDateRange_update filterDateRange_update);
}
