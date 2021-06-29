package com.example.trabalho.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.trabalho.presenter.contracts.ModelContract;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Trip implements Parcelable, ModelContract.Model {

    private String uid;
    private String userUid;
    private Date departureDate;
    private Date arrivalDate;
    private Date returnDate;
    private String visitedPlace;
    private String visitedCountry;
    private String visitedCity;
    private String homePlace;
    private String homeCountry;
    private String homeCity;
    private boolean hasReturnDate = false;

    public String getUid() {
        return uid;
    }

    public String getUserUid() {
        return userUid;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public String getVisitedCountry() {
        return visitedCountry;
    }

    public String getVisitedCity() {
        return visitedCity;
    }

    public String getVisitedPlace() {
        if (visitedCity != null && visitedCountry != null) {
            return visitedCity + ", " + visitedCountry.substring(0, 3).toUpperCase();
        }
        return visitedPlace;
    }

    public String getHomeCountry() {
        return homeCountry;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public String getHomePlace() {
        if (homeCity != null && homeCountry != null) {
            return homeCity + ", " + homeCountry.substring(0, 3).toUpperCase();
        }
        return homePlace;
    }

    public boolean getHasReturnDate() {
        return hasReturnDate;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
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

    public void setVisitedCountry(String visitedCountry) {
        this.visitedCountry = visitedCountry;
    }

    public void setVisitedCity(String visitedCity) {
        this.visitedCity = visitedCity;
    }

    public void setVisitedPlace(String visitedPlace) {
        String[] arrayPlace = visitedPlace.split(", ");
        if (arrayPlace.length == 2) {
            this.visitedCountry = arrayPlace[1];
            this.visitedCity = arrayPlace[0];
        }
        this.visitedPlace = visitedPlace;
    }

    public void setHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public void setHomePlace(String homePlace) {
        String[] arrayPlace = homePlace.split(", ");
        if (arrayPlace.length == 2) {
            this.homeCountry = arrayPlace[1];
            this.homeCity = arrayPlace[0];
        }
        this.homePlace = visitedPlace;
    }

    public void setHasReturnDate(boolean hasReturnDate) {
        this.hasReturnDate = hasReturnDate;
    }

    public Trip() {}

    protected Trip(Parcel in) {
        uid = in.readString();
        userUid = in.readString();
        visitedCountry = in.readString();
        visitedCity = in.readString();
        homeCountry = in.readString();
        homeCity = in.readString();
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
        parcel.writeString(visitedCountry);
        parcel.writeString(visitedCity);
        parcel.writeString(homeCountry);
        parcel.writeString(homeCity);
        parcel.writeString(homePlace);
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
        trip.put("visitedCountry", this.visitedCountry);
        trip.put("visitedCity", this.visitedCity);
        trip.put("homeCountry", this.homeCountry);
        trip.put("homeCity", this.homeCity);
        trip.put("userUid", this.userUid);
        return trip;
    }
}
