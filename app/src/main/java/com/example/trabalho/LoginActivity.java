package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.login_button);
        Button register = (Button) findViewById(R.id.register_button);

        login.setOnClickListener(v -> openHomeActivity(v));
        register.setOnClickListener(v -> openRegisterActivity(v));
    }

    public void openHomeActivity(View view) {
        Intent intent = new Intent(this,HomeActivity.class);
//        EditText loginName = (EditText)findViewById(R.id.editTextTextPersonName2);
//        String qq = loginName.getText().toString();
//        EditText password = (EditText)findViewById(R.id.editTextTextPassword);
//        String ww = password.getText().toString();


//        intent.putExtra("nome",loginName.getText().toString());


//        if(qq.equals(ww)) {
            startActivity(intent);
//        }else{
//            Toast.makeText(this,"User and password are different!!",Toast.LENGTH_LONG).show();
//
//        }
    }

    public void openRegisterActivity(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}