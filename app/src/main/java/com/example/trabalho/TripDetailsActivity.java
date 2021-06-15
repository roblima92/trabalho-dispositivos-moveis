package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.trabalho.adapters.ForecastAdapter;
import com.example.trabalho.databinding.ActivityTripDetailsBinding;
import com.example.trabalho.models.Forecast;
import com.example.trabalho.models.Trip;
import com.example.trabalho.models.User;
import com.example.trabalho.presenter.HomePresenter;
import com.example.trabalho.presenter.TripPresenter;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.example.trabalho.presenter.contracts.RequestForecastContract;
import com.example.trabalho.presenter.TripDetailsPresenter;

import java.util.List;

public class TripDetailsActivity extends AppCompatActivity implements ActivityContract.ActivityView, RequestForecastContract.RequestForecastView {

    private TripDetailsPresenter tripDetailsPresenter;
    private ActivityTripDetailsBinding tripDetailsBinding;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        Intent intent = getIntent();
        this.trip = new Trip();
        this.trip.setUid(intent.getExtras().getString("uId"));

        tripDetailsPresenter = new TripDetailsPresenter(this, trip);
    }

    public void bindTrip(Trip trip, List<Forecast> forecastsHome, List<Forecast> forecastsDestine) {

        tripDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_trip_details);
        tripDetailsBinding.setPresenter(tripDetailsPresenter);
        tripDetailsBinding.setTrip(trip);
        bindList(forecastsHome, tripDetailsBinding.tripRvDeparture);
        bindList(forecastsDestine, tripDetailsBinding.tripRvArrival);
    }

    @Override
    public void bindList(List<Forecast> forecastArrayList, RecyclerView recyclerViewForecast) {
        try {
            RecyclerView recyclerView = recyclerViewForecast;
            LinearLayoutManager linearLayoutManagerHorinzontal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(linearLayoutManagerHorinzontal);

            ForecastAdapter forecastAdapter = new ForecastAdapter(forecastArrayList);
            recyclerView.setAdapter(forecastAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void navigate(Intent intent) {
        startActivity(intent);
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