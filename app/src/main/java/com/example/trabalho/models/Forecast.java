package com.example.trabalho.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.example.trabalho.presenter.contracts.ModelContract;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Map;

public class Forecast implements Parcelable {
    private Date date;
    private int max;
    private int min;
    private int average;
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

    public int getAverage() {
        return average;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public Weather getWeather() { return weather; }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public String getFormattedDate() {
        SimpleDateFormat brazilianFormat = new SimpleDateFormat("dd/MM/yyyy");
        return brazilianFormat.format(this.date);
    }

//    @BindingAdapter({"imageUrl", "picasso"})
//    public static void setImageUrl(ImageView view, String poserPath, Picasso picasso){
//        picasso.get().load("http://openweathermap.org/img/w/"+ poserPath + ".png").into(view);
//    }

    public Forecast() {
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
