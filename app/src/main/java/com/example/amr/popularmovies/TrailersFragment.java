package com.example.amr.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrailersFragment extends Fragment {

    ListView list_item_trailers;
    List<String> listItems, list_trailers;
    ArrayAdapter arrayAdapter;

    public TrailersFragment(List<String> listItems, List<String> list_trailers) {
        this.listItems = listItems;
        this.list_trailers = list_trailers;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_trailers, container, false);

        list_item_trailers = (ListView) v.findViewById(R.id.list_item_trailers);

        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listItems);
        list_item_trailers.setAdapter(arrayAdapter);

        list_item_trailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + list_trailers.get(pos))));
            }
        });

        return v;
    }
}
