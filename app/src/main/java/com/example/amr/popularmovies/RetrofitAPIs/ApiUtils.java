package com.example.amr.popularmovies.RetrofitAPIs;

public class ApiUtils {

    private ApiUtils() {
    }

    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}