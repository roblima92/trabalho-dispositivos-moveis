package com.example.trabalho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;

import com.example.trabalho.databinding.ActivityLoginBinding;
import com.example.trabalho.databinding.ActivityRegisterBinding;
import com.example.trabalho.models.User;
import com.example.trabalho.presenter.LoginPresenter;
import com.example.trabalho.presenter.RegisterPresenter;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements ActivityContract.ActivityView {

//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    ImageView imageRegister;
//    Button submitRegister;
//    TextView editName;
//    TextView editPhone;
//    TextView editEmail;
//    TextView editPassword;

    private ActivityContract.ActivityFormPresenter registerPresenter;
    private ActivityRegisterBinding registerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter = new RegisterPresenter(this);

        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerBinding.setPresenter((RegisterPresenter) registerPresenter);
        registerBinding.setUser(new User());

        ((RegisterPresenter) registerPresenter).registerBinding = registerBinding;
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