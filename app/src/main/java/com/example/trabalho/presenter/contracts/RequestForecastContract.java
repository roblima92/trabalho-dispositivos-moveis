package com.example.trabalho.presenter.contracts;

import android.content.Context;
import android.os.Parcelable;

import com.example.trabalho.models.Forecast;

import java.util.Date;
import java.util.List;

public class RequestForecastContract {

    public interface RequestForecastView {
        public void bindList(List<Forecast> forecastArrayList);
    }

    public interface RequestForecastPresenter {
        public void sendErrorForecast(String errorMessage);
        public void getForecast(List<Forecast> forecastArrayList, String type);
        public Forecast findForecast(Date date, String type) throws Exception;
    }

    public interface VolleyCallBack {
        void onSuccess();
    }
}
