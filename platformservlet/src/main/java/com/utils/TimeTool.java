package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTool {

    public static String formatTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
