package com.timain.monitor.service;

import com.timain.monitor.pojo.dto.AlarmDetailDto;
import com.timain.monitor.pojo.dto.AlarmViewDto;
import com.timain.monitor.pojo.dto.QueryDetailDto;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 11:56
 */
public interface AlarmViewService {

    /**
     * 获取公共的字段列
     *
     * @return 列
     */
    String[] getCommonColumns();

    /**
     * 查询工单统计明细【已选择时间】
     *
     * @param dto 查询参数
     * @return 工单统计明细
     */
    List<Map<String, String>> loadSheetDetailDataByDate(AlarmDetailDto dto);

    /**
     * 查询工单统计明细【未选择时间】
     * @param areaId areaId
     * @param spec spec
     * @param sheetType sheetType
     * @return 工单统计明细
     */
    List<Map<String, Object>> loadSheetDetailData(String areaId, String spec, String sheetType);

    /**
     * 查询所有全专业告警概况信息
     *
     * @param dto 查询参数
     * @return 告警概况信息
     */
    List<Map<String, Object>> queryAlarmOverview(AlarmViewDto dto);

    /**
     * 查询所有全省各地市监控告警统计列表
     *
     * @param dto 查询参数
     * @return 全省各地市监控告警统计列表
     */
    List<Map<String, Object>> queryMonitorAlarm(AlarmViewDto dto);

    /**
     * 加载工单统计数据
     *
     * @param areaId 域ID
     * @return 工单统计数据
     */
    Map<String, List<Map<String, Object>>> loadSheetStatData(String areaId);

    /**
     * 加载工单统计数据
     *
     * @param params 查询参数
     * @return 工单统计数据
     */
    List<Map<String, Object>> loadSpecGeneralAlarmStatByDate(Map<String, Object> params);

    /**
     * 查询工单统计信息
     *
     * @param params 查询参数
     * @return 工单统计信息
     */
    List<Map<String, Object>> querySpecGeneralStat(Map<String, Map<String, Object>> params);

    /**
     * 查询工单详情信息
     *
     * @param dto 查询参数
     * @return 工单详情信息
     * @throws Exception
     */
    List<Map<String, Object>> queryDetailInfo(QueryDetailDto dto) throws Exception;

    /**
     * 填充0
     *
     * @param dataList 数据
     */
    void fillZeroIntoMap(List<Map<String, Object>> dataList);

    /**
     * 按照specId排序
     *
     * @param dataList 数据
     */
    void sortBySpecId(List<Map<String, Object>> dataList);

    /**
     * 统计
     *
     * @param dataList 数据
     * @param flag     标识
     */
    void setTotalData(List<Map<String, Object>> dataList, boolean flag);

    /**
     * 无线网小区自动派单的告警统计SQL（去掉子告警和工程告警）
     *
     * @return 结果值
     */
    int findCountNum();
}
