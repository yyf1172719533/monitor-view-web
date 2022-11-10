package com.timain.monitor.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linkage.system.utils.database.DBUtil;
import com.timain.monitor.constants.Const;
import com.timain.monitor.constants.KeyConstants;
import com.timain.monitor.pojo.dto.AlarmSetDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 20:30
 */
@SuppressWarnings({"all"})
public abstract class DataUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataUtils.class);

    private static final Pattern CWK_PATTERN = Pattern.compile(Const.CWK_PATTERN);

    public static Map<String, Object> parseUserInfo(String userInfo) {
        if (StringUtils.isBlank(userInfo)) {
            return Maps.newHashMap();
        }
        Boolean isJson = checkIsJson(userInfo);
        if (!isJson) {
            return Maps.newHashMap();
        }
        return JSONObject.parseObject(userInfo, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * 判断字符串是否是json字符串
     *
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

    public static void setKey(AlarmSetDto dto) {
        // 设置各Tab页关键字
        dto.setKeyNa(Const.NA);
        dto.setKeyWi(Const.KEY_WI);
        dto.setKeyCw(Const.KEY_CW);
        dto.setKeyDs(Const.KEY_DS);
        dto.setKeyAs(Const.KEY_AS);
        dto.setKeyPl(Const.KEY_PL);
        dto.setKeyF(Const.KEY_F);
        dto.setKeyTb(Const.KEY_TB);
        dto.setKeyWiLabel(Const.KEY_WI_LABEL);
        dto.setKeyCwLabel(Const.KEY_CW_LABEL);
        dto.setKeyDsLabel(Const.KEY_DS_LABEL);
        dto.setKeyAsLabel(Const.KEY_AS_LABEL);
        dto.setKeyPlLabel(Const.KEY_PL_LABEL);
        dto.setKeyFLabel(Const.KEY_F_LABEL);
        dto.setKeyTbLabel(Const.KEY_TB_LABEL);
        dto.setdCmk(Const.MODULE_KEYS.toString());
    }

    public static void setChildWin(Map<String, Object> winInfo, List<Map<String, Object>> childWinList) {
        winInfo.put(KeyConstants.DEFAULT_CV_LIST, childWinList);
        Map<String, Object> childWinMap = Maps.newHashMap();
        childWinList.forEach(item -> childWinMap.put(String.valueOf(item.get(KeyConstants.CHILD_WIN_KEY)), item.get(KeyConstants.CHILD_WIN_NAME)));

        boolean b = winInfo.containsKey(KeyConstants.CHILD_VIEW_NAME)
                && !StringUtil.empty(winInfo.get(KeyConstants.CHILD_VIEW_NAME))
                && CWK_PATTERN.matcher(String.valueOf(winInfo.get(KeyConstants.CHILD_VIEW_NAME))).matches();
        if (b) {
            List<String> cskList = Arrays.stream(winInfo.get(KeyConstants.CHILD_VIEW_NAME).toString().split(Const.CA)).collect(Collectors.toList());
            // 删除默认子窗口列表中已经配置的子窗口
            childWinList.removeIf(next -> cskList.contains(String.valueOf(next.get(KeyConstants.CHILD_WIN_KEY))));
            // 组装已经配置的子窗口列表
            List<Map<String, Object>> mapList = cskList.stream().map(e -> {
                Map<String, Object> map = Maps.newHashMap();
                map.put(KeyConstants.CHILD_WIN_KEY, e);
                map.put(KeyConstants.CHILD_WIN_NAME, childWinMap.get(e));
                return map;
            }).collect(Collectors.toList());
            winInfo.put(KeyConstants.CV_LIST, mapList);
            return;
        }
        // 将必选的子窗口放入已配置的列表中
        List<Map<String, Object>> cvList = Lists.newArrayList();
        Iterator<Map<String, Object>> iterator = childWinList.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> next = iterator.next();
            if ("1".equals(String.valueOf(next.get(KeyConstants.REQUIRED)))) {
                cvList.add(Maps.newHashMap(next));
                iterator.remove();
            }
        }
        winInfo.put(KeyConstants.CV_LIST, cvList);
    }

    public static List<Map<String, Object>> buildNewList(List<Map<String, Object>> mapList, List<String> keys) {
        if (CollectionUtil.isEmpty(mapList)) {
            return Collections.singletonList(Maps.newHashMap());
        }
        return mapList.stream().map(item -> {
            Map<String, Object> map = Maps.newHashMap();
            keys.forEach(key -> map.put(key, item.get(key)));
            return map;
        }).collect(Collectors.toList());
    }

    public static Map<String, Object> buildNewMap(Map<String, Object> data, List<String> keys) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(KeyConstants.CV, buildNewList((List<Map<String, Object>>) data.get(KeyConstants.CV_LIST), keys));
        map.put(KeyConstants.DCV, buildNewList((List<Map<String, Object>>) data.get(KeyConstants.DEFAULT_CV_LIST), keys));
        return map;
    }

    public static String parseData(Map<String, Object> rule) {
        if (CollectionUtil.isEmpty(rule)) {
            return StringUtils.EMPTY;
        }
        if (2 == DBUtil.getDbType()) {
            return parseClob((Clob) rule.get(KeyConstants.RULE_CONTEXT));
        }
        return String.valueOf(rule.get(KeyConstants.RULE_CONTEXT));
    }

    public static String parseClob(Clob clob) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(clob.getCharacterStream());
            char[] c = new char[1024];
            int i = 0;
            while ((i = reader.read(c)) != -1) {
                builder.append(c, 0, i);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            LOGGER.error("parse clob data error: {}", throwables.getMessage());
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    public static String replaceSQ(String s) {
        return s.replaceAll("\'", "\\\\'");
    }

    public static String replaceQS(String s) {
        return s.replaceAll("\\\\'", "\'");
    }
}
