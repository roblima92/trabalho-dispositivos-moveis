package com.example.trabalho;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.trabalho.databinding.ActivityTripBinding;
import com.example.trabalho.models.Trip;
import com.example.trabalho.presenter.TripPresenter;
import com.example.trabalho.presenter.contracts.ActivityContract;

public class TripActivity extends AppCompatActivity implements ActivityContract.ActivityView {

    private ActivityContract.ActivityFormPresenter tripPresenter;
    private ActivityTripBinding tripBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        tripPresenter = new TripPresenter(this);

        tripBinding = DataBindingUtil.setContentView(this, R.layout.activity_trip);
        tripBinding.setPresenter((TripPresenter) tripPresenter);
        tripBinding.setTrip(new Trip());

        ((TripPresenter) tripPresenter).tripBinding = tripBinding;
    }

    @Override
    public void navigate(Intent intent) {
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}