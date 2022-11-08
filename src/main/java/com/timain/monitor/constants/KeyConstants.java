package com.timain.monitor.constants;

/**
 * key值常量
 * @author yyf
 * @version 1.0
 * @date 2022/11/4 14:14
 */
public interface KeyConstants {

    String AREA_ID = "area_id";

    String NUM = "num";

    String COLUMN_ID = "columnId";

    String SPEC_ID = "spec_id";

    String SPEC_NAME = "spec_name";

    String ROW_ID = "rowId";

    String START_TIME = "startTime";

    String BEGIN_TIME = "beginTime";

    String END_TIME = "endTime";

    String CITY_ID = "city_id";

    String NODE_ATTRS = "nodeAttrs";

    String SUB_NODES = "subNodes";

    String SPECIALTY = "specialty";

    String TABLE = "table";

    String TA_HISTORY_ALARM = "ta_historyalarm_";

    String SPEC_CITY_STAT = "specCityStat";

    String SPEC_GENERAL_STAT = "specGeneralStat";

    /**
     * 已监控网元数
     */
    String RES_COUNT = "res_count";

    /**
     * 总告警数
     */
    String ALARM_BEFORE_COMPRESS_COUNT = "alarm_before_compress_count";

    /**
     * 压缩告警数
     */
    String COMPRESS_ALARM_COUNT = "compress_alarm_count";

    /**
     * 压缩后告警数
     */
    String ALARM_COUNT = "alarm_count";

    /**
     * 标工后告警数
     */
    String ALARM_AFTER_LOCATED_COUNT = "alarm_after_located_count";

    /**
     * 关联后告警数
     */
    String ALARM_AFTER_RELATED_COUNT = "alarm_after_related_count";

    /**
     * 标准严主数
     */
    String STANDARDED_LEVEL_1_2_ALARM_COUNT = "standarded_level_1_2_alarm_count";

    /**
     * 延时后告警数
     */
    String ALARM_AFTER_DELAY_COUNT = "alarm_after_delay_count";

    /**
     * 关注告警数
     */
    String DAILY_DUTY_ALARM_COUNT = "daily_duty_alarm_count";

    /**
     * 派单告警数
     */
    String DISPATCHED_SHEET_ALARM_COUNT = "dispatched_sheet_alarm_count";

    /**
     * 已派发工单数
     */
    String DISPATCHED_SHEET_COUNT = "dispatched_sheet_count";

    /**
     * 在途工单数
     */
    String PROCESSING_SHEET_COUNT = "processing_sheet_count";

    /**
     * 已关闭工单数
     */
    String CLOSEED_SHEET_COUNT = "closeed_sheet_count";

    String LOCATED_ALARM_COUNT = "located_alarm_count";

    String RELATED_ALARM_COUNT = "related_alarm_count";

    String ACTIVE_ALARM = "activealarm";

    String SHEET_ALARM = "sheetalarm";

    String KEY_00 = "00";
    String KEY_01 = "01";
    String KEY_02 = "02";
    String KEY_03 = "03";
    String KEY_04 = "04";
    String KEY_05 = "05";
    String KEY_06 = "06";
    String KEY_07 = "07";
    String KEY_08 = "08";
    String KEY_09 = "09";
    String KEY_10 = "10";
    String KEY_11 = "11";
    String KEY_12 = "12";
    String KEY_13 = "13";
    String KEY_14 = "14";
    String KEY_15 = "15";
    String KEY_16 = "16";
    String KEY_17 = "17";

    String CREATOR = "creator";

    String DEFAULT_CREATOR = "-1";

    String VIEW_ID = "view_id";

    String VIEW_NAME = "view_name";

    String MONITOR_VIEW_NAME = "monitor_viewname";

    String WINDOW_NUM = "windownum";

    String ITEM_NAME = "item_name";

    String IS_MULTI = "ismulti";

    String ITEM_PIC_NAME = "item_picname";

    String UPDATE_ATTR = "updateatrr";

    String ITEM_TYPE = "item_type";

    String ITEM_URL = "item_url";

    String ITEM_ACCEPT = "item_accept";

    String LEVEL = "level";

    String FILE = "file";

    String CHILD_WIN_KEY = "childwinkey";

    String CHILD_WIN_NAME = "childwinname";

    String CHILD_VIEW_NAME = "childviewname";

    String WINDOW_ID = "window_id";

    String WINDOW_NAME = "window_name";

    String WINDOW_X = "window_x";

    String WINDOW_Y = "window_y";

    String WINDOW_W = "window_w";

    String WINDOW_H = "window_h";

    String WINDOW_DEPTH = "window_depth";

    String WINDOW_UNIQUE_KEY = "window_uniquekey";

    String LOAD_CUST_ALARMS = "load_custalarms";

    String SEQUENCE = "sequence";

    String STATE_EN_NAME = "state_enname";

    String STATE_VALUE = "state_value";

    String STATE_PIC = "state_pic";

    String STATE_CH_NAME = "state_chname";

    String TOOLBAR_EN_NAME = "toolbar_enname";

    String TOOLBAR_CH_NAME = "toolbar_chname";

    String FIELD_NAME = "field_name";

    String FIELD_DESC = "field_desc";

    String USE_TYPE = "usetype";

    String STATE_DESC = "state_desc";
}
