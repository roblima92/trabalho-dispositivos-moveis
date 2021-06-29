package com.example.trabalho.utils;

import android.os.Build;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Helper {

    public static long getDaysBetweenTwoDates(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static void setVisiblePassword(ImageView image1, ImageView image2, EditText passwordInput) {
        int PASSWORD_LOCK = 129;
        image1.setVisibility(image1.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        image2.setVisibility(image2.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        passwordInput.setInputType(passwordInput.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ? PASSWORD_LOCK : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    public static String getRadioButtonSelectedOnRadioGroup(RadioGroup radioGroup) {
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonId);
        int radioButtonIndex = radioGroup.indexOfChild(radioButton);
        RadioButton radioButtonSelected = (RadioButton) radioGroup.getChildAt(radioButtonIndex);
        return radioButtonSelected.getText().toString();
    }
}
