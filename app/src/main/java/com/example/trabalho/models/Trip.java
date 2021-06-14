package com.example.trabalho.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.trabalho.presenter.contracts.ModelContract;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Trip implements Parcelable, ModelContract.Model {
    private Date departureDate;
    private Date arrivalDate;
    private Date returnDate;
    private boolean hasReturnDate = false;
    private String place;
    private String country;
    private String city;
    private String userUid;

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

    public String getUserUid() {
        return userUid;
    }

    public String getPlace() {
        if (city != null && country != null) {
            return city + ", " + country.substring(0, 3).toUpperCase();
        }
        return place;
    }

    public boolean getHasReturnDate() {
        return hasReturnDate;
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

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public void setHasReturnDate(boolean hasReturnDate) {
        this.hasReturnDate = hasReturnDate;
    }

    public void setPlace(String place) {
        String[] arrayPlace = place.split(", ");
        if (arrayPlace.length == 2) {
            this.country = arrayPlace[1];
            this.city = arrayPlace[0];
        }
        this.place = place;
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

    @Override
    public Map<String, Object> getInstanceinMap() {
        Map<String, Object> trip = new HashMap<>();
        trip.put("departureDate", this.departureDate);
        trip.put("arrivalDate", this.arrivalDate);
        trip.put("returnDate", this.returnDate);
        trip.put("place", this.place);
        trip.put("country", this.country);
        trip.put("city", this.city);
        trip.put("userUid", this.userUid);
        return trip;
    }
}
