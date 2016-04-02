package com.kozsabynin.createyourself.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Evgeni Developer on 02.04.2016.
 */
public class DateUtils {
    public static String getDateTextByCalendar(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(calendar.getTime());
    }
}
