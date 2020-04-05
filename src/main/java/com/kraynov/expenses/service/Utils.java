package com.kraynov.expenses.service;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Utils {
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat dateInputFormatter = new SimpleDateFormat("yyyy-MM-dd");

    static {
        dateInputFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
}
