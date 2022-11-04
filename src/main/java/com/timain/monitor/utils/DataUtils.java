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

    public static void calculationData(List<Map<String, Object>> dataList) {
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
