package com.example.trabalho.presenter;

import com.example.trabalho.ForecastActivity;
import com.example.trabalho.NewTripActivity;
import com.example.trabalho.databinding.ActivityForecastBinding;
import com.example.trabalho.databinding.ActivityNewTripBinding;
import com.example.trabalho.models.Address;
import com.example.trabalho.models.Forecast;
import com.example.trabalho.models.Hourly;
import com.example.trabalho.models.LocationGeo;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.example.trabalho.presenter.contracts.RequestForecastContract;
import com.example.trabalho.presenter.contracts.RequestLocationContract;
import com.example.trabalho.services.Location;
import com.example.trabalho.services.OpenWeather;
import com.example.trabalho.services.OpenWeatherHourly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ForecastPresenter implements ActivityContract.ActivityPresenter,
        RequestLocationContract.RequestLocationPresenter,
        RequestForecastContract.RequestForecastPresenter,
        RequestForecastContract.RequestHourlyPresenter {

    private ActivityContract.ActivityView forecastView;
    private LocationGeo locationGeo;
    public ActivityForecastBinding forecastBinding;

    public ForecastPresenter(ActivityContract.ActivityView view) {
        forecastView = view;
        start();
    }

    @Override
    public void start() {
        Location location = new Location((ForecastActivity) forecastView, this);
        location.getLastLocation();
    }

    @Override
    public void sendErrorForecast(String errorMessage) {
        this.forecastView.showToast("Não foi buscar os dados da previsão do tempo.");
    }

    @Override
    public void sendErrorHourly(String errorMessage) {
        this.forecastView.showToast("Não foi buscar os dados da previsão do tempo.");
    }

    @Override
    public void getHourly(List<Hourly> hourlyArrayList) {
        ((RequestForecastContract.RequestHourlyView) forecastView).bindHourlyList(hourlyArrayList, forecastBinding.forecastRvToday);
    }

    @Override
    public void getForecast(List<Forecast> forecastArrayList, String type) {
        ((ForecastActivity) forecastView).bindForecast(forecastArrayList.get(0));
//        ((RequestForecastContract.RequestForecastView) forecastView).bindList(forecastArrayList, forecastBinding.forecastRvNextDays);
    }

    @Override
    public void getLocation(LocationGeo locationGeo) throws IOException {
        this.locationGeo = locationGeo;
        Address address = new Address();
        address.transformCoordenatesToAddress(this.locationGeo.getLatitude(), this.locationGeo.getLongitude(), this.forecastView.getContext());
        this.requestWeather(address.getCity(), address.getCountry());
    }

    public void requestWeather(String city, String country) {
        OpenWeather openWeatherHome = new OpenWeather(this, forecastView.getContext(), "home");
        openWeatherHome.startByCity(country, city);

        OpenWeatherHourly openWeatherHourlyHome = new OpenWeatherHourly(this, forecastView.getContext());
        openWeatherHourlyHome.start(country, city);
    }
}
