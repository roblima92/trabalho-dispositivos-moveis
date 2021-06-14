package com.example.trabalho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.trabalho.databinding.ActivityHomeBinding;
import com.example.trabalho.models.User;
import com.example.trabalho.presenter.HomePresenter;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity implements ActivityContract.ActivityView {

    private ActivityContract.ActivityPresenter homePresenter;
    private ActivityHomeBinding homeBinding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            ActivityContract.ActivityView homeView = this;
            Activity homeActivity = this;
            DocumentReference userModel = db.collection("users").document(mAuth.getCurrentUser().getUid());
            userModel.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            User user = new User();
                            user.setName((String) document.get("name"));
                            user.setEmail((String) document.get("email"));
                            user.setPhone((String) document.get("phone"));
                            user.setGender((String) document.get("gender"));
                            homePresenter = new HomePresenter(homeView, mAuth);
                            homeBinding = DataBindingUtil.setContentView(homeActivity, R.layout.activity_home);
                            homeBinding.setPresenter((HomePresenter) homePresenter);
                            homeBinding.setForecastActivity(ForecastActivity.class);
                            homeBinding.setMyTripsActivity(MyTripsActivity.class);
                            homeBinding.setProfileActivity(ProfileActivity.class);
                            homeBinding.setNewTripActivity(NewTripActivity.class);
                            homeBinding.setUser(user);
                        }
                    }
                }
            });

        } catch (Exception e) {
            this.showToast("Ocorreu uma falha ao tentar buscar seus dados");
        }
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