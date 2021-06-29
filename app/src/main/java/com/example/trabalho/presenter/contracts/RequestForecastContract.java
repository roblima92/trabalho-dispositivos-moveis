package com.example.trabalho.presenter.contracts;

import android.content.Context;
import android.os.Parcelable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalho.models.Forecast;
import com.example.trabalho.models.Hourly;

import java.util.Date;
import java.util.List;

public class RequestForecastContract {

    public interface RequestForecastView {
        public void bindList(List<Forecast> forecastArrayList, RecyclerView recyclerView);
    }

    public interface RequestHourlyView {
        public void bindHourlyList(List<Hourly> hourlyArrayList, RecyclerView recyclerView);
    }

    public interface RequestForecastPresenter {
        public void sendErrorForecast(String errorMessage);
        public void getForecast(List<Forecast> forecastArrayList, String type);
    }

    public interface RequestHourlyPresenter {
        public void sendErrorHourly(String errorMessage);
        public void getHourly(List<Hourly> hourlyArrayList);
    }

    public interface VolleyCallBack {
        void onSuccess();
    }
}
