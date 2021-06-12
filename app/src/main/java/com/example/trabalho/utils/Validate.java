package com.example.trabalho.utils;

import android.os.Build;
import android.text.TextUtils;
import android.widget.EditText;

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

    public static Boolean dateCantBeGreaterThanAnotherDate(Date date1, Date date2, String message) throws Exception {
        if (date1.compareTo(date2) >= 0) {
            throw new Exception(message);
        }
        return true;
    }
}
