package com.timain.monitor.mapper;

import com.timain.monitor.pojo.vo.CityVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 11:52
 */
@Mapper
public interface AlarmViewMapper {

    /**
     * 根据时间统计工单，查询定时统计入库表
     * @param params 查询参数
     * @return 工单信息
     */
    List<Map<String, String>> loadSheetDetailDataByDate(Map<String, Object> params);

    /**
     * 获取权限内本地网属地列表
     * @param areaId 当前用户域id
     * @return
     */
    List<CityVo> getAreaRegionList(String areaId);

    /**
     * 全省各地市监控告警统计列表：活动和清除告警
     * @return 监控告警统计列表
     */
    List<Map<String, Object>> loadSpecCityAlarmStat();

    /**
     * 无线网小区自动派单的告警统计SQL（去掉子告警和工程告警）
     * @param param 查询参数
     * @return 告警统计SQL
     */
    int findCountNum(Map<String, Object> param);

    /**
     * 首页根据时间统计，查询定时统计入库表
     * @param param
     * @return
     */
    List<Map<String, Object>> loadSpecGeneralAlarmStatByDate(Map<String, Object> param);
}
