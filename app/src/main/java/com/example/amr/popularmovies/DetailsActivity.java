package com.example.amr.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Receive the sent Bundle
        Intent sentIntent = getIntent();
        Bundle sentBundle = sentIntent.getExtras();
        //Inflate Details Fragment & Send the Bundle to it
        DetailsFragment mDetailsFragment = new DetailsFragment();
        mDetailsFragment.setArguments(sentBundle);
        getSupportFragmentManager().beginTransaction().add(R.id.flDetails, mDetailsFragment, "").commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
