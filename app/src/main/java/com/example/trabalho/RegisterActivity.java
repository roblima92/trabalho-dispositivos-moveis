package com.example.trabalho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

//    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView imageRegister;
    Button submitRegister;
    TextView editName;
    TextView editPhone;
    TextView editEmail;
    TextView editPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        submitRegister = findViewById(R.id.SubmitRegister);
        editPhone = findViewById(R.id.editPhone);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        imageRegister = findViewById(R.id.imageRegister);
    }

    public void register(View view){
        Intent intent = new Intent(this,TripActivity.class);

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("name", editName.getText().toString());
        user.put("email", editEmail.getText().toString());
        user.put("phone", editPhone.getText().toString());
        user.put("password", editPassword.getText().toString());

// Add a new document with a generated ID
//        db.collection("user")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
//                        startActivity(intent);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getBaseContext(), "Erro ao registrar!", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }
}