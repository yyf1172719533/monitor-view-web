package com.timain.monitor.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.timain.monitor.constants.KeyConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 20:30
 */
public abstract class DataUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtils.class);

    public static Map<String, Object> parseUserInfo(String userInfo) {
        if (StringUtils.isBlank(userInfo)) {
            return Maps.newHashMap();
        }
        Boolean isJson = checkIsJson(userInfo);
        if (!isJson) {
            return Maps.newHashMap();
        }
        return JSONObject.parseObject(userInfo, new TypeReference<Map<String, Object>>(){});
    }

    /**
     * 判断字符串是否是json字符串
     * @param str 字符串
     * @return true：是 false：否
     */
    public static Boolean checkIsJson(String str) {
        try {
            Object o = JSON.parse(str);
            LOGGER.info("解析json字符串为：{}", o);
            return true;
        } catch (Exception e) {
            LOGGER.error("解析字符串失败：{}", e.getMessage());
            return false;
        }
    }

    public static void calculationMonitorAlarmData(List<Map<String, Object>> dataList) {
        long activeAlarm = 0L, sheetAlarm = 0L, city00 = 0L, city01 = 0L, city02 = 0L;
        long city03 = 0L, city04 = 0L, city05 = 0L, city06 = 0L, city07 = 0L, city08 = 0L;
        long city09 = 0L, city10 = 0L, city11 = 0L, city12 = 0L, city13 = 0L, city14 = 0L;
        long city15 = 0L, city16 = 0L, city17 = 0L;
        for (Map<String, Object> map : dataList) {
            activeAlarm += calc(map, KeyConstants.ACTIVE_ALARM);
            sheetAlarm += calc(map, KeyConstants.SHEET_ALARM);
            city00 += calc(map, KeyConstants.KEY_00);
            city01 += calc(map, KeyConstants.KEY_01);
            city02 += calc(map, KeyConstants.KEY_02);
            city03 += calc(map, KeyConstants.KEY_03);
            city04 += calc(map, KeyConstants.KEY_04);
            city05 += calc(map, KeyConstants.KEY_05);
            city06 += calc(map, KeyConstants.KEY_06);
            city07 += calc(map, KeyConstants.KEY_07);
            city08 += calc(map, KeyConstants.KEY_08);
            city09 += calc(map, KeyConstants.KEY_09);
            city10 += calc(map, KeyConstants.KEY_10);
            city11 += calc(map, KeyConstants.KEY_11);
            city12 += calc(map, KeyConstants.KEY_12);
            city13 += calc(map, KeyConstants.KEY_13);
            city14 += calc(map, KeyConstants.KEY_14);
            city15 += calc(map, KeyConstants.KEY_15);
            city16 += calc(map, KeyConstants.KEY_16);
            city17 += calc(map, KeyConstants.KEY_17);
        }
        Map<String, Object> totalMap = Maps.newHashMap();
        totalMap.put(KeyConstants.SPEC_NAME, "总计");
        totalMap.put(KeyConstants.ACTIVE_ALARM, activeAlarm);
        totalMap.put(KeyConstants.SHEET_ALARM, sheetAlarm);
        totalMap.put(KeyConstants.KEY_00, city00);
        totalMap.put(KeyConstants.KEY_01, city01);
        totalMap.put(KeyConstants.KEY_02, city02);
        totalMap.put(KeyConstants.KEY_03, city03);
        totalMap.put(KeyConstants.KEY_04, city04);
        totalMap.put(KeyConstants.KEY_05, city05);
        totalMap.put(KeyConstants.KEY_06, city06);
        totalMap.put(KeyConstants.KEY_07, city07);
        totalMap.put(KeyConstants.KEY_08, city08);
        totalMap.put(KeyConstants.KEY_09, city09);
        totalMap.put(KeyConstants.KEY_10, city10);
        totalMap.put(KeyConstants.KEY_11, city11);
        totalMap.put(KeyConstants.KEY_12, city12);
        totalMap.put(KeyConstants.KEY_13, city13);
        totalMap.put(KeyConstants.KEY_14, city14);
        totalMap.put(KeyConstants.KEY_15, city15);
        totalMap.put(KeyConstants.KEY_16, city16);
        totalMap.put(KeyConstants.KEY_17, city17);

        dataList.add(totalMap);
    }

    public static void calculationAlarmOverviewData(List<Map<String, Object>> dataList) {
        long resCount = 0L;
        long alarmBeforeCompressCount = 0L;
        long alarmCount = 0L;
        long alarmAfterLocatedCount = 0L;
        long alarmAfterRelatedCount = 0L;
        long standardedLevel12AlarmCount = 0L;
        long alarmAfterDelayCount = 0L;
        long dailyDutyAlarmCount = 0L;
        long dispatchedSheetAlarmCount = 0L;
        long dispatchedSheetCount = 0L;
        long processingSheetCount = 0L;
        long closedSheetCount = 0L;
        for (Map<String, Object> map : dataList) {
            resCount += calc(map, KeyConstants.RES_COUNT);
            alarmBeforeCompressCount += calc(map, KeyConstants.ALARM_BEFORE_COMPRESS_COUNT);
            alarmCount += calc(map, KeyConstants.ALARM_COUNT);
            alarmAfterLocatedCount += calc(map, KeyConstants.ALARM_AFTER_LOCATED_COUNT);
            alarmAfterRelatedCount += calc(map, KeyConstants.ALARM_AFTER_RELATED_COUNT);
            standardedLevel12AlarmCount += calc(map, KeyConstants.STANDARDED_LEVEL_1_2_ALARM_COUNT);
            alarmAfterDelayCount += calc(map, KeyConstants.ALARM_AFTER_DELAY_COUNT);
            dailyDutyAlarmCount += calc(map, KeyConstants.DAILY_DUTY_ALARM_COUNT);
            dispatchedSheetAlarmCount += calc(map, KeyConstants.DISPATCHED_SHEET_ALARM_COUNT);
            dispatchedSheetCount += calc(map, KeyConstants.DISPATCHED_SHEET_COUNT);
            processingSheetCount += calc(map, KeyConstants.PROCESSING_SHEET_COUNT);
            closedSheetCount += calc(map, KeyConstants.CLOSEED_SHEET_COUNT);
        }
        Map<String, Object> totalMap = Maps.newHashMap();
        totalMap.put(KeyConstants.SPEC_NAME, "总计");
        totalMap.put(KeyConstants.RES_COUNT, resCount);
        totalMap.put(KeyConstants.ALARM_BEFORE_COMPRESS_COUNT, alarmBeforeCompressCount);
        totalMap.put(KeyConstants.ALARM_COUNT, alarmCount);
        totalMap.put(KeyConstants.ALARM_AFTER_LOCATED_COUNT, alarmAfterLocatedCount);
        totalMap.put(KeyConstants.ALARM_AFTER_RELATED_COUNT, alarmAfterRelatedCount);
        totalMap.put(KeyConstants.STANDARDED_LEVEL_1_2_ALARM_COUNT, standardedLevel12AlarmCount);
        totalMap.put(KeyConstants.ALARM_AFTER_DELAY_COUNT, alarmAfterDelayCount);
        totalMap.put(KeyConstants.DAILY_DUTY_ALARM_COUNT, dailyDutyAlarmCount);
        totalMap.put(KeyConstants.DISPATCHED_SHEET_ALARM_COUNT, dispatchedSheetAlarmCount);
        totalMap.put(KeyConstants.DISPATCHED_SHEET_COUNT, dispatchedSheetCount);
        totalMap.put(KeyConstants.PROCESSING_SHEET_COUNT, processingSheetCount);
        totalMap.put(KeyConstants.CLOSEED_SHEET_COUNT, closedSheetCount);

        dataList.add(totalMap);
    }

    private static Long calc(Map<String, Object> map, String key) {
        return Long.parseLong(Optional.ofNullable(map.get(key)).orElse("0").toString());
    }

    public static Map<String, Object> fillMap(Map<String, Object> data, String key, String specialty, int num) {
        Map<String, Object> returnMap = Maps.newHashMap();
        returnMap.put(KeyConstants.ROW_ID, specialty);
        returnMap.put(KeyConstants.COLUMN_ID, key);
        switch (key) {
            case KeyConstants.ALARM_BEFORE_COMPRESS_COUNT:
                returnMap.put(KeyConstants.NUM, Long.parseLong(String.valueOf(data.get(KeyConstants.COMPRESS_ALARM_COUNT)))
                        + Long.parseLong(String.valueOf(data.get(KeyConstants.ALARM_COUNT))));
                break;
            case KeyConstants.ALARM_AFTER_LOCATED_COUNT:
                returnMap.put(KeyConstants.NUM, Integer.parseInt(String.valueOf(data.get(KeyConstants.ALARM_COUNT)))
                        - Integer.parseInt(String.valueOf(data.get(KeyConstants.LOCATED_ALARM_COUNT))));
                break;
            case KeyConstants.ALARM_AFTER_RELATED_COUNT:
                returnMap.put(KeyConstants.NUM, Integer.parseInt(String.valueOf(data.get(KeyConstants.ALARM_COUNT)))
                        - Integer.parseInt(String.valueOf(data.get(KeyConstants.LOCATED_ALARM_COUNT)))
                        - Integer.parseInt(String.valueOf(data.get(KeyConstants.RELATED_ALARM_COUNT))));
                break;
            case KeyConstants.DAILY_DUTY_ALARM_COUNT:
            case KeyConstants.DISPATCHED_SHEET_ALARM_COUNT:
                try {
                    returnMap.put(KeyConstants.NUM, num + Integer.parseInt(String.valueOf(data.get(key))));
                } catch (Exception e) {
                    LOGGER.error("convert data error: {}", e.getMessage());
                    returnMap.put(KeyConstants.NUM, data.get(key));
                }
                break;
            case KeyConstants.RES_COUNT:
            case KeyConstants.ALARM_COUNT:
            case KeyConstants.STANDARDED_LEVEL_1_2_ALARM_COUNT:
            case KeyConstants.ALARM_AFTER_DELAY_COUNT:
            case KeyConstants.DISPATCHED_SHEET_COUNT:
            case KeyConstants.PROCESSING_SHEET_COUNT:
            case KeyConstants.CLOSEED_SHEET_COUNT:
                returnMap.put(KeyConstants.NUM, data.get(key));
                break;
            default:
                break;
        }
        return returnMap;
    }
}
