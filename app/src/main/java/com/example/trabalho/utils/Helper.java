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

public class Helper {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long getDaysBetweenTwoDates(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.DAYS.between(localDate1, localDate2);
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
