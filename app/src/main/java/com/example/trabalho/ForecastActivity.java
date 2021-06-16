package com.example.trabalho;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalho.adapters.ForecastAdapter;
import com.example.trabalho.adapters.HourlyAdapter;
import com.example.trabalho.databinding.ActivityForecastBinding;
import com.example.trabalho.models.Forecast;
import com.example.trabalho.models.Hourly;
import com.example.trabalho.presenter.ForecastPresenter;
import com.example.trabalho.presenter.TripDetailsPresenter;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.example.trabalho.presenter.contracts.RequestForecastContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ForecastActivity extends AppCompatActivity implements ActivityContract.ActivityView,
        RequestForecastContract.RequestForecastView,
        RequestForecastContract.RequestHourlyView {

    private ForecastPresenter forecastPresenter;
    private ActivityForecastBinding forecastBinding;
    private Forecast forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        forecastPresenter = new ForecastPresenter(this);
    }

    public void bindForecast(Forecast forecast, List<Hourly> hourlyArrayList, List<Forecast> forecastArrayList, String place) {
        forecastBinding = DataBindingUtil.setContentView(this, R.layout.activity_forecast);
        forecastBinding.setPresenter(forecastPresenter);
        forecastBinding.setForecast(forecast);
        forecastBinding.setActualTemperature(((int) hourlyArrayList.get(0).getTemperature()) + "°");
        forecastBinding.setPlace(place);
        Picasso.get().load("http://openweathermap.org/img/w/"+forecast.getWeather().getIcon() + ".png").resize(350,350).into(forecastBinding.forecastWeather);
        this.bindList(forecastArrayList, forecastBinding.forecastRvNextDays);
        this.bindHourlyList(hourlyArrayList, forecastBinding.forecastRvToday);
    }

    @Override
    public void bindHourlyList(List<Hourly> hourlyArrayList, RecyclerView recyclerViewHourly) {
        try {
            RecyclerView recyclerView = recyclerViewHourly;
            LinearLayoutManager linearLayoutManagerHorinzontal = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(linearLayoutManagerHorinzontal);

            HourlyAdapter hourlyAdapter = new HourlyAdapter(hourlyArrayList);
            recyclerView.setAdapter(hourlyAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public Context getContext() {
        return this.getApplicationContext();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigate(Intent intent) {
        startActivity(intent);
    }

}