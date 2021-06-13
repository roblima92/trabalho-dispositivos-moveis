package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.trabalho.databinding.ActivityHomeBinding;
import com.example.trabalho.models.User;
import com.example.trabalho.presenter.HomePresenter;
import com.example.trabalho.presenter.contracts.ActivityContract;

public class HomeActivity extends AppCompatActivity implements ActivityContract.ActivityView {

    private ActivityContract.ActivityPresenter homePresenter;
    private ActivityHomeBinding homeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        User user = intent.getParcelableExtra("objUser");

        homePresenter = new HomePresenter(this);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        homeBinding.setPresenter((HomePresenter) homePresenter);
        homeBinding.setForecastActivity(ForecastActivity.class);
        homeBinding.setMyTripsActivity(MyTripsActivity.class);
        homeBinding.setProfileActivity(ProfileActivity.class);
        homeBinding.setNewTripActivity(TripActivity.class);

        homeBinding.setUser(user);
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