package com.example.trabalho.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.trabalho.presenter.contracts.ModelContract;

import java.util.Date;

public class Trip implements Parcelable, ModelContract.Model {
    private Date departureDate;
    private Date arrivalDate;
    private Date returnDate;
    private String country;
    private String city;

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Trip() {}

    protected Trip(Parcel in) {
        country = in.readString();
        city = in.readString();
        departureDate = (java.util.Date) in.readSerializable();
        arrivalDate = (java.util.Date) in.readSerializable();
        returnDate = (java.util.Date) in.readSerializable();
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
        parcel.writeSerializable(returnDate);
    }
}
