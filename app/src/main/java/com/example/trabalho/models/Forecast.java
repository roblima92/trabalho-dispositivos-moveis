package com.example.trabalho.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Forecast implements Parcelable {
    private Date date;
    private int max;
    private int min;
    private int pressure;
    private int humidity;
    private Weather weather;

    public Date getDate() {
        return date;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public Weather getWeather() { return weather; }

    public String getFormattedDate() {
        SimpleDateFormat brazilianFormat = new SimpleDateFormat("dd/MM/yyyy");
        return brazilianFormat.format(this.date);
    }

    public Forecast(Date date, int max, int min, int pressure, int humidity, Weather weather) {
        this.date = date;
        this.max = max;
        this.min = min;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weather = weather;
    }

    protected Forecast(Parcel in) {
        date = (Date) in.readSerializable();
        max = in.readInt();
        min = in.readInt();
        humidity = in.readInt();
        pressure = in.readInt();
        weather = in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<Forecast> CREATOR = new Creator<Forecast>() {
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
        parcel.writeSerializable(date);
        parcel.writeInt(max);
        parcel.writeInt(min);
        parcel.writeInt(pressure);
        parcel.writeInt(humidity);
        weather.writeToParcel(parcel, i);
    }
}
