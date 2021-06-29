package com.example.trabalho;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.trabalho.databinding.ActivityProfileBinding;
import com.example.trabalho.models.User;
import com.example.trabalho.presenter.ProfilePresenter;
import com.example.trabalho.presenter.contracts.ActivityContract;

public class ProfileActivity extends AppCompatActivity implements ActivityContract.ActivityView {

    private ActivityContract.ActivityPresenter profilePresenter;
    private ActivityProfileBinding profileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilePresenter = new ProfilePresenter(this);
    }

    public void bindUser(User user) {
        profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        profileBinding.setPresenter((ProfilePresenter) profilePresenter);
        profileBinding.setUser(user);
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