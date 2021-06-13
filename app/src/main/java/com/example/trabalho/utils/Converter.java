package com.example.trabalho.utils;

import android.widget.EditText;

import androidx.databinding.BindingConversion;
import androidx.databinding.InverseMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @InverseMethod("stringToDate")
    public static String dateToString(Date date) {
        if (date == null) return "";
        return format.format(date);
    }

    public static Date stringToDate(String date) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
