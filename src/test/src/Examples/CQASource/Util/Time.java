package Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {

    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static String getCurrentDateTimeString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime now = LocalDateTime.now();

        return dateFormat.format(now);
    }

}
