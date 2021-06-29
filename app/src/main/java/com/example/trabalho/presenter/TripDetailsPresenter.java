package com.example.trabalho.presenter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.trabalho.TripDetailsActivity;
import com.example.trabalho.models.Forecast;
import com.example.trabalho.models.ForecastDocument;
import com.example.trabalho.models.Trip;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.example.trabalho.utils.Converter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
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

                        if (returnDateTimestamp != null) {
                            trip.setReturnDate(returnDateTimestamp.toDate());
                        }
                        trip.setDepartureDate(departureDateTimestamp.toDate());
                        trip.setArrivalDate(arrivalDateTimestamp.toDate());
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

    public void shareDetails(Trip tripDetail) {
        Context context = tripDetailsView.getContext();
        String message = "Dados de minha viagem:" +
                            "\nLugar: "+ tripDetail.getVisitedCity() + ", " + tripDetail.getVisitedCountry() +
                            "\nData de partida: "+ Converter.dateToString(tripDetail.getDepartureDate()) +
                            "\nData de chegada: "+ Converter.dateToString(tripDetail.getArrivalDate());

        if (tripDetail.getReturnDate() != null) {
            message = message + "\nData de retorno: "+ Converter.dateToString(tripDetail.getReturnDate());
        }

        message = message + "\n\nFrom Jean, não passe frio";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");

        Intent chooser = Intent.createChooser(sendIntent, "Compartilhar minha viagem");

        if (sendIntent.resolveActivity(context.getPackageManager()) != null) {
            this.tripDetailsView.navigate(chooser);
        }
    }

}
