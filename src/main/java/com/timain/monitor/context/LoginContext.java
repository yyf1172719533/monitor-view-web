package com.timain.monitor.context;

import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 20:25
 */
public abstract class LoginContext {

    private static final ThreadLocal<Map<String, Object>> USER_CONTEXT = new ThreadLocal<>();

    public static void set(Map<String, Object> value) {
        USER_CONTEXT.set(value);
    }

    public static Map<String, Object> get() {
        return USER_CONTEXT.get();
    }

    public static Object get(String key) {
        Map<String, Object> data = USER_CONTEXT.get();
        if (!data.containsKey(key)) {
            return null;
        }
        return data.get(key);
    }

    public static void remove() {
        USER_CONTEXT.remove();
    }
}
