package com.example.trabalho.presenter;

import android.app.Activity;
import android.content.Intent;

import com.example.trabalho.presenter.contracts.ActivityContract;

public class HomePresenter implements ActivityContract.ActivityPresenter {

    private ActivityContract.ActivityView homeView;

    public HomePresenter(ActivityContract.ActivityView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void start() {}

    public void onClickCardView(Class activity) {
        Intent intent = new Intent(this.homeView.getContext(), activity);
        homeView.navigate(intent);
    }
}
