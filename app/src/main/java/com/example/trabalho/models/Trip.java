package com.example.trabalho.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Trip implements Parcelable {
    private Date departureDate;
    private Date arrivalDate;
    private String country;
    private String city;

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public Trip(Date departureDate, Date arrivalDate, String country, String city) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.country = country;
        this.city = city;
    }

    protected Trip(Parcel in) {
        country = in.readString();
        city = in.readString();
        departureDate = (java.util.Date) in.readSerializable();
        arrivalDate = (java.util.Date) in.readSerializable();
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(country);
        parcel.writeString(city);
        parcel.writeSerializable(departureDate);
        parcel.writeSerializable(arrivalDate);
    }
}
