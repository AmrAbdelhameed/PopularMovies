package com.example.amr.popularmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.amr.popularmovies.Adapters.MainGridViewAdapter;
import com.example.amr.popularmovies.DataBase.MovieProvider;
import com.example.amr.popularmovies.Models.MainResponse;
import com.example.amr.popularmovies.RetrofitAPIs.APIService;
import com.example.amr.popularmovies.RetrofitAPIs.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment {

    APIService mAPIService;
    private GridView mGridView;
    private List<MainResponse.ResultsBean> mGridData;
    private MainGridViewAdapter mGridAdapter;
    private TabletMood mListener;
    private ProgressDialog pdialog;

    void setNameListener(TabletMood tabletMood) {
        this.mListener = tabletMood;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_main, container, false);

        mAPIService = ApiUtils.getAPIService();

        pdialog = new ProgressDialog(getActivity());
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        mGridView = (GridView) fragment.findViewById(R.id.MainGriidView);

        mGridData = new ArrayList<MainResponse.ResultsBean>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferencesMovieTheme", Context.MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "popular");
        String Titletheme = sharedPreferences.getString("Title", "Popular");

        ((MainActivity) getActivity()).setTitle(Titletheme);

        getMoviesGET(theme);

        return fragment;
    }

    public void getMoviesGET(String theme) {
        pdialog.show();
        mAPIService.getMovies(theme).enqueue(new Callback<MainResponse>() {

            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {

                if (response.isSuccessful()) {

                    final MainResponse mainResponse = response.body();

                    mGridData = mainResponse.getResults();

                    mGridAdapter = new MainGridViewAdapter(getActivity(), R.layout.grid_item_layout, mainResponse.getResults());
                    mGridView.setAdapter(mGridAdapter);

                    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                            int ID = mGridData.get(position).getId();
                            String Title = mGridData.get(position).getTitle();
                            String Year = mGridData.get(position).getRelease_date();
                            Double Rate = mGridData.get(position).getVote_average();
                            String Overview = mGridData.get(position).getOverview();
                            String Image1 = "https://image.tmdb.org/t/p/w154/" + mGridData.get(position).getPoster_path();
                            String Image2 = "https://image.tmdb.org/t/p/w154/" + mGridData.get(position).getBackdrop_path();

                            mListener.setSelectedName(ID, Title, Year, Rate, Overview, Image1, Image2);
                        }

                    });
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
                pdialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.popular) {
            ChangeTheme("popular", "Popular");
            return true;
        } else if (id == R.id.top_rated) {
            ChangeTheme("top_rated", "Top Rated");
            return true;
        } else if (id == R.id.now_playing) {
            ChangeTheme("now_playing", "Now Playing");
            return true;
        } else if (id == R.id.upcoming) {
            ChangeTheme("upcoming", "Up Coming");
            return true;
        } else if (id == R.id.action_refresh) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferencesMovieTheme", Context.MODE_PRIVATE);
            String theme = sharedPreferences.getString("theme", "popular");
            String Titletheme = sharedPreferences.getString("Title", "Popular");

            ((MainActivity) getActivity()).setTitle(Titletheme);
            getMoviesGET(theme);
            return true;
        } else if (id == R.id.action_favourite) {
            Intent i = new Intent(getActivity(), FavouriteActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ChangeTheme(String theme, String Title) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferencesMovieTheme", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("theme", theme);
        editor.putString("Title", Title);
        editor.apply();
        getMoviesGET(theme);
        ((MainActivity) getActivity()).setTitle(Title);
    }
}
