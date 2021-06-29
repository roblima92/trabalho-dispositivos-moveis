package com.example.trabalho.services;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.example.trabalho.models.LocationGeo;
import com.example.trabalho.presenter.contracts.RequestLocationContract;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;

public class Location {

    private FusedLocationProviderClient fusedLocationClient;
    private Activity activity;
    private Context context;
    private RequestLocationContract.RequestLocationPresenter presenter;

    public Location(Activity activity, RequestLocationContract.RequestLocationPresenter presenter) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.presenter = presenter;
    }

    public void getLastLocation() {
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.context);
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this.activity,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    1
            );
        }

        this.fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this.activity, new OnSuccessListener<android.location.Location>() {
                    @Override
                    public void onSuccess(android.location.Location location) {
                        if (location != null) {
                            LocationGeo locationGeo = new LocationGeo(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    location.getAltitude(),
                                    location.getSpeed(),
                                    location.getBearing(),
                                    location.getAccuracy()
                            );

                            try {
                                presenter.getLocation(locationGeo);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

}
