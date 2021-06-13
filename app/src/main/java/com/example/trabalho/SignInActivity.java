//package com.example.trabalho;
//
//import android.app.ActivityOptions;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.firebase.ui.auth.AuthUI;
//import com.firebase.ui.auth.IdpResponse;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class SignInActivity extends AppCompatActivity {
//    static final int LOGIN_REQUEST_CODE = 1;
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_in);
//
//        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
//
//            List<AuthUI.IdpConfig> providers = Arrays.asList(
//                    new AuthUI.IdpConfig.EmailBuilder().build(),
//                    //new AuthUI.IdpConfig.PhoneBuilder().build(),
//                    new AuthUI.IdpConfig.GoogleBuilder().build()
//                    //new AuthUI.IdpConfig.FacebookBuilder().build(),
//                    //new AuthUI.IdpConfig.TwitterBuilder().build()
//            );
//
//            // Create and launch sign-in intent
//            startActivityForResult(
//                    AuthUI.getInstance()
//                            .createSignInIntentBuilder()
//                            .setAvailableProviders(providers)
//                            .build(),
//                    LOGIN_REQUEST_CODE);
//        } else {
//            goToHomeActivity();
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    protected void goToHomeActivity() {
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == LOGIN_REQUEST_CODE) {
//            IdpResponse response = IdpResponse.fromResultIntent(data);
//
//            if (resultCode == RESULT_OK) {
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                goToHomeActivity();
//            } else {
//                Toast.makeText(this, "Login error", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//}
