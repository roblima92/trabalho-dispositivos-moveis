package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.trabalho.databinding.ActivityLoginBinding;
import com.example.trabalho.models.User;
import com.example.trabalho.presenter.LoginPresenter;
import com.example.trabalho.presenter.contracts.ActivityContract;

public class LoginActivity extends AppCompatActivity implements ActivityContract.ActivityView {

    private ActivityContract.ActivityFormPresenter loginPresenter;
    private ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenter(this);

        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginBinding.setPresenter((LoginPresenter) loginPresenter);
        loginBinding.setUser(new User());
        loginBinding.setRegisterActivity(RegisterActivity.class);

        ((LoginPresenter) loginPresenter).loginBinding = loginBinding;
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