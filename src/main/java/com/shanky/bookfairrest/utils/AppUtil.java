package com.shanky.bookfairrest.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class AppUtil {

    private final static SimpleDateFormat DATE_WITH_TIME = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public static String parseDateInString(Date date) {
        return DATE_WITH_TIME.format(date);
    }

    public static int generateRandomNumber() {
        Random random = new Random();
        return 1000 + random.nextInt(9999);
    }

    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }
}
