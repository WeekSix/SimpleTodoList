package com.zz.minitodo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy M d H:mm", Locale.getDefault());

    public static String dateToString(Date date) {
        return (sdf.format(date));
    }

    public static Date stringToDate(String dateString) {
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(0);
        }
    }
}