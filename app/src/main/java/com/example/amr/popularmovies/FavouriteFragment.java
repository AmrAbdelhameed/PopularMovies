package com.example.amr.popularmovies;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.amr.popularmovies.Adapters.FavouriteGridViewAdapter;
import com.example.amr.popularmovies.DataBase.MovieProvider;
import com.example.amr.popularmovies.Models.MainResponse;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment {

    MainResponse.ResultsBean resultsBean;
    private GridView FavouriteGrideView;
    private List<MainResponse.ResultsBean> mGridData;
    private FavouriteGridViewAdapter mGridAdapter;
    private TabletMoodFavourite mListener;

    void setNameListener(TabletMoodFavourite TabletMoodFavourite) {
        this.mListener = TabletMoodFavourite;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_favourite, container, false);

        FavouriteGrideView = (GridView) fragment.findViewById(R.id.FavouriteGrideView);

        mGridData = new ArrayList<MainResponse.ResultsBean>();

        Uri bookUri = MovieProvider.MOVIE_CONTENT_URI;
        Cursor movieCursor = getActivity().getContentResolver().query(bookUri, new String[]{"idd", "imageposter", "imageback", "title", "rate", "year", "overview"}, null, null, null);
        while (movieCursor.moveToNext()) {
            resultsBean = new MainResponse.ResultsBean();

            int mmid = movieCursor.getInt(0);
            String imagemovie1 = movieCursor.getString(1);
            String imagemovie2 = movieCursor.getString(2);
            String movietitle = movieCursor.getString(3);
            Double movierate = movieCursor.getDouble(4);
            String movieyear = movieCursor.getString(5);
            String mmoverview = movieCursor.getString(6);

            resultsBean.setId(mmid);
            resultsBean.setPoster_path(imagemovie1);
            resultsBean.setBackdrop_path(imagemovie2);
            resultsBean.setTitle(movietitle);
            resultsBean.setVote_average(movierate);
            resultsBean.setRelease_date(movieyear);
            resultsBean.setOverview(mmoverview);

            mGridData.add(resultsBean);
        }
        movieCursor.close();

        mGridAdapter = new FavouriteGridViewAdapter(getActivity(), R.layout.grid_item_layout, mGridData);
        FavouriteGrideView.setAdapter(mGridAdapter);

        FavouriteGrideView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                int ID = mGridData.get(position).getId();
                String Title = mGridData.get(position).getTitle();
                String Year = mGridData.get(position).getRelease_date();
                Double Rate = mGridData.get(position).getVote_average();
                String Overview = mGridData.get(position).getOverview();
                String Image1 = mGridData.get(position).getPoster_path();
                String Image2 = mGridData.get(position).getBackdrop_path();

                mListener.setSelectedName(ID, Title, Year, Rate, Overview, Image1, Image2);
            }

        });

        return fragment;
    }
}
