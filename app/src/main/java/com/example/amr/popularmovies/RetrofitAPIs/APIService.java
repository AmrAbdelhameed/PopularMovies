package com.example.amr.popularmovies.RetrofitAPIs;

import com.example.amr.popularmovies.Models.MainResponse;
import com.example.amr.popularmovies.Models.ReviewsResponse;
import com.example.amr.popularmovies.Models.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("movie/{theme}?api_key=0378f9d1be05009430ec0b03b4f3b3e8")
    Call<MainResponse> getMovies(@Path("theme") String theme);

    @GET("movie/{id}/videos?api_key=0378f9d1be05009430ec0b03b4f3b3e8")
    Call<TrailersResponse> getVideosMovies(@Path("id") int id);

    @GET("movie/{id}/reviews?api_key=0378f9d1be05009430ec0b03b4f3b3e8")
    Call<ReviewsResponse> getReviewsMovies(@Path("id") int id);

}