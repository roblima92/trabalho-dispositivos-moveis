package com.example.trabalho;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalho.adapters.MyTripsAdapter;
import com.example.trabalho.databinding.ActivityMyTripsBinding;
import com.example.trabalho.models.Trip;
import com.example.trabalho.presenter.MyTripsPresenter;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.example.trabalho.presenter.contracts.ModelContract;
import com.example.trabalho.presenter.contracts.RequestFirestoreContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyTripsActivity extends AppCompatActivity implements ActivityContract.ActivityView, RequestFirestoreContract.RequestFirestoreView {

    private ActivityContract.ActivityPresenter myTripsPresenter;
    private ActivityMyTripsBinding myTripsBinding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);
        myTripsPresenter = new MyTripsPresenter(this, mAuth);
        myTripsBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_trips);
    }

    @Override
    public void bindListFirestore(List<ModelContract.Model> modelArrayList) {
        try {
            RecyclerView recyclerView = myTripsBinding.recyclerViewMyTrips;
            LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(linearLayoutManagerVertical);

            MyTripsAdapter myTripsAdapter = new MyTripsAdapter(modelArrayList);
            recyclerView.setAdapter(myTripsAdapter);
        } catch (Exception e) {
            this.showToast("Ocorreu um erro ao buscar suas viagens");
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