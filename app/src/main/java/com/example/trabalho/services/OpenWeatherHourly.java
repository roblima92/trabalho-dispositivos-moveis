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
import com.example.trabalho.models.Hourly;
import com.example.trabalho.models.Weather;
import com.example.trabalho.presenter.contracts.RequestForecastContract;
import com.example.trabalho.utils.Converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OpenWeatherHourly implements Response.Listener<JSONObject>,
                                    Response.ErrorListener {

    private List<Hourly> hourlyArrayList = new ArrayList<>();
    private RequestForecastContract.RequestHourlyPresenter hourlyPresenter;
    private Context context;

    public OpenWeatherHourly(RequestForecastContract.RequestHourlyPresenter hourlyPresenter, Context context) {
        this.hourlyPresenter = hourlyPresenter;
        this.context = context;
    }

    public void start(String city, String country) {
        String url = "https://pro.openweathermap.org/data/2.5/forecast/hourly?q=" + city + "," + country + "&appid=f2713eeac08f8eed423cfcb9d47dee25&lang=pt_br&units=metric";
        RequestQueue queue = Volley.newRequestQueue(this.context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray hourly = response.getJSONArray("list");

            for (int i = 0; i < hourly.length(); i++) {
                JSONObject json = hourly.getJSONObject(i);
                Weather weather = new Weather();
                weather.setId(json.getJSONArray("weather").getJSONObject(0).getInt("id"));
                weather.setMain(json.getJSONArray("weather").getJSONObject(0).getString("main"));
                weather.setDescription(json.getJSONArray("weather").getJSONObject(0).getString("description"));
                weather.setIcon(json.getJSONArray("weather").getJSONObject(0).getString("icon"));

                Hourly obj = new Hourly();
                String[] arrayDate = json.getString("dt_txt").split(" ");
                String[] brazilianFormatArray = arrayDate[0].split("-");
                String brazilianFormatString = brazilianFormatArray[2] + "/" + brazilianFormatArray[1] + "/" + brazilianFormatArray[0];

                if (brazilianFormatString.equals(Converter.dateToString(new Date()))) {
                    obj.setHour(arrayDate[1]);
                    obj.setHumidity(json.getJSONObject("main").getInt("humidity"));
                    obj.setTemperature((int) Math.round(json.getJSONObject("main").getDouble("temp")));
                    obj.setWeather(weather);
                    this.hourlyArrayList.add(obj);
                }
            }

            this.hourlyPresenter.getHourly(this.hourlyArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        this.hourlyPresenter.sendErrorHourly("Ocorreu um erro ao tentar buscar a temperatura por horário da cidade informada");
    }
}
