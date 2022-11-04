package com.timain.monitor.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统常量
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 17:24
 */
public final class SysConstants {

    public static final String USER_KEY = "session_userinfo";

    public static final Map<String, String> SPECIDTONAMEMAP = new HashMap<>(16);

    static {
        SPECIDTONAMEMAP.put("1", "移动核心网");
        SPECIDTONAMEMAP.put("2", "IP互联网");
        SPECIDTONAMEMAP.put("3", "传输网");
        SPECIDTONAMEMAP.put("4", "动力环境");
        SPECIDTONAMEMAP.put("5", "无线网");
        SPECIDTONAMEMAP.put("6", "固定交换网");
        SPECIDTONAMEMAP.put("7", "IP承载网");
        SPECIDTONAMEMAP.put("8", "接入网");
        SPECIDTONAMEMAP.put("9", "ATM");
        SPECIDTONAMEMAP.put("10", "网管系统");
        SPECIDTONAMEMAP.put("11", "业务平台");
        SPECIDTONAMEMAP.put("12", "大客户");
        SPECIDTONAMEMAP.put("13", "光缆监测");
        SPECIDTONAMEMAP.put("14", "铁塔动环");
        SPECIDTONAMEMAP.put("15", "IPTV");
    }
}
