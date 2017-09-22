package com.example.amr.popularmovies;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.popularmovies.Models.ReviewsResponse;
import com.example.amr.popularmovies.Models.TrailersResponse;
import com.example.amr.popularmovies.RetrofitAPIs.APIService;
import com.example.amr.popularmovies.RetrofitAPIs.ApiUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsFragment extends Fragment {
    TextView title_movie, yearView, rate, DescriptionView;
    ImageView imageView;
    TabLayout tabLayout;
    LinearLayout fragment_container;
    APIService mAPIService;
    int ID;
    List<String> listItems, list_trailers;
    List<String> listAuthors, list_reviews;
    private ProgressDialog pdialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View detailsFragment = inflater.inflate(R.layout.fragment_details, container, false);

        listItems = new ArrayList<String>();
        list_trailers = new ArrayList<String>();

        listAuthors = new ArrayList<String>();
        list_reviews = new ArrayList<String>();

        mAPIService = ApiUtils.getAPIService();

        pdialog = new ProgressDialog(getActivity());
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        tabLayout = (TabLayout) detailsFragment.findViewById(R.id.tab_layout);
        fragment_container = (LinearLayout) detailsFragment.findViewById(R.id.fragment_container);
        title_movie = (TextView) detailsFragment.findViewById(R.id.title_movie);
        yearView = (TextView) detailsFragment.findViewById(R.id.year_movie);
        rate = (TextView) detailsFragment.findViewById(R.id.rate_movie);
        DescriptionView = (TextView) detailsFragment.findViewById(R.id.description_movie);
        imageView = (ImageView) detailsFragment.findViewById(R.id.Image_Poster);

        Bundle sentBundle = getArguments();

        ID = sentBundle.getInt("ID");
        getVideosMoviesGET(ID);

        String Title = sentBundle.getString("Title");

        boolean toolbarExist = sentBundle.getBoolean("Toolbar");
        if (toolbarExist) {
            ((DetailsActivity) getActivity()).setTitle(Title);
        } else {
            title_movie.setText(Title);
            title_movie.setVisibility(View.VISIBLE);
        }
        String Year = sentBundle.getString("Year");
        yearView.setText("Year : " + Year);

        Double Rate = sentBundle.getDouble("Rate");
        rate.setText("Rate : " + String.valueOf(Rate) + "/10");

        String Overview = sentBundle.getString("Overview");
        DescriptionView.setText("Description : " + Overview);

        String Image2 = sentBundle.getString("Image2");
        Picasso.with(getActivity()).load(Image2).into(imageView);

        tabLayout.addTab(tabLayout.newTab().setText("Trailers"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    replaceFragment(new TrailersFragment(listItems, list_trailers));
                } else {
                    replaceFragment(new ReviewsFragment(listAuthors, list_reviews));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return detailsFragment;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        transaction.commit();
    }

    public void getVideosMoviesGET(int id) {
        pdialog.show();
        mAPIService.getVideosMovies(id).enqueue(new Callback<TrailersResponse>() {

            @Override
            public void onResponse(Call<TrailersResponse> call, Response<TrailersResponse> response) {

                if (response.isSuccessful()) {

                    TrailersResponse trailersResponse = response.body();
                    List<TrailersResponse.ResultsBean> trailersResponseResults = trailersResponse.getResults();

                    for (int i = 0; i < trailersResponseResults.size(); i++) {
                        listItems.add("Trailer " + (i + 1));
                        list_trailers.add(trailersResponseResults.get(i).getKey());
                    }
                    replaceFragment(new TrailersFragment(listItems, list_trailers));
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<TrailersResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
        pdialog.show();
        mAPIService.getReviewsMovies(id).enqueue(new Callback<ReviewsResponse>() {

            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {

                if (response.isSuccessful()) {

                    ReviewsResponse reviewsResponse = response.body();
                    List<ReviewsResponse.ResultsBean> reviewsResponseResults = reviewsResponse.getResults();

                    for (int i = 0; i < reviewsResponseResults.size(); i++) {
                        listAuthors.add(reviewsResponseResults.get(i).getAuthor());
                        list_reviews.add(reviewsResponseResults.get(i).getContent());
                    }
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });

    }
}
