package com.timain.monitor.utils;

import com.timain.monitor.constants.Const;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 17:36
 */
public final class StringUtil extends StringUtils {

    /**
     * 判断字符串是否为空
     *
     * @param s
     * @return
     */
    public static boolean empty(String s) {
        return null == s || "".equals(s.trim()) || Const.NULL.equalsIgnoreCase(s.trim());
    }

    /**
     * 判断对象是否为空
     *
     * @param o
     * @return
     */
    public static boolean empty(Object o) {
        return o == null || empty(String.valueOf(o));
    }

    /**
     * 创建json数据格式
     *
     * @param data
     * @param cols
     * @return
     */
    public static String createJson(Map data, String[] cols) {
        if (data == null || cols == null) {
            return "{}";
        }
        StringBuilder json = new StringBuilder();
        json.append("{");
        String val = null;
        for (int i = 0, len = cols.length; i < len; i++) {
            val = String.valueOf(data.get(cols[i]));
            json.append(i > 0 ? Const.CA : "");
            json.append(Const.SQ).append(cols[i]).append(Const.SQ);
            json.append(Const.MQ);
            json.append(Const.SQ).append(empty(val) ? Const.NA : val).append(Const.SQ);
        }
        json.append("}");
        return json.toString();
    }

    /**
     * 创建json数据格式
     *
     * @param list
     * @param cols
     * @return
     */
    public static String createJson(List<Map> list, String[] cols) {
        if (list == null || cols == null) {
            return "[]";
        }
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0, size = list.size(); i < size; i++) {
            json.append(i > 0 ? Const.CA : "");
            json.append(createJson(list.get(i), cols));
        }
        json.append("]");
        return json.toString();
    }
}
