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
}
