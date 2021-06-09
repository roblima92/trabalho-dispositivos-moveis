package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.example.trabalho.adapters.ForecastAdapter;
import com.example.trabalho.databinding.ActivityTripDetailsBinding;
import com.example.trabalho.models.Forecast;
import com.example.trabalho.models.LocationGeo;
import com.example.trabalho.models.Trip;
import com.example.trabalho.presenter.RequestForecastContract;
import com.example.trabalho.presenter.TripDetailsPresenter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class TripDetailsActivity extends AppCompatActivity implements RequestForecastContract.RequestForecastView {

    private ArrayList<Forecast> forecastArrayList = new ArrayList<>();
    private RequestForecastContract.RequestForecastPresenter tripDetailsPresenter;
    private ActivityTripDetailsBinding tripDetailsBinding;
    private Trip trip;
    private LocationGeo locationGeo;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.tripDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_trip_details);

        Intent intent = getIntent();
        this.trip = intent.getParcelableExtra("objTrip");
        this.tripDetailsBinding.setTrip(trip);

        // Isso irá para a presenter depois
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    1
            );
        }
        RequestForecastContract.RequestForecastView t = this;
        this.fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            locationGeo = new LocationGeo(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    location.getAltitude(),
                                    location.getSpeed(),
                                    location.getBearing(),
                                    location.getAccuracy()
                            );

                            tripDetailsPresenter = new TripDetailsPresenter(t, trip, locationGeo);
                        }
                    }
                });
    }

    @Override
    public void bindList(List<Forecast> forecastArrayList) {
        try {
            RecyclerView recyclerView = findViewById(R.id.recyclerViewForecast);
            LinearLayoutManager linearLayoutManagerHorinzontal = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(linearLayoutManagerHorinzontal);

            ForecastAdapter forecastAdapter = new ForecastAdapter(forecastArrayList);
            recyclerView.setAdapter(forecastAdapter);

            Forecast forecastDeparture = this.tripDetailsPresenter.findForecast(this.trip.getDepartureDate());
            Forecast forecastArrival = this.tripDetailsPresenter.findForecast(this.trip.getArrivalDate());

            this.tripDetailsBinding.setForecastArrival(forecastArrival);
            this.tripDetailsBinding.setForecastDeparture(forecastDeparture);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }

}