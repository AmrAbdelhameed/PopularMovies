package com.example.amr.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

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
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
