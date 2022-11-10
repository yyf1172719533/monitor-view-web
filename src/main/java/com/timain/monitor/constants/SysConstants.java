package com.timain.monitor.constants;

import java.util.*;

/**
 * 系统常量
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 17:24
 */
public final class SysConstants {

    public static final String USER_KEY = "session_userinfo";

    public static final String VOICE_RESOURCE_PATH = "system.resource.warn-voice.voicedir";

    public static final Map<String, String> SPEC_ID_TO_NAME_MAP = new HashMap<>(16);

    public static final Map<String, String> SHEET_MAP = new HashMap<>(16);

    public static final List<String> WIN_INFO_LIST = new ArrayList<>();

    public static final List<String> CHILD_WIN_INFO_LIST = new ArrayList<>();

    public static final List<String> DISPLAY_COLUMN_INFO_LIST = new ArrayList<>();

    public static final List<String> STATE_INFO_LIST = new ArrayList<>();

    public static final List<String> PRE_LOAD_INFO_LIST = new ArrayList<>();

    public static final List<String> WIN_FILTER_INFO_LIST = new ArrayList<>();

    public static final List<String> TOOL_BAR_INFO_LIST = new ArrayList<>();

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

        WIN_INFO_LIST.add(KeyConstants.CREATOR);
        WIN_INFO_LIST.add(KeyConstants.WINDOW_NAME);
        WIN_INFO_LIST.add(KeyConstants.WINDOW_DESC);
        WIN_INFO_LIST.add(KeyConstants.WINDOW_ENABLE);
        WIN_INFO_LIST.add(KeyConstants.LOAD_CUST_ALARM);

        CHILD_WIN_INFO_LIST.add(KeyConstants.CHILD_WIN_KEY);
        CHILD_WIN_INFO_LIST.add(KeyConstants.CHILD_WIN_NAME);

        DISPLAY_COLUMN_INFO_LIST.add(KeyConstants.ALL_FN);
        DISPLAY_COLUMN_INFO_LIST.add(KeyConstants.ALL_FD);
        DISPLAY_COLUMN_INFO_LIST.add(KeyConstants.AL_FN);
        DISPLAY_COLUMN_INFO_LIST.add(KeyConstants.SEQ);
        DISPLAY_COLUMN_INFO_LIST.add(KeyConstants.SEQUENCE);

        STATE_INFO_LIST.add(KeyConstants.ALL_SE);
        STATE_INFO_LIST.add(KeyConstants.ALL_SC);
        STATE_INFO_LIST.add(KeyConstants.AL_SE);

        PRE_LOAD_INFO_LIST.add(KeyConstants.PT);
        PRE_LOAD_INFO_LIST.add(KeyConstants.PP);

        WIN_FILTER_INFO_LIST.add(KeyConstants.RULE_ID);
        WIN_FILTER_INFO_LIST.add(KeyConstants.RULE_NAME);
        WIN_FILTER_INFO_LIST.add(KeyConstants.CREATOR_NAME);
        WIN_FILTER_INFO_LIST.add(KeyConstants.CREATE_TIME_LABEL);
        WIN_FILTER_INFO_LIST.add(KeyConstants.STARTED);
        WIN_FILTER_INFO_LIST.add(KeyConstants.STARTED_LABEL);
        WIN_FILTER_INFO_LIST.add(KeyConstants.IS_PUBLIC);
        WIN_FILTER_INFO_LIST.add(KeyConstants.IS_PUBLIC_LABEL);
        WIN_FILTER_INFO_LIST.add(KeyConstants.BINDING);

        TOOL_BAR_INFO_LIST.add(KeyConstants.ALL_TE);
        TOOL_BAR_INFO_LIST.add(KeyConstants.ALL_TC);
        TOOL_BAR_INFO_LIST.add(KeyConstants.AL_TE);
    }
}
