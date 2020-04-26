package com.shanky.bookfairrest.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AppUtil {

    private final static SimpleDateFormat DATE_WITH_TIME = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public static String parseDateInString(Date date) {
        return DATE_WITH_TIME.format(date);
    }

    public static Integer generateRandomNumber() {
        Random random = new Random();
        return 1000 + random.nextInt(9999);
    }
}
