package com.example.trabalho.models;

import android.content.Context;
import android.location.Geocoder;

import com.example.trabalho.presenter.contracts.ModelContract;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Address {

    private String city;
    private String state;
    private String country;

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public void transformCoordenatesToAddress(double latitude, double longitude, Context context) throws IOException {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<android.location.Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        country = addresses.get(0).getCountryName();
        city = addresses.get(0).getSubAdminArea();
    }
}
