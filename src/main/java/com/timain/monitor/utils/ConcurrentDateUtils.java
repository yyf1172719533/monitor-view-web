package com.timain.monitor.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/4 17:08
 */
public abstract class ConcurrentDateUtils {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final ThreadLocal<DateFormat> dateThreadLocal = new ThreadLocal<>();

    public static DateFormat get() {
        DateFormat format = dateThreadLocal.get();
        if (null == format) {
            format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            dateThreadLocal.set(format);
        }
        return format;
    }

    public static DateFormat get(String datePattern) {
        DateFormat format = dateThreadLocal.get();
        if (null == format) {
            format = new SimpleDateFormat(datePattern);
            dateThreadLocal.set(format);
        }
        return format;
    }
}
