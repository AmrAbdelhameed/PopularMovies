package com.example.amr.popularmovies.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.amr.popularmovies.Models.MainResponse;
import com.example.amr.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainGridViewAdapter extends ArrayAdapter<MainResponse.ResultsBean> {

    private Context mContext;
    private int layoutResourceId;
    private List<MainResponse.ResultsBean> mGridData;

    public MainGridViewAdapter(Context mContext, int layoutResourceId, List<MainResponse.ResultsBean> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }

    public void setGridData(List<MainResponse.ResultsBean> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.Image1 = (ImageView) row.findViewById(R.id.grid_item_image_in_Main);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w320/" + mGridData.get(position).getPoster_path()).into(holder.Image1);
        return row;
    }

    static class ViewHolder {
        ImageView Image1;
    }
}