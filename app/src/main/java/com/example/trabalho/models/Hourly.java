package com.example.trabalho.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Hourly implements Parcelable {
    private String hour;
    private double temperature;
    private int humidity;
    private Weather weather;

    public String getHour() {
        return hour;
    }

    public String getHourWithoutSeconds() {
        if (hour == null) return hour;
        String[] hourArray = hour.split(":");
        return hourArray[0] + ":" + hourArray[1];
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Hourly() {
    }

    protected Hourly(Parcel in) {
        hour = in.readString();
        temperature = in.readDouble();
        humidity = in.readInt();
    }

    public static final Parcelable.Creator<Forecast> CREATOR = new Parcelable.Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel in) {
            return new Forecast(in);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hour);
        parcel.writeDouble(temperature);
        parcel.writeInt(humidity);
    }
}
