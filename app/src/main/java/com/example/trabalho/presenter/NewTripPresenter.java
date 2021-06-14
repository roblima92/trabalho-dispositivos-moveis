package com.example.trabalho.presenter;


import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.trabalho.HomeActivity;
import com.example.trabalho.TripDetailsActivity;
import com.example.trabalho.databinding.ActivityNewTripBinding;
import com.example.trabalho.models.Trip;
import com.example.trabalho.presenter.contracts.ActivityContract;

import com.example.trabalho.presenter.contracts.ModelContract;
import com.example.trabalho.utils.Validate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class NewTripPresenter implements ActivityContract.ActivityFormPresenter {


    private Trip trip;
    private ActivityContract.ActivityView tripView;
    public ActivityNewTripBinding newTripBinding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public NewTripPresenter(ActivityContract.ActivityView tripView) {
        this.tripView = tripView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void submit(ModelContract.Model tripModel) {
        this.trip = (Trip) tripModel;
        this.trip.setUserUid(mAuth.getCurrentUser().getUid());
        try {
            this.validate();

            db.collection("trips")
                    .add(trip.getInstanceinMap())
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Intent intent = new Intent(tripView.getContext(), TripDetailsActivity.class);
                            intent.putExtra("objTrip", trip);
                            tripView.navigate(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            tripView.showToast("Ops! Ocorreu um erro ao tentar salvar esta viagem.");
                        }
                    });
        } catch (Exception e) {
            this.tripView.showToast(e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void validate() throws Exception {
        Validate.fieldIsRequired(newTripBinding.newTripDepartureDateInput, "Data de partida");
        Validate.fieldIsRequired(newTripBinding.newTripArrivalDateInput, "Data de chegada");
        Validate.fieldIsRequired(newTripBinding.newTripDestinationCityInput, "Cidade, País");
        if (trip.getDepartureDate() == null || !Validate.stringIsDateValid(newTripBinding.newTripDepartureDateInput.getText().toString())) {
            throw new Exception ("Data de partida inválida!");
        }
        if (trip.getArrivalDate() == null || !Validate.stringIsDateValid(newTripBinding.newTripArrivalDateInput.getText().toString())) {
            throw new Exception ("Data de chegada inválida!");
        }
        if (trip.getReturnDate() != null && !Validate.stringIsDateValid(newTripBinding.newTripReturnDateInput.getText().toString())) {
            throw new Exception ("Data de retorno inválida!");
        } else if (trip.getReturnDate() != null) {
            Validate.dateCantBeGreaterThanAnotherDate(trip.getArrivalDate(), trip.getReturnDate(), "Data de retorno não pode ser anterior a data de chegada!");
        }
        Validate.dateCantBeGreaterThanAnotherDate(trip.getDepartureDate(), trip.getArrivalDate(), "Data de chegada não pode ser anterior a data de partida!");
        Validate.dateCantBeGreaterThanAnotherDate(new Date(System.currentTimeMillis()-24*60*60*1000), trip.getDepartureDate(), "Data de partida não pode ser anterior a data de hoje!");
        Validate.dateOnLimit(trip.getDepartureDate(), "Data de partida");
        Validate.dateOnLimit(trip.getArrivalDate(), "Data de chegada");
    }

    public void changeVisibleReturnDate(Trip tripLayout, ConstraintLayout constraintLayout) {
        tripLayout.setHasReturnDate(!tripLayout.getHasReturnDate());
        constraintLayout.setVisibility(constraintLayout.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }
}
