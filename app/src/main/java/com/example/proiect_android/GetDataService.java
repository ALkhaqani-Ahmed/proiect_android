package com.example.proiect_android;

import com.example.proiect_android.Results;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {
    @Headers({"X-RapidAPI-Key:f7dd2fc62cmsha6806ae6a303f78p165220jsn02dad7b7f27d", "X-RapidAPI-Host:football-prediction-api.p.rapidapi.com"})
    @GET("/api/v2/predictions")
    Call<Results> getResults(@Query("iso_date") String iso_date, @Query("federation") String  federation);


}
