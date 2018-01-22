package com.esoxjem.movieguide.listing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.XmlRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.esoxjem.movieguide.ProfileActivity;
import com.esoxjem.movieguide.R;
import com.esoxjem.movieguide.Constants;
import com.esoxjem.movieguide.details.MovieDetailsActivity;
import com.esoxjem.movieguide.details.MovieDetailsFragment;
import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.explore.ExploreActivity;
import com.esoxjem.movieguide.network.TmdbWebService;

public class MoviesListingActivity extends AppCompatActivity implements MoviesListingFragment.Callback,NavigationView.OnNavigationItemSelectedListener
{
    public static final String DETAILS_FRAGMENT = "DetailsFragment";
    private boolean twoPaneMode;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(R.string.movie_guide);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (findViewById(R.id.movie_details_container) != null)
        {
            twoPaneMode = true;

            if (savedInstanceState == null)
            {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_details_container, new MovieDetailsFragment())
                        .commit();
            }
        } else
        {
            twoPaneMode = false;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onMoviesLoaded(Movie movie)
    {
        if(twoPaneMode)
        {
            loadMovieFragment(movie);
        } else
        {
            // Do not load in single pane view
        }

    }

    @Override
    public void onMovieClicked(Movie movie)
    {
        if (twoPaneMode)
        {
            loadMovieFragment(movie);
        } else
        {
            startMovieActivity(movie);
        }
    }

    private void startMovieActivity(Movie movie)
    {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(Constants.MOVIE, movie);
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void loadMovieFragment(Movie movie)
    {
        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.getInstance(movie);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_details_container, movieDetailsFragment, DETAILS_FRAGMENT)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
           Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
           startActivity(profileIntent);
            }
        else if (id == R.id.nav_watched) {


        } else if (id == R.id.nav_friends) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_explore) {
           Intent exploreIntent = new Intent(getApplicationContext(), ExploreActivity.class);
           startActivity(exploreIntent);
        } else if (id == R.id.nav_logOut) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
