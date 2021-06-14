package com.example.trabalho;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalho.adapters.MyTripsAdapter;
import com.example.trabalho.databinding.ActivityMyTripsBinding;

import com.example.trabalho.models.Trip;


import com.example.trabalho.presenter.MyTripsPresenter;

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

public class MyTripsActivity extends AppCompatActivity implements ActivityContract.ActivityView, RequestFirestoreContract.RequestFirestoreView {

    private ActivityContract.ActivityPresenter myTripsPresenter;
    private ActivityMyTripsBinding myTripsBinding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private List<ModelContract.Model> trips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);

        try {
            myTripsPresenter = new MyTripsPresenter(this, mAuth);
            myTripsBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_trips);

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
                                trip.setPlace((String) document.get("place"));
                                trip.setCountry((String) document.get("country"));
                                trip.setCity((String) document.get("city"));
                                trip.setUserUid((String) document.get("userUid"));

                                trips.add(trip);
                            }
                            bindListFirestore(trips);
                        } else {
                            showToast("Ocorreu um erro ao tentar buscar suas viagens");
                        }
                    }
            });

        } catch (Exception e) {
            this.showToast("Ocorreu uma falha ao tentar buscar os dados de sua viagem");
        }
    }

    @Override
    public void bindListFirestore(List<ModelContract.Model> modelArrayList) {
        try {
            RecyclerView recyclerView = myTripsBinding.rvMyTrips;
            LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(linearLayoutManagerVertical);

            MyTripsAdapter myTripsAdapter = new MyTripsAdapter(modelArrayList);
            recyclerView.setAdapter(myTripsAdapter);
        } catch (Exception e) {
            this.showToast("Ocorreu um erro ao buscar suas viagens");
        }
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigate(Intent intent) {
        startActivity(intent);
    }
}