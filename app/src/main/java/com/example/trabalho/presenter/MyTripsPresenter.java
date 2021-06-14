package com.example.trabalho.presenter;

import com.example.trabalho.presenter.contracts.ActivityContract;
import com.google.firebase.auth.FirebaseAuth;

public class MyTripsPresenter implements ActivityContract.ActivityPresenter {

    private ActivityContract.ActivityView myTripsView;
    private FirebaseAuth mAuth;

    public MyTripsPresenter(ActivityContract.ActivityView myTripsView, FirebaseAuth mAuth) {
        this.myTripsView = myTripsView;
        this.mAuth = mAuth;
    }

    @Override
    public void start() {

    }
}
