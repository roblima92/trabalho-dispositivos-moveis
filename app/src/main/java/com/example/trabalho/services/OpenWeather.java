package com.example.trabalho.services;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabalho.models.Forecast;
import com.example.trabalho.models.Weather;
import com.example.trabalho.presenter.contracts.RequestForecastContract;

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

public class OpenWeather implements Response.Listener<JSONObject>,
                                    Response.ErrorListener {

    private final double CONSTANT_KELVIN = 273.15;
    private List<Forecast> forecastArrayList = new ArrayList<>();
    private RequestForecastContract.RequestForecastPresenter forecastPresenter;
    private Context context;
    private String type;

    public OpenWeather(RequestForecastContract.RequestForecastPresenter forecastPresenter, Context context, String type) {
        this.forecastPresenter = forecastPresenter;
        this.context = context;
        this.type = type;
    }

    private void start(String url) {
        RequestQueue queue = Volley.newRequestQueue(this.context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    public void startByCity(String country, String city) {
        String url = "https://api.openweathermap.org/data/2.5/forecast/daily?q=" + city + "," + country + "&cnt=16&appid=8118ed6ee68db2debfaaa5a44c832918&lang=pt_br";
        this.start(url);
    }

    public void startByCoordinates(double lat, double lon) {
        String url = "https://api.openweathermap.org/data/2.5/forecast/daily?lat="+lat+"&lon="+lon+"&cnt=16&appid=8118ed6ee68db2debfaaa5a44c832918&lang=pt_br";
        this.start(url);
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
                BigDecimal maxForecast = new BigDecimal(json.getJSONObject("temp").getDouble("max") - CONSTANT_KELVIN);
                BigDecimal minForecast = new BigDecimal(json.getJSONObject("temp").getDouble("min") - CONSTANT_KELVIN);

                Forecast obj = new Forecast(
                        brazilianFormat.parse(brazilianFormat.format(Date.from(instant))),
                        (int) Math.round(maxForecast.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()),
                        (int) Math.round(minForecast.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()),
                        json.getInt("pressure"),
                        json.getInt("humidity"),
                        weather);

                this.forecastArrayList.add(obj);
            }

            this.forecastPresenter.getForecast(this.forecastArrayList, this.type);
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        this.forecastPresenter.sendErrorForecast("Ocorreu um erro ao tentar buscar a temperatura da cidade informada");
    }
}
