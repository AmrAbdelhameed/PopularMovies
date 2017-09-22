package com.example.amr.popularmovies;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {

    ListView list_item_reviews;
    List<String> listAuthors, list_reviews;
    ArrayAdapter arrayAdapter;

    public ReviewsFragment(List<String> listAuthors, List<String> list_reviews) {
        this.listAuthors = listAuthors;
        this.list_reviews = list_reviews;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reviews, container, false);

        list_item_reviews = (ListView) v.findViewById(R.id.list_item_reviews);

        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listAuthors);
        list_item_reviews.setAdapter(arrayAdapter);

        list_item_reviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(list_reviews.get(pos))
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle(listAuthors.get(pos));
                d.show();
            }
        });
        return v;
    }
}
