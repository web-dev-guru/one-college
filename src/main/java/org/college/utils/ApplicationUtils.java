package org.college.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ApplicationUtils {
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd";
    public static String getCurrentDate(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }
}
