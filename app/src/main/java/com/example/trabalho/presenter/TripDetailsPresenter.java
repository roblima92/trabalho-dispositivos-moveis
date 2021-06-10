package com.example.trabalho.presenter;

import android.app.Activity;

import com.example.trabalho.models.Forecast;
import com.example.trabalho.models.LocationGeo;
import com.example.trabalho.models.Trip;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.example.trabalho.presenter.contracts.RequestForecastContract;
import com.example.trabalho.presenter.contracts.RequestLocationContract;
import com.example.trabalho.services.Location;
import com.example.trabalho.services.OpenWeather;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripDetailsPresenter implements RequestForecastContract.RequestForecastPresenter,
        ActivityContract.ActivityPresenter, RequestLocationContract.RequestLocationPresenter {

    private RequestForecastContract.RequestForecastView tripDetailsForecastView;
    private ActivityContract.ActivityView tripDetailsView;
    private Trip trip;
    private List<Forecast> forecastArrayList = new ArrayList<>();
    private List<Forecast> forecastActual = new ArrayList<>();
    private List<Forecast> forecastDestiny = new ArrayList<>();
    private LocationGeo locationGeo;

    public TripDetailsPresenter(RequestForecastContract.RequestForecastView viewForecast,
                                ActivityContract.ActivityView view,
                                Activity activity,
                                Trip trip) {
        this.tripDetailsView = view;
        this.tripDetailsForecastView = viewForecast;
        this.trip = trip;

        Location location = new Location(activity, this);
        location.getLastLocation();
    }

    @Override
    public void start() {
        OpenWeather openWeatherActual = new OpenWeather(this, this.tripDetailsView.getContext(), "Actual");
        openWeatherActual.startByCoordinates(locationGeo.getLatitude(), locationGeo.getLongitude());

        OpenWeather openWeatherDestiny = new OpenWeather(this, this.tripDetailsView.getContext(), "Destiny");
        openWeatherDestiny.startByCity(trip.getCountry(), trip.getCity());
    }

    @Override
    public Forecast findForecast(Date date, String type) {
        if (forecastArrayList == null || forecastArrayList.size() == 0) {
            return null;
        }

        List<Forecast> forecastAux = new ArrayList<>();
        switch (type) {
            case "":
                forecastAux = this.forecastArrayList;
                break;
            case "Actual":
                forecastAux = this.forecastActual;
                break;
            case "Destiny":
                forecastAux = this.forecastDestiny;
                break;
        }

        for (Forecast forecast : forecastAux) {
            if (forecast.getDate().compareTo(date) == 0) {
                return forecast;
            }
        }
        return null;
    }

    @Override
    public void getLocation(LocationGeo locationGeo) {
        this.locationGeo = locationGeo;
        this.start();
    }

    @Override
    public void getForecast(List<Forecast> forecasts, String type) {

        // Format forecasts arrayList
        if (forecasts == null || forecasts.size() == 0) {
            this.sendErrorForecast("Nenhuma previsão de tempo foi retornada.");
            return;
        }

        if (type == "Destiny") {
            this.forecastDestiny = forecasts;
            for (Forecast forecast : forecasts) {
                if (forecast.getDate().compareTo(trip.getArrivalDate()) >= 0 && findForecast(forecast.getDate(), "") == null) {
                    this.forecastArrayList.add(forecast);
                }
            }
        } else if (type == "Actual") {
            // insert forecasts from actual place in the first positions
            int contActual = 0;
            this.forecastActual = forecasts;
            for (Forecast forecast : forecasts) {
                if (forecast.getDate().compareTo(trip.getDepartureDate()) < 0 && findForecast(forecast.getDate(), "") == null) {
                    this.forecastArrayList.add(contActual, forecast);
                    contActual++;
                }
            }
        }

        this.tripDetailsForecastView.bindList(this.forecastArrayList);
    }

    @Override
    public void sendErrorForecast(String errorMessage) {
        this.tripDetailsView.showToast(errorMessage);
    }

}
