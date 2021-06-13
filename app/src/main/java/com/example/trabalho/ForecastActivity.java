package com.example.trabalho;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalho.presenter.contracts.ActivityContract;

public class ForecastActivity extends AppCompatActivity implements ActivityContract.ActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
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
