package com.example.trabalho.presenter;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.trabalho.HomeActivity;
import com.example.trabalho.TripActivity;
import com.example.trabalho.TripDetailsActivity;
import com.example.trabalho.models.Forecast;
import com.example.trabalho.models.ForecastDocument;
import com.example.trabalho.models.LocationGeo;
import com.example.trabalho.models.Trip;
import com.example.trabalho.models.User;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.example.trabalho.presenter.contracts.RequestFirestoreContract;
import com.example.trabalho.presenter.contracts.RequestForecastContract;
import com.example.trabalho.presenter.contracts.RequestLocationContract;
import com.example.trabalho.services.Location;
import com.example.trabalho.services.OpenWeather;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripDetailsPresenter implements ActivityContract.ActivityPresenter {

    private ActivityContract.ActivityView tripDetailsView;
    private Trip trip;
    private List<Forecast> forecastActual = new ArrayList<>();
    private List<Forecast> forecastDestiny = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public TripDetailsPresenter(ActivityContract.ActivityView view,
                                Trip trip) {
        this.tripDetailsView = view;
        this.trip = trip;
        this.start();
    }

    @Override
    public void start() {

        DocumentReference userModel = db.collection("trips").document(trip.getUid());
        userModel.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Trip trip = new Trip();
                        Timestamp departureDateTimestamp = (Timestamp) document.get("departureDate");
                        Timestamp arrivalDateTimestamp = (Timestamp) document.get("arrivalDate");
                        Timestamp returnDateTimestamp = (Timestamp) document.get("returnDate");

                        trip.setDepartureDate(departureDateTimestamp.toDate());
                        trip.setArrivalDate(arrivalDateTimestamp.toDate());
                        trip.setReturnDate(returnDateTimestamp.toDate());
                        trip.setVisitedPlace(document.get("visitedCity") + ", " + document.get("visitedCountry"));
                        trip.setVisitedCountry((String) document.get("visitedCountry"));
                        trip.setVisitedCity((String) document.get("visitedCity"));
                        trip.setHomeCountry((String) document.get("homeCountry"));
                        trip.setHomeCity((String) document.get("homeCity"));
                        trip.setHomePlace(document.get("homeCity") + ", " + document.get("homeCountry"));

                        trip.setUserUid((String) document.get("userUid"));

                        List<Forecast> forecastDestine = document.toObject(ForecastDocument.class).forecastDestination;
                        List<Forecast> forecastHome = document.toObject(ForecastDocument.class).forecastHome;

                        ((TripDetailsActivity) tripDetailsView).bindTrip(
                            trip,
                            forecastHome,
                            forecastDestine
                        );
                    }
                }
            }
        });
    }

}
