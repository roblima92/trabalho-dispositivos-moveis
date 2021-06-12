package com.example.trabalho.presenter;


import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.trabalho.TripDetailsActivity;
import com.example.trabalho.databinding.ActivityTripBinding;
import com.example.trabalho.models.Trip;
import com.example.trabalho.presenter.contracts.ActivityContract;

import com.example.trabalho.utils.Validate;

import java.util.Date;

public class TripPresenter implements ActivityContract.ActivityFormPresenter {


    private Trip trip;
    private ActivityContract.ActivityView tripView;
    public ActivityTripBinding tripBinding;

    public TripPresenter(ActivityContract.ActivityView tripView) {
        this.tripView = tripView;
    }

    @Override
    public void start() { }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void submit(Trip trip) {
        this.trip = trip;
        try {
            this.validate();
            Intent intent = new Intent(tripView.getContext(), TripDetailsActivity.class);
            intent.putExtra("objTrip", trip);
            tripView.navigate(intent);
        } catch (Exception e) {
            this.tripView.showToast(e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void validate() throws Exception {
        Validate.fieldIsRequired(tripBinding.inputDepartureDate, "Data de partida");
        Validate.fieldIsRequired(tripBinding.inputArrivalDate, "Data de chegada");
        Validate.fieldIsRequired(tripBinding.inputCountry, "País");
        Validate.fieldIsRequired(tripBinding.inputCity, "Cidade");
        if (trip.getDepartureDate() == null || !Validate.stringIsDateValid(tripBinding.inputDepartureDate.getText().toString())) {
            throw new Exception ("Data de partida inválida!");
        }
        if (trip.getArrivalDate() == null || !Validate.stringIsDateValid(tripBinding.inputArrivalDate.getText().toString())) {
            throw new Exception ("Data de chegada inválida!");
        }
        if (trip.getReturnDate() != null && !Validate.stringIsDateValid(tripBinding.inputReturnDate.getText().toString())) {
            throw new Exception ("Data de retorno inválida!");
        } else if (trip.getReturnDate() != null) {
            Validate.dateCantBeGreaterThanAnotherDate(trip.getArrivalDate(), trip.getReturnDate(), "Data de retorno não pode ser anterior a data de chegada!");
        }
        Validate.dateCantBeGreaterThanAnotherDate(trip.getDepartureDate(), trip.getArrivalDate(), "Data de chegada não pode ser anterior a data de partida!");
        Validate.dateCantBeGreaterThanAnotherDate(new Date(System.currentTimeMillis()-24*60*60*1000), trip.getDepartureDate(), "Data de partida não pode ser anterior a data de hoje!");
        Validate.dateOnLimit(trip.getDepartureDate(), "Data de partida");
        Validate.dateOnLimit(trip.getArrivalDate(), "Data de chegada");
    }
}
