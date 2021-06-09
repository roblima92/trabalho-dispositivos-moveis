package com.example.trabalho.models;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationGeo implements Parcelable {

    private double latitude;
    private double longitude;
    private double altitude;
    private float speed;
    private float bearing;
    private float accuracy;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LocationGeo(double latitude, double longitude, double altitude, float speed, float bearing, float accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
        this.bearing = bearing;
        this.accuracy = accuracy;
    }

    protected LocationGeo(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        altitude = in.readDouble();
        speed = in.readFloat();
        bearing = in.readFloat();
        accuracy = in.readFloat();
    }

    public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
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
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeDouble(altitude);
        parcel.writeFloat(speed);
        parcel.writeFloat(bearing);
        parcel.writeFloat(accuracy);
    }
}
