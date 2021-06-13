package com.example.trabalho.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.trabalho.HomeActivity;
import com.example.trabalho.models.User;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.example.trabalho.presenter.contracts.ModelContract;
import com.example.trabalho.utils.Helper;
import com.example.trabalho.utils.Validate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.trabalho.databinding.ActivityRegisterBinding;

public class RegisterPresenter implements ActivityContract.ActivityFormPresenter {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ActivityContract.ActivityView registerView;
    private User user;
    public ActivityRegisterBinding registerBinding;

    public RegisterPresenter(ActivityContract.ActivityView registerView) {
        this.registerView = registerView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void submit(ModelContract.Model model) {
        this.user = (User) model;
        try {
            this.validate();
            this.user.setGender(Helper.getRadioButtonSelectedOnRadioGroup(registerBinding.genderRegisterGroup));
            this.user.setName(this.user.getFirstName() + " " + this.user.getLastName());

            mAuth.createUserWithEmailAndPassword(this.user.getEmail(), this.user.getPassword())
                    .addOnCompleteListener((Activity) this.registerView, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser userFirebase = mAuth.getCurrentUser();
                                db.collection("users")
                                        .document(userFirebase.getUid())
                                        .set(user.getInstanceinMap())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent intent = new Intent(registerView.getContext(), HomeActivity.class);
                                                intent.putExtra("objUser", user);
                                                registerView.navigate(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                System.out.println("ERRO: "+e.getMessage());
                                                registerView.showToast("Ops! Ocorreu um erro ao tentar realizar seu cadastro.");
                                            }
                                        });
                            } else {
                                registerView.showToast("Ops! Ocorreu um erro ao tentar realizar seu cadastro.");
                            }
                        }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.registerView.showToast(e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void validate() throws Exception {
        Validate.fieldIsRequired(registerBinding.nameRegisterInput, "nome");
        Validate.fieldIsRequired(registerBinding.lastnameRegisterInput, "sobrenome");
        Validate.fieldIsRequired(registerBinding.emailRegisterInput, "email");
        Validate.fieldIsRequired(registerBinding.passwordRegisterInput, "senha");
        Validate.fieldIsRequired(registerBinding.phoneRegisterInput, "celular");
        Validate.radioGroupIsRequired(registerBinding.genderRegisterGroup, "gênero");
        Validate.emailIsValid(registerBinding.emailRegisterInput);
        Validate.phoneIsValid(registerBinding.phoneRegisterInput);
    }
}
