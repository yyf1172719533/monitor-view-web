package com.timain.monitor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linkage.commons.util.DateTimeUtil;
import com.linkage.system.utils.corba.CorbaMsgSupport;
import com.timain.monitor.constants.KeyConstants;
import com.timain.monitor.constants.SysConstants;
import com.timain.monitor.constants.TopicConstants;
import com.timain.monitor.enums.ErrorEnum;
import com.timain.monitor.exception.BusinessException;
import com.timain.monitor.mapper.AlarmViewMapper;
import com.timain.monitor.pojo.dto.AlarmDetailDto;
import com.timain.monitor.pojo.dto.AlarmViewDto;
import com.timain.monitor.pojo.dto.QueryDetailDto;
import com.timain.monitor.pojo.vo.CityVo;
import com.timain.monitor.service.AlarmViewService;
import com.timain.monitor.utils.ConcurrentDateUtils;
import com.timain.monitor.utils.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 11:56
 */
@SuppressWarnings({"all"})
@Service
public class AlarmViewServiceImpl implements AlarmViewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmViewServiceImpl.class);

    @Autowired
    private AlarmViewMapper alarmViewMapper;
    @Autowired
    private CorbaMsgSupport corbaMsgSupport;

    /**
     * 获取公共的字段列
     *
     * @return 列
     */
    @Override
    public String[] getCommonColumns() {
        return new String[]{"res_count", "alarm_before_compress_count", "alarm_count", "alarm_after_located_count", "alarm_after_related_count",
                "standarded_level_1_2_alarm_count", "alarm_after_delay_count", "daily_duty_alarm_count", "dispatched_sheet_alarm_count",
                "dispatched_sheet_count", "processing_sheet_count", "closeed_sheet_count", "activealarm", "sheetalarm",
                "00", "01", "02", "05", "09", "03", "04", "07", "08", "10", "12", "14", "15", "06", "11", "16", "17", "13"};
    }

    /**
     * 查询工单统计明细
     *
     * @param dto 查询参数
     * @return 工单统计明细
     */
    @Override
    public List<Map<String, String>> loadSheetDetailDataByDate(AlarmDetailDto dto) {
        Map<String, Object> params = BeanUtil.beanToMap(dto);
        params.put(KeyConstants.START_TIME, new DateTimeUtil(params.get(KeyConstants.START_TIME).toString()).getLongTime());
        params.put(KeyConstants.END_TIME, new DateTimeUtil(params.get(KeyConstants.END_TIME).toString()).getLongTime());
        return alarmViewMapper.loadSheetDetailDataByDate(params);
    }

    /**
     * 查询工单统计明细【未选择时间】
     *
     * @param areaId    areaId
     * @param spec      spec
     * @param sheetType sheetType
     * @return 工单统计明细
     */
    @Override
    public List<Map<String, Object>> loadSheetDetailData(String areaId, String spec, String sheetType) {
        List<CityVo> regionList = alarmViewMapper.getAreaRegionList(areaId);
        Map<String, Object> cityMap = Maps.newHashMap();
        StringBuilder sb = new StringBuilder();
        regionList.forEach(item -> {
            if (item.getCityLayer() != 1) {
                sb.append(",").append(item.getCityId());
                cityMap.put(item.getCityId(), item.getCityName());
            } else {
                sb.append(",all");
                cityMap.put("all", "全省");
            }
        });
        String cityStr = sb.substring(1);
        Map<String, Object> nodeAttr = Maps.newHashMap();
        nodeAttr.put(KeyConstants.CITY_ID, cityStr);
        nodeAttr.put(KeyConstants.SPECIALTY, spec);

        Map<String, Map<String, Object>> param = Maps.newHashMap();
        param.put(KeyConstants.NODE_ATTRS, nodeAttr);

        Map<String, List<Map<String, Object>>> data;
        try {
            data = corbaMsgSupport.sendAndRecvMsg(TopicConstants.SHEET_DETAIL_STATISTIC, param);
            List<Map<String, Object>> mapList = data.get(KeyConstants.SUB_NODES);
            return mapList.stream().map(item -> {
                Map<String, Object> nodeAttrMap = (Map<String, Object>) item.get(KeyConstants.NODE_ATTRS);
                String cityId = String.valueOf(nodeAttrMap.get(KeyConstants.CITY_ID));
                String specId = String.valueOf(nodeAttrMap.get(KeyConstants.SPECIALTY));
                Map<String, Object> map = Maps.newHashMap();
                map.put("specName", SysConstants.SPEC_ID_TO_NAME_MAP.get(specId));
                map.put("cityName", cityMap.get(cityId));
                if (KeyConstants.DISPATCHED_SHEET_COUNT.equals(sheetType)) {
                    map.put(KeyConstants.NUM, String.valueOf(nodeAttrMap.get(KeyConstants.DISPATCHED_SHEET_COUNT)));
                } else if (KeyConstants.PROCESSING_SHEET_COUNT.equals(sheetType)) {
                    map.put(KeyConstants.NUM, String.valueOf(nodeAttrMap.get(KeyConstants.PROCESSING_SHEET_COUNT)));
                } else if (KeyConstants.CLOSEED_SHEET_COUNT.equals(sheetType)) {
                    map.put(KeyConstants.NUM, String.valueOf(nodeAttrMap.get(KeyConstants.CLOSEED_SHEET_COUNT)));
                }
                return map;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("send and receive msg error: {}", e.getMessage());
            throw new BusinessException(ErrorEnum.CORBA_TOPIC_MSG_ERROR);
        }
    }

    /**
     * 查询所有全专业告警概况信息
     *
     * @param dto 查询参数
     * @return 告警概况信息
     */
    @Override
    public List<Map<String, Object>> queryAlarmOverview(AlarmViewDto dto) {
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        String areaId = dto.getAreaId();
        List<Map<String, Object>> dataList;
        if (new DateTimeUtil().getYYYY_MM_DD().equals(startTime)) {
            Map<String, List<Map<String, Object>>> map = this.loadSheetStatData(areaId);
            dataList = map.get(KeyConstants.SPEC_GENERAL_STAT);
        } else {
            Map<String, Object> param = Maps.newHashMap();
            param.put(KeyConstants.START_TIME, startTime);
            param.put(KeyConstants.END_TIME, endTime);
            param.put("areaId", areaId);
            dataList = this.loadSpecGeneralAlarmStatByDate(param);
        }
        Map<String, Map<String, Object>> tempMap = Maps.newHashMap();
        List<Map<String, Object>> mapList = dataList.stream().map(item -> {
            String specId = String.valueOf(item.get(KeyConstants.ROW_ID));
            Map<String, Object> map = null;
            if (!tempMap.containsKey(specId)) {
                map = Maps.newHashMap();
                map.put(KeyConstants.SPEC_ID, specId);
                map.put(KeyConstants.SPEC_NAME, SysConstants.SPEC_ID_TO_NAME_MAP.get(specId));
                map.put(String.valueOf(item.get(KeyConstants.COLUMN_ID)), item.get(KeyConstants.NUM));
                tempMap.put(specId, map);
            } else {
                map = tempMap.get(specId);
                map.put(String.valueOf(item.get(KeyConstants.COLUMN_ID)), item.get(KeyConstants.NUM));
            }
            return map;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        this.fillZeroIntoMap(mapList);
        this.sortBySpecId(mapList);
        this.setTotalData(mapList, true);
        return mapList;
    }

    /**
     * 查询所有全省各地市监控告警统计列表
     *
     * @param dto 查询参数
     * @return 全省各地市监控告警统计列表
     */
    @Override
    public List<Map<String, Object>> queryMonitorAlarm(AlarmViewDto dto) {
        Map<String, List<Map<String, Object>>> dataMap = this.loadSheetStatData(dto.getAreaId());
        List<Map<String, Object>> mapList = dataMap.get(KeyConstants.SPEC_CITY_STAT);
        List<String> specList = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        Map<String, Map<String, Object>> tempMap = Maps.newHashMap();
        List<Map<String, Object>> maps = mapList.stream().map(item -> {
            String specId = String.valueOf(item.get(KeyConstants.ROW_ID));
            Map<String, Object> map = null;
            if (specList.contains(specId)) {
                if (!tempMap.containsKey(specId)) {
                    map = Maps.newHashMap();
                    map.put(KeyConstants.SPEC_ID, specId);
                    map.put(KeyConstants.SPEC_NAME, SysConstants.SPEC_ID_TO_NAME_MAP.get(specId));
                    map.put(String.valueOf(item.get(KeyConstants.COLUMN_ID)), item.get(KeyConstants.NUM));
                    tempMap.put(specId, map);
                } else {
                    map = tempMap.get(specId);
                    map.put(String.valueOf(item.get(KeyConstants.COLUMN_ID)), item.get(KeyConstants.NUM));
                }
            }
            return map;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        this.fillZeroIntoMap(maps);
        this.sortBySpecId(maps);
        this.setTotalData(maps, false);
        return maps;
    }

    /**
     * 加载工单统计数据
     *
     * @param areaId 域ID
     * @return 工单统计数据
     */
    @Override
    public Map<String, List<Map<String, Object>>> loadSheetStatData(String areaId) {
        List<CityVo> regionList = alarmViewMapper.getAreaRegionList(areaId);
        StringBuilder sb = new StringBuilder();
        regionList.forEach(item -> {
            if (item.getCityLayer() != 1) {
                sb.append(",").append(item.getCityId());
            } else {
                sb.append(",all");
            }
        });
        String cityStr = sb.substring(1);
        List<Map<String, Object>> dataList = alarmViewMapper.loadSpecCityAlarmStat();
        Map<String, Map<String, Object>> tempMap = Maps.newHashMap();
        List<Map<String, Object>> maps = dataList.stream().map(item -> {
            String specId = String.valueOf(item.get(KeyConstants.ROW_ID));
            Map<String, Object> map = null;
            if (cityStr.contains(String.valueOf(item.get(KeyConstants.COLUMN_ID)))) {
                if (!tempMap.containsKey(specId)) {
                    map = Maps.newHashMap();
                    map.put(KeyConstants.ROW_ID, specId);
                    map.put(KeyConstants.COLUMN_ID, "00");
                    map.put(KeyConstants.NUM, item.get(KeyConstants.NUM));
                    tempMap.put(specId, map);
                } else {
                    map = tempMap.get(specId);
                    int num = Integer.parseInt(String.valueOf(map.get(KeyConstants.NUM)));
                    map.put(KeyConstants.NUM, num + Integer.parseInt(String.valueOf(item.get(KeyConstants.NUM))));
                }
            }
            return map;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        dataList.addAll(maps);

        Map<String, List<Map<String, Object>>> resultMap = new HashMap<>(16);
        resultMap.put(KeyConstants.SPEC_CITY_STAT, dataList);
        // todo corba
        Map<String, Map<String, Object>> params = Maps.newHashMap();
        Map<String, Object> nodeAttr = Maps.newHashMap();
        nodeAttr.put(KeyConstants.CITY_ID, cityStr);
        params.put(KeyConstants.NODE_ATTRS, nodeAttr);
        resultMap.put(KeyConstants.SPEC_GENERAL_STAT, this.querySpecGeneralStat(params));
        return resultMap;
    }

    /**
     * 加载工单统计数据
     *
     * @param params 查询参数
     * @return 工单统计数据
     */
    @Override
    public List<Map<String, Object>> loadSpecGeneralAlarmStatByDate(Map<String, Object> params) {
        params.put(KeyConstants.START_TIME, new DateTimeUtil(String.valueOf(params.get(KeyConstants.START_TIME))).getLongTime());
        params.put(KeyConstants.END_TIME, new DateTimeUtil(String.valueOf(params.get(KeyConstants.END_TIME))).getLongTime());
        return alarmViewMapper.loadSpecGeneralAlarmStatByDate(params);
    }

    /**
     * 查询工单统计信息
     *
     * @param params 查询参数
     * @return 工单统计信息
     */
    @Override
    public List<Map<String, Object>> querySpecGeneralStat(Map<String, Map<String, Object>> params) {
        Map<String, List<Map<String, Object>>> dataMap;
        try {
            dataMap = corbaMsgSupport.sendAndRecvMsg(TopicConstants.SHEET_STAT, params);
        } catch (Exception e) {
            LOGGER.error("send and receive msg error: {}", e.getMessage());
            throw new BusinessException(ErrorEnum.CORBA_TOPIC_MSG_ERROR);
        }
        List<Map<String, Object>> mapList = dataMap.get(KeyConstants.SUB_NODES);
        List<Map<String, Object>> resultList = Lists.newArrayList();
        mapList.stream().forEach(item -> {
            Map<String, Object> map = (Map<String, Object>) item.get(KeyConstants.NODE_ATTRS);
            String specialty = String.valueOf(map.get(KeyConstants.SPECIALTY));
            int num = 0;
            if ("5".equals(specialty)) {
                num = this.findCountNum();
            }
            resultList.add(DataUtils.fillMap(map, KeyConstants.RES_COUNT, specialty, num));
            resultList.add(DataUtils.fillMap(map, KeyConstants.ALARM_BEFORE_COMPRESS_COUNT, specialty, num));
            resultList.add(DataUtils.fillMap(map, KeyConstants.ALARM_COUNT, specialty, num));
            resultList.add(DataUtils.fillMap(map, KeyConstants.ALARM_AFTER_LOCATED_COUNT, specialty, num));
            resultList.add(DataUtils.fillMap(map, KeyConstants.ALARM_AFTER_RELATED_COUNT, specialty, num));
            resultList.add(DataUtils.fillMap(map, KeyConstants.STANDARDED_LEVEL_1_2_ALARM_COUNT, specialty, num));
            resultList.add(DataUtils.fillMap(map, KeyConstants.ALARM_AFTER_DELAY_COUNT, specialty, num));
            resultList.add(DataUtils.fillMap(map, KeyConstants.DAILY_DUTY_ALARM_COUNT, specialty, num));
            resultList.add(DataUtils.fillMap(map, KeyConstants.DISPATCHED_SHEET_ALARM_COUNT, specialty, num));
            resultList.add(DataUtils.fillMap(map, KeyConstants.DISPATCHED_SHEET_COUNT, specialty, num));
            resultList.add(DataUtils.fillMap(map, KeyConstants.PROCESSING_SHEET_COUNT, specialty, num));
            resultList.add(DataUtils.fillMap(map, KeyConstants.CLOSEED_SHEET_COUNT, specialty, num));
        });
        return resultList;
    }

    /**
     * 查询工单详情信息
     *
     * @param dto 查询参数
     * @return 工单详情信息
     */
    @Override
    public List<Map<String, Object>> queryDetailInfo(QueryDetailDto dto) {
        dto.setQueryType(SysConstants.SHEET_MAP.get(dto.getQueryType()));
        Map<String, Object> map = BeanUtil.beanToMap(dto);
        map.put("specId", SysConstants.SPEC_ID_TO_NAME_MAP.get(dto.getSpecName()));
        Map<String, Object> params = Maps.newHashMap();
        params.put(KeyConstants.NODE_ATTRS, map);
        // todo corba
        Map<String, Object> result = corbaMsgSupport.sendAndRecvMsg(TopicConstants.SHEET_LIST_STAT, params);
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) result.get(KeyConstants.SUB_NODES);
        return mapList.stream().map(item -> (Map<String, Object>) item.get(KeyConstants.NODE_ATTRS)).collect(Collectors.toList());
    }

    /**
     * 填充0
     *
     * @param dataList 数据
     */
    @Override
    public void fillZeroIntoMap(List<Map<String, Object>> dataList) {
        String[] columns = getCommonColumns();
        dataList.forEach(map -> {
            for (String column : columns) {
                if (!map.containsKey(column) || Objects.isNull(map.get(column))) {
                    map.put(column, 0);
                }
            }
        });
    }

    /**
     * 按照specId排序
     *
     * @param dataList 数据
     */
    @Override
    public void sortBySpecId(List<Map<String, Object>> dataList) {
        dataList.sort((Comparator.comparing(o -> String.valueOf(o.get(KeyConstants.SPEC_ID)))));
    }

    /**
     * 统计
     *
     * @param dataList 数据
     * @param flag     标识
     */
    @Override
    public void setTotalData(List<Map<String, Object>> dataList, boolean flag) {
        if (flag) {
            DataUtils.calculationAlarmOverviewData(dataList);
            return;
        }
        DataUtils.calculationMonitorAlarmData(dataList);
    }

    /**
     * 无线网小区自动派单的告警统计SQL（去掉子告警和工程告警）
     *
     * @return 结果值
     */
    @Override
    public int findCountNum() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long time = calendar.getTimeInMillis() / 1000;
        Map<String, Object> param = Maps.newHashMap();
        param.put(KeyConstants.BEGIN_TIME, time);
        param.put(KeyConstants.END_TIME, time + 86400);
        String dateStr = ConcurrentDateUtils.get("yyyy-MM").format(new Date());
        param.put(KeyConstants.TABLE, KeyConstants.TA_HISTORY_ALARM + dateStr);
        return alarmViewMapper.findCountNum(param);
    }
}
