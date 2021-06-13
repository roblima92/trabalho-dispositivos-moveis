package com.example.trabalho.utils;

import android.os.Build;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.regex.Pattern;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Validate {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Boolean stringIsDateValid(String dateString) throws Exception {
        try {
            LocalDate.parse(dateString,
                    DateTimeFormatter.ofPattern("d/M/uuuu")
                            .withResolverStyle(ResolverStyle.STRICT)
            );
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Boolean dateOnLimit(Date date, String label) throws Exception {
        Long daysDifference = Helper.getDaysBetweenTwoDates(date, new Date());
        if (daysDifference > Global.LIMIT_FORECAST_DAYS) {
            throw new Exception(label + " muito distante! (Limite de " + Global.LIMIT_FORECAST_DAYS + " dias)");
        }
        return true;
    }

    public static Boolean fieldIsRequired(EditText editText, String label) throws Exception {
        if (TextUtils.isEmpty(editText.getText())) {
            String text = "O campo "+ label +" é obrigatório";
            editText.setError(text);
            throw new Exception(text);
        }
        return true;
    }

    public static Boolean radioGroupIsRequired(RadioGroup radioGroup, String campo) throws Exception {
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        if (radioButtonId == -1) {
            throw new Exception("O campo "+ campo +" é obrigatório");
        }
        return true;
    }

    public static Boolean emailIsValid(EditText email) throws Exception {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (!pat.matcher(email.getText().toString()).matches()) {
            throw new Exception("O email informado é inválido");
        }
        return true;
    }

    public static Boolean phoneIsValid(EditText phone) throws Exception {
        String phoneRegex = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})";
        Pattern pat = Pattern.compile(phoneRegex);
        if (!pat.matcher(phone.getText().toString()).matches()) {
            throw new Exception("O celular informado é inválido");
        }
        return true;
    }

    public static Boolean dateCantBeGreaterThanAnotherDate(Date date1, Date date2, String message) throws Exception {
        if (date1.compareTo(date2) > 0) {
            throw new Exception(message);
        }
        return true;
    }
}
