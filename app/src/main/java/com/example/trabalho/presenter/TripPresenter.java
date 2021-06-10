package com.example.trabalho.presenter;

import com.example.trabalho.models.Trip;
import com.example.trabalho.presenter.contracts.ActivityContract;

public class TripPresenter implements ActivityContract.ActivityPresenter {


    private Trip trip;

    public TripPresenter(Trip trip) {
        this.trip = trip;
    }

    @Override
    public void start() {

    }

    public boolean validateForm() {
        return true;
    }
}
