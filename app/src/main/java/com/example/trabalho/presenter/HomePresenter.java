package com.example.trabalho.presenter;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.trabalho.HomeActivity;
import com.example.trabalho.LoginActivity;
import com.example.trabalho.models.User;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomePresenter implements ActivityContract.ActivityPresenter {

    private ActivityContract.ActivityView homeView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public HomePresenter(ActivityContract.ActivityView homeView) {
        this.homeView = homeView;
        this.start();
    }

    @Override
    public void start() {
        DocumentReference userModel = db.collection("users").document(mAuth.getCurrentUser().getUid());
        userModel.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User user = new User();
                        user.setUid(mAuth.getCurrentUser().getUid());
                        user.setName((String) document.get("name"));
                        user.setEmail((String) document.get("email"));
                        user.setPhone((String) document.get("phone"));
                        user.setGender((String) document.get("gender"));
                        ((HomeActivity) homeView).bindUser(user);
                    }
                }
            }
        });
    }

    public void onClickCardView(Class activity) {
        Intent intent = new Intent(this.homeView.getContext(), activity);
        homeView.navigate(intent);
    }

    public void logout() {
        mAuth.getInstance().signOut();
        onClickCardView(LoginActivity.class);
    }

}
