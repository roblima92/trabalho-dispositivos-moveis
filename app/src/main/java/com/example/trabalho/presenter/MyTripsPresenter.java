package com.example.trabalho.presenter;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.trabalho.TripDetailsActivity;
import com.example.trabalho.models.Trip;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.example.trabalho.presenter.contracts.ModelContract;
import com.example.trabalho.presenter.contracts.RequestFirestoreContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyTripsPresenter implements ActivityContract.ActivityPresenter {

    private ActivityContract.ActivityView myTripsView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<ModelContract.Model> trips = new ArrayList<>();

    public MyTripsPresenter(ActivityContract.ActivityView myTripsView, FirebaseAuth mAuth) {
        this.myTripsView = myTripsView;
        this.mAuth = mAuth;
        this.start();
    }

    @Override
    public void start() {
        db.collection("trips")
            .whereEqualTo("userUid", mAuth.getCurrentUser().getUid())
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()  {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
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
                            trip.setHomePlace(document.get("homeCity") + ", " + document.get("homeCountry"));
                            trip.setHomeCountry((String) document.get("homeCountry"));
                            trip.setHomeCity((String) document.get("homeCity"));
                            trip.setUserUid((String) document.get("userUid"));
                            trip.setUid(document.getId());

                            trips.add(trip);
                        }
                        ((RequestFirestoreContract.RequestFirestoreView) myTripsView).bindListFirestore(trips);
                    } else {
                        myTripsView.showToast("Ocorreu um erro ao tentar buscar suas viagens");
                    }
                }
            });

    }
}
