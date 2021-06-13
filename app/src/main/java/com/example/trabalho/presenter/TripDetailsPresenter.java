package com.example.trabalho.presenter;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.volley.Response;
import com.example.trabalho.models.Forecast;
import com.example.trabalho.models.LocationGeo;
import com.example.trabalho.models.Trip;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.example.trabalho.presenter.contracts.RequestForecastContract;
import com.example.trabalho.presenter.contracts.RequestLocationContract;
import com.example.trabalho.services.Location;
import com.example.trabalho.services.OpenWeather;

import org.json.JSONObject;

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
    private List<Forecast> forecastReturn = new ArrayList<>();
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

        RequestForecastContract.RequestForecastPresenter forecastPresenter = this;
        OpenWeather openWeatherActual = new OpenWeather(forecastPresenter, this.tripDetailsView.getContext(), "Actual");
        openWeatherActual.startByCoordinatesPromise(locationGeo.getLatitude(), locationGeo.getLongitude(), new RequestForecastContract.VolleyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess() {
                OpenWeather openWeatherDestiny = new OpenWeather(forecastPresenter, tripDetailsView.getContext(), "Destiny");
                openWeatherDestiny.startByCityPromise(trip.getCountry(), trip.getCity(), new RequestForecastContract.VolleyCallBack() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSuccess() {
                        if (trip.getReturnDate() != null) {
                            getForecast(forecastActual, "Return");
                        }
                    }
                });
            }
        });
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
            case "Return":
                forecastAux = this.forecastReturn;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void getForecast(List<Forecast> forecasts, String type) {

        // Check if departure date is the same than arrival date
        Boolean sameDate = trip.getDepartureDate().compareTo(trip.getArrivalDate()) == 0;

        // Format forecasts arrayList
        if (forecasts == null || forecasts.size() == 0) {
            this.sendErrorForecast("Nenhuma previsão de tempo foi retornada.");
            return;
        }

        if (type == "Destiny") {
            this.forecastDestiny = forecasts;
            for (Forecast forecast : forecasts) {
                if (trip.getReturnDate() == null) {
                    if (forecast.getDate().compareTo(trip.getArrivalDate()) >= 0 &&
                            findForecast(forecast.getDate(), "") == null) {
                        forecastArrayList.add(forecast);
                    }
                } else if (forecast.getDate().compareTo(trip.getArrivalDate()) >= 0 &&
                        forecast.getDate().compareTo(trip.getReturnDate()) < 0 &&
                        findForecast(forecast.getDate(), "") == null) {
                    forecastArrayList.add(forecast);
                }
            }
        } else if (type == "Actual") {
            this.forecastActual = forecasts;
            for (Forecast forecast : forecasts) {
                if (sameDate) {
                    if (forecast.getDate().compareTo(trip.getDepartureDate()) < 0 &&
                            findForecast(forecast.getDate(), "") == null) {
                        this.forecastArrayList.add(forecast);
                    }
                } else {
                    if (forecast.getDate().compareTo(trip.getDepartureDate()) <= 0 &&
                            findForecast(forecast.getDate(), "") == null) {
                        this.forecastArrayList.add(forecast);
                    }
                }

            }
        } else if (type == "Return") {
            this.forecastReturn = forecasts;
            for (Forecast forecast : forecasts) {
                if (forecast.getDate().compareTo(trip.getReturnDate()) >= 0 &&
                        findForecast(forecast.getDate(), "") == null) {
                    this.forecastArrayList.add(forecast);
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
