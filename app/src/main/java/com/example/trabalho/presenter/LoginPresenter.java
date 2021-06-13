package com.example.trabalho.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.example.trabalho.HomeActivity;
import com.example.trabalho.databinding.ActivityLoginBinding;
import com.example.trabalho.models.User;
import com.example.trabalho.presenter.contracts.ActivityContract;
import com.example.trabalho.presenter.contracts.ModelContract;
import com.example.trabalho.utils.Validate;

public class LoginPresenter implements ActivityContract.ActivityFormPresenter {

    private ActivityContract.ActivityView loginView;
    private User user;
    public ActivityLoginBinding loginBinding;

    public LoginPresenter(ActivityContract.ActivityView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void start() {

    }

    public void onClickCardView(Activity activity) {
        Intent intent = new Intent(this.loginView.getContext(), activity.getClass());
        loginView.navigate(intent);
    }

    public void setVisiblePassword(ImageView image1, ImageView image2, EditText passwordInput) {
        int PASSWORD_LOCK = 129;
        image1.setVisibility(image1.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        image2.setVisibility(image2.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        passwordInput.setInputType(passwordInput.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ? PASSWORD_LOCK : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void submit(ModelContract.Model user) {
        this.user = (User) user;
        try {
            this.validate();
            Intent intent = new Intent(loginView.getContext(), HomeActivity.class);
            intent.putExtra("objUser", this.user);
            loginView.navigate(intent);
        } catch (Exception e) {
            this.loginView.showToast(e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void validate() throws Exception {
        Validate.fieldIsRequired(loginBinding.emailInput, "email");
        Validate.fieldIsRequired(loginBinding.passwordInput, "senha");
        Validate.emailIsValid(loginBinding.emailInput);
    }
}
