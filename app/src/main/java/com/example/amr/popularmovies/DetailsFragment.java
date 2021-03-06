package com.example.amr.popularmovies;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amr.popularmovies.DataBase.DBHelper;
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
    TextView title_movie, yearView, rate, DescriptionView, makeAsFavourite;
    ImageView imageView;
    TabLayout tabLayout;
    LinearLayout fragment_container;
    APIService mAPIService;
    int ID;
    List<String> listItems, list_trailers;
    List<String> listAuthors, list_reviews;
    Button Btn_makeAsFavourite;
    String Title, Year, Overview, Image1, Image2;
    boolean toolbarExist, Favourite;
    Double Rate;
    DBHelper dbHelper;
    private ProgressDialog pdialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View detailsFragment = inflater.inflate(R.layout.fragment_details, container, false);

        dbHelper = new DBHelper(getContext());

        listItems = new ArrayList<String>();
        list_trailers = new ArrayList<String>();

        listAuthors = new ArrayList<String>();
        list_reviews = new ArrayList<String>();

        mAPIService = ApiUtils.getAPIService();

        pdialog = new ProgressDialog(getActivity());
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        makeAsFavourite = (TextView) detailsFragment.findViewById(R.id.makeAsFavourite);
        Btn_makeAsFavourite = (Button) detailsFragment.findViewById(R.id.Btn_makeAsFavourite);
        tabLayout = (TabLayout) detailsFragment.findViewById(R.id.tab_layout);
        fragment_container = (LinearLayout) detailsFragment.findViewById(R.id.fragment_container);
        title_movie = (TextView) detailsFragment.findViewById(R.id.title_movie);
        yearView = (TextView) detailsFragment.findViewById(R.id.year_movie);
        rate = (TextView) detailsFragment.findViewById(R.id.rate_movie);
        DescriptionView = (TextView) detailsFragment.findViewById(R.id.description_movie);
        imageView = (ImageView) detailsFragment.findViewById(R.id.Image_Poster);

        Bundle sentBundle = getArguments();

        Favourite = sentBundle.getBoolean("Favourite");

        ID = sentBundle.getInt("ID");
        getVideosMoviesGET(ID);

        Title = sentBundle.getString("Title");

        toolbarExist = sentBundle.getBoolean("Toolbar");
        if (toolbarExist) {
            ((DetailsActivity) getActivity()).setTitle(Title);
        } else {
            title_movie.setText(Title);
            title_movie.setVisibility(View.VISIBLE);
        }
        Year = sentBundle.getString("Year");
        yearView.setText("Year : " + Year);

        Rate = sentBundle.getDouble("Rate");
        rate.setText("Rate : " + String.valueOf(Rate) + "/10");

        Overview = sentBundle.getString("Overview");
        DescriptionView.setText("Description : " + Overview);

        Image1 = sentBundle.getString("Image1");

        Image2 = sentBundle.getString("Image2");
        Picasso.with(getActivity()).load(Image2).into(imageView);

        if (dbHelper.checkmovie(ID).getCount() == 0) {
            Btn_makeAsFavourite.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
            makeAsFavourite.setText("Make As Favourite");
        } else {
            Btn_makeAsFavourite.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
            makeAsFavourite.setText("Make As UnFavourite");
        }

        Btn_makeAsFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbHelper.checkmovie(ID).getCount() > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("UnFavourite it ?")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dbHelper.deleterow(Title);
                                    Toast.makeText(getActivity(), "UnFavourite Successfully", Toast.LENGTH_SHORT).show();
                                    Btn_makeAsFavourite.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                                    makeAsFavourite.setText("Make As Favourite");
                                    if (toolbarExist)
                                        ((DetailsActivity) getActivity()).finish();

                                }
                            }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog d = builder.create();
                    d.setTitle("Are you sure");
                    d.show();
                } else {
                    if (dbHelper.checkmovie(ID).getCount() == 0) {
                        dbHelper.addMovie(ID, Image1, Image2, Title, Rate, Year, Overview);
                        Toast.makeText(getActivity(), "Favourite Successfully", Toast.LENGTH_SHORT).show();
                        Btn_makeAsFavourite.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                        makeAsFavourite.setText("Make As UnFavourite");
                    }
                }
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("Trailers"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    if (listItems.size() == 0)
                        Toast.makeText(getActivity(), "Nothing to show", Toast.LENGTH_SHORT).show();
                    replaceFragment(new TrailersFragment(listItems, list_trailers));
                } else {
                    if (listAuthors.size() == 0)
                        Toast.makeText(getActivity(), "Nothing to show", Toast.LENGTH_SHORT).show();
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
                    if (listItems.size() == 0)
                        Toast.makeText(getActivity(), "Nothing to show", Toast.LENGTH_SHORT).show();
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
