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

    public static final Map<String, String> SPEC_ID_TO_NAME_MAP = new HashMap<>(16);

    public static final Map<String, String> SHEET_MAP = new HashMap<>(16);

    static {
        SPEC_ID_TO_NAME_MAP.put("1", "移动核心网");
        SPEC_ID_TO_NAME_MAP.put("2", "IP互联网");
        SPEC_ID_TO_NAME_MAP.put("3", "传输网");
        SPEC_ID_TO_NAME_MAP.put("4", "动力环境");
        SPEC_ID_TO_NAME_MAP.put("5", "无线网");
        SPEC_ID_TO_NAME_MAP.put("6", "固定交换网");
        SPEC_ID_TO_NAME_MAP.put("7", "IP承载网");
        SPEC_ID_TO_NAME_MAP.put("8", "接入网");
        SPEC_ID_TO_NAME_MAP.put("9", "ATM");
        SPEC_ID_TO_NAME_MAP.put("10", "网管系统");
        SPEC_ID_TO_NAME_MAP.put("11", "业务平台");
        SPEC_ID_TO_NAME_MAP.put("12", "大客户");
        SPEC_ID_TO_NAME_MAP.put("13", "光缆监测");
        SPEC_ID_TO_NAME_MAP.put("14", "铁塔动环");
        SPEC_ID_TO_NAME_MAP.put("15", "IPTV");

        SHEET_MAP.put(KeyConstants.CLOSEED_SHEET_COUNT, KeyConstants.CLOSEED_SHEET_COUNT);
        SHEET_MAP.put(KeyConstants.DISPATCHED_SHEET_COUNT, KeyConstants.DISPATCHED_SHEET_COUNT);
        SHEET_MAP.put(KeyConstants.PROCESSING_SHEET_COUNT, KeyConstants.PROCESSING_SHEET_COUNT);
    }
}
