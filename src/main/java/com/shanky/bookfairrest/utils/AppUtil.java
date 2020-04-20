package com.shanky.bookfairrest.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtil {

    private final static SimpleDateFormat DATE_WITH_TIME = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public static String parseDateInString(Date date) {
        return DATE_WITH_TIME.format(date);
    }
}
