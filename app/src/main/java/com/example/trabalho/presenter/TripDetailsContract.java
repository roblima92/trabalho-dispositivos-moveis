package com.example.trabalho.presenter;

import android.content.Context;
import android.os.Parcelable;

import com.example.trabalho.models.Forecast;

import java.util.Date;
import java.util.List;

public class TripDetailsContract {

    public interface TripDetailsView {
        public void bindList(List<Forecast> forecastArrayList);
        public void showToast(String message);
        public Context getContext();
    }

    public interface TripDetailsInterfacePresenter {
        public void start();
        public Forecast findForecast(Date date) throws Exception;
    }
}
