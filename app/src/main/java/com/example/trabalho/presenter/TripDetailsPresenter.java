package com.example.trabalho.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabalho.models.Forecast;
import com.example.trabalho.models.Trip;
import com.example.trabalho.models.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripDetailsPresenter implements Response.Listener<JSONObject>,
        Response.ErrorListener, TripDetailsContract.TripDetailsInterfacePresenter {

    private TripDetailsContract.TripDetailsView tripDetailsView;
    private Trip trip;
    private List<Forecast> forecastArrayList = new ArrayList<>();
    private final double constantKelvin = 273.15;

    public TripDetailsPresenter(TripDetailsContract.TripDetailsView view, Trip trip) {
        this.tripDetailsView = view;
        this.trip = trip;
        this.start();
    }

    @Override
    public void start() {
        RequestQueue queue = Volley.newRequestQueue(tripDetailsView.getContext());
        String url = "https://api.openweathermap.org/data/2.5/forecast/daily?q=" + this.trip.getCity() + "," + this.trip.getCountry() + "&cnt=16&appid=8118ed6ee68db2debfaaa5a44c832918";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public Forecast findForecast(Date date) {
        for (Forecast forecast : forecastArrayList) {
            if (forecast.getDate().compareTo(date) == 0) {
                return forecast;
            }
        }
        this.tripDetailsView.showToast("Ops! A data informada não foi encontrada na nossa previsão de tempo.");
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResponse(JSONObject response) {
        try {

            JSONArray forecasts = response.getJSONArray("list");

            SimpleDateFormat brazilianFormat = new SimpleDateFormat("dd/MM/yyyy");

            for (int i = 0; i < forecasts.length(); i++) {
                JSONObject json = forecasts.getJSONObject(i);
                Weather weather = new Weather(
                        json.getJSONArray("weather").getJSONObject(0).getInt("id"),
                        json.getJSONArray("weather").getJSONObject(0).getString("main"),
                        json.getJSONArray("weather").getJSONObject(0).getString("description"),
                        json.getJSONArray("weather").getJSONObject(0).getString("icon")
                );

                Instant instant = Instant.ofEpochSecond(Long.parseLong(json.getString("dt")));
                BigDecimal maxForecast = new BigDecimal(json.getJSONObject("temp").getDouble("max") - constantKelvin);
                BigDecimal minForecast = new BigDecimal(json.getJSONObject("temp").getDouble("min") - constantKelvin);

                Forecast obj = new Forecast(
                        brazilianFormat.parse(brazilianFormat.format(Date.from(instant))),
                        maxForecast.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(),
                        minForecast.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(),
                        json.getInt("pressure"),
                        json.getInt("humidity"),
                        weather);

                this.forecastArrayList.add(obj);
            }

            tripDetailsView.bindList(this.forecastArrayList);

        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        tripDetailsView.showToast("Ocorreu um erro ao tentar buscar a temperatura da cidade informada");
    }
}
