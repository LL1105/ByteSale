package com.sale.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date) {
        return new SimpleDateFormat(DEFAULT_PATTERN).format(date);
    }

    public static Date parse(String dateStr) throws Exception {
        return new SimpleDateFormat(DEFAULT_PATTERN).parse(dateStr);
    }
}