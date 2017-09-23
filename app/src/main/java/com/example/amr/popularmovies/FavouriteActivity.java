package com.example.amr.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class FavouriteActivity extends AppCompatActivity implements TabletMoodFavourite {
    boolean mIsTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Favourite Movies");

        FavouriteFragment favouriteFragment = new FavouriteFragment();
        favouriteFragment.setNameListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.flMain2, favouriteFragment, "").commit();
        if (null != findViewById(R.id.flDetails2)) {
            mIsTwoPane = true;
        }
    }

    @Override
    public void setSelectedName(int ID, String Title, String Year, Double Rate, String Overview, String Image1, String Image2) {

        if (!mIsTwoPane) {
            Intent i = new Intent(this, DetailsActivity.class);

            Bundle b = new Bundle();
            b.putBoolean("Favourite", true);
            b.putInt("ID", ID);
            b.putBoolean("Toolbar", true);
            b.putString("Title", Title);
            b.putString("Year", Year);
            b.putDouble("Rate", Rate);
            b.putString("Overview", Overview);
            b.putString("Image1", Image1);
            b.putString("Image2", Image2);
            i.putExtras(b);

            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else {
            DetailsFragment mDetailsFragment = new DetailsFragment();

            Bundle b = new Bundle();
            b.putBoolean("Favourite", true);
            b.putInt("ID", ID);
            b.putBoolean("Toolbar", false);
            b.putString("Title", Title);
            b.putString("Year", Year);
            b.putDouble("Rate", Rate);
            b.putString("Overview", Overview);
            b.putString("Image1", Image1);
            b.putString("Image2", Image2);
            mDetailsFragment.setArguments(b);

            getSupportFragmentManager().beginTransaction().replace(R.id.flDetails2, mDetailsFragment, "").commit();
        }
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
