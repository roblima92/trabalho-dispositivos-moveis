package com.example.trabalho.presenter;

import android.content.Intent;

import com.example.trabalho.LoginActivity;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.google.firebase.auth.FirebaseAuth;


public class HomePresenter implements ActivityContract.ActivityPresenter {

    private ActivityContract.ActivityView homeView;
    private FirebaseAuth mAuth;

    public HomePresenter(ActivityContract.ActivityView homeView, FirebaseAuth mAuth) {
        this.homeView = homeView;
        this.mAuth = mAuth;
    }

    @Override
    public void start() {}

    public void onClickCardView(Class activity) {
        Intent intent = new Intent(this.homeView.getContext(), activity);
        homeView.navigate(intent);
    }

    public void logout() {
        mAuth.getInstance().signOut();
        onClickCardView(LoginActivity.class);
    }

}
