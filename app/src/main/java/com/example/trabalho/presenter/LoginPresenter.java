package com.example.trabalho.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.trabalho.HomeActivity;
import com.example.trabalho.databinding.ActivityLoginBinding;
import com.example.trabalho.models.User;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.example.trabalho.presenter.contracts.ModelContract;
import com.example.trabalho.utils.Validate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPresenter implements ActivityContract.ActivityFormPresenter {

    private ActivityContract.ActivityView loginView;
    private User user;
    public ActivityLoginBinding loginBinding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public LoginPresenter(ActivityContract.ActivityView loginView) {
        this.loginView = loginView;
    }

    public void onClickCardView(Class activity) {
        Intent intent = new Intent(this.loginView.getContext(), activity);
        loginView.navigate(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void submit(ModelContract.Model userModel) {
        this.user = (User) userModel;
        try {
            this.validate();
            mAuth.signInWithEmailAndPassword(this.user.getEmail(), this.user.getPassword())
                    .addOnCompleteListener((Activity) this.loginView, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(loginView.getContext(), HomeActivity.class);
                                loginView.navigate(intent);
                            } else {
                                loginView.showToast("Dados inválidos");
                            }
                        }
                    });
        } catch (Exception e) {
            this.loginView.showToast(e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void validate() throws Exception {
        Validate.fieldIsRequired(loginBinding.loginInputEmail, "email");
        Validate.fieldIsRequired(loginBinding.loginInputPassword, "senha");
        Validate.emailIsValid(loginBinding.loginInputEmail);
    }
}
