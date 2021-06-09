package com.example.trabalho.presenter;

import com.example.trabalho.models.Forecast;
import com.example.trabalho.models.LocationGeo;
import com.example.trabalho.models.Trip;
import com.example.trabalho.services.OpenWeather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripDetailsPresenter implements RequestForecastContract.RequestForecastPresenter {

    private RequestForecastContract.RequestForecastView tripDetailsView;
    private Trip trip;
    private List<Forecast> forecastArrayList = new ArrayList<>();
    private LocationGeo locationGeo;

    public TripDetailsPresenter(RequestForecastContract.RequestForecastView view, Trip trip, LocationGeo locationGeo) {
        this.tripDetailsView = view;
        this.trip = trip;
        this.locationGeo = locationGeo;
        this.start();
    }

    @Override
    public void start() {
        OpenWeather openWeatherActual = new OpenWeather(this, this.tripDetailsView.getContext(), "Actual");
        openWeatherActual.startByCoordinates(locationGeo.getLatitude(), locationGeo.getLongitude());

        OpenWeather openWeatherDestiny = new OpenWeather(this, this.tripDetailsView.getContext(), "Destiny");
        openWeatherDestiny.startByCity(trip.getCountry(), trip.getCity());
    }

    @Override
    public Forecast findForecast(Date date) {
        if (forecastArrayList == null || forecastArrayList.size() == 0) {
            return null;
        }
        for (Forecast forecast : forecastArrayList) {
            if (forecast.getDate().compareTo(date) == 0) {
                return forecast;
            }
        }
        return null;
    }

    @Override
    public void getForecast(List<Forecast> forecastArrayList, String type) {

        // Format forecasts arrayList
        if (type == "Destiny") {
            for (int i = 0; i < forecastArrayList.size(); i++) {
                if (forecastArrayList.get(i).getDate().compareTo(trip.getArrivalDate()) >= 0 && findForecast(forecastArrayList.get(i).getDate()) == null) {
                    this.forecastArrayList.add(i, forecastArrayList.get(i));
                }
            }
        } else if (type == "Actual") {
            for (int i = 0; i < forecastArrayList.size(); i++) {
                if (forecastArrayList.get(i).getDate().compareTo(trip.getDepartureDate()) <= 0 && findForecast(forecastArrayList.get(i).getDate()) == null) {
                    this.forecastArrayList.add(i, forecastArrayList.get(i));
                }
            }
        }

        this.tripDetailsView.bindList(this.forecastArrayList);
    }

    @Override
    public void sendError(String errorMessage) {
        this.tripDetailsView.showToast("Ocorreu um erro ao tentar buscar a temperatura da cidade informada");
    }

}
