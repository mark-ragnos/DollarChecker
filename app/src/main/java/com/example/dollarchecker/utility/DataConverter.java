package com.example.dollarchecker.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataConverter {
    private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public static String getDateString(Calendar date) {
        return format.format(date.getTime());
    }


    public static float getFloat(String str) {
        return Float.parseFloat(str.replace(',', '.'));
    }

}
