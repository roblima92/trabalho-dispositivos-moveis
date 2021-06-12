package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CardView weather = (CardView) findViewById(R.id.forecast_card);
        CardView profile = (CardView) findViewById(R.id.my_profile_card);
        CardView trip = (CardView) findViewById(R.id.new_trip_card);
        CardView old_trips = (CardView) findViewById(R.id.my_trips_card);

        weather.setOnClickListener(v -> openWeatherActivity(v));
        profile.setOnClickListener(v -> openProfileActivity(v));
        trip.setOnClickListener(v -> openTripActivity(v));
        old_trips.setOnClickListener(v -> openOldTripsActivity(v));
    }

    public void openWeatherActivity(View view) {
        Intent intent = new Intent(this,WeatherActivity.class);
        startActivity(intent);
    }

    public void openProfileActivity(View view) {
        Intent intent = new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }

    public void openTripActivity(View view) {
        Intent intent = new Intent(this,TripActivity.class);
        startActivity(intent);
    }

    public void openOldTripsActivity(View view) {
        Intent intent = new Intent(this,OldTripsActivity.class);
        startActivity(intent);
    }
}