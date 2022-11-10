package com.timain.monitor.controller;

import cn.hutool.core.bean.BeanUtil;
import com.timain.monitor.constants.AlarmOverviewColumn;
import com.timain.monitor.constants.Columns;
import com.timain.monitor.constants.MonitorAlarmColumn;
import com.timain.monitor.enums.ErrorEnum;
import com.timain.monitor.exception.BusinessException;
import com.timain.monitor.pojo.DataResult;
import com.timain.monitor.pojo.dto.AlarmDetailDto;
import com.timain.monitor.pojo.dto.AlarmViewDto;
import com.timain.monitor.pojo.dto.QueryDetailDto;
import com.timain.monitor.service.AlarmViewService;
import com.timain.monitor.utils.ExportUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 11:50
 */
@Api(description = "告警窗口接口管理")
@RestController
@RequestMapping("alarmView")
public class AlarmViewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmViewController.class);

    @Autowired
    private AlarmViewService alarmViewService;

    @ApiOperation("告警窗口页面初始化数据【未选择时间】")
    @PostMapping("querySheetStatData")
    public DataResult querySheetStatData(@RequestParam String areaId) {
        try {
            return DataResult.buildSuccess("查询成功", alarmViewService.loadSheetStatData(areaId));
        } catch (Exception e) {
            LOGGER.error("告警窗口页面初始化数据失败：{}", e.getMessage());
            throw new BusinessException(ErrorEnum.INIT_ALARM_VIEW_DATA_ERROR);
        }
    }

    /**
     * 告警窗口页面数据查询
     * @param dto 查询参数
     * @return 全专业告警概况表[当日告警]、全省各地市监控告警统计列表[活动全部]
     */
    @ApiOperation("告警窗口页面数据查询【已选择时间】")
    @PostMapping("querySpecGeneralAlarmStatByDate")
    public DataResult querySpecGeneralAlarmStatByDate(@RequestBody AlarmViewDto dto) {
        Map<String, Object> params = BeanUtil.beanToMap(dto);
        return DataResult.buildSuccess("查询成功", alarmViewService.loadSpecGeneralAlarmStatByDate(params));
    }

    @ApiOperation("已派发工单数、在途工单数、已关闭工单数等工单统计明细查看【未选择时间】")
    @PostMapping("querySheetDetailData")
    public DataResult querySheetDetailData(@RequestParam String areaId, @RequestParam String spec, @RequestParam String sheetType) {
        return DataResult.buildSuccess("查询成功", alarmViewService.loadSheetDetailData(areaId, spec, sheetType));
    }

    @ApiOperation("已派发工单数、在途工单数、已关闭工单数等工单统计明细查看【已选择时间】")
    @PostMapping("querySheetDetailDataByDate")
    public DataResult querySheetDetailDataByDate(@RequestBody AlarmDetailDto dto) {
        return DataResult.buildSuccess("查询成功", alarmViewService.loadSheetDetailDataByDate(dto));
    }

    @ApiOperation("根据属地查询工单详情信息")
    @PostMapping("querySheetDetailInfo")
    public DataResult querySheetDetailInfo(@RequestBody QueryDetailDto dto) {
        try {
            return DataResult.buildSuccess("查询成功", alarmViewService.queryDetailInfo(dto));
        } catch (Exception e) {
            LOGGER.error("查询工单详情信息失败：{}", e.getMessage());
            throw new BusinessException(ErrorEnum.QUERY_ALARM_DETAIL_INFO_ERROR);
        }
    }

    /**
     * 导出全专业告警概况表
     * @param dto 查询参数
     * @param response response
     */
    @ApiOperation("导出全专业告警概况表")
    @PostMapping("exportAlarmOverview")
    public void exportAlarmOverview(@RequestBody AlarmViewDto dto, HttpServletResponse response) {
        List<Map<String, Object>> dataList;
        Columns columns = new AlarmOverviewColumn();
        try {
            dataList = alarmViewService.queryAlarmOverview(dto);
            ExportUtil.downloadExcel(columns.getFileName(), columns.getTitles(), columns.getColumns(), dataList, response);
        } catch (Exception e) {
            LOGGER.error("export alarmOverview data error: {}", e.getMessage());
            throw new BusinessException(ErrorEnum.EXPORT_ALARM_OVERVIEW_ERROR);
        }
    }

    /**
     * 导出全省各地市监控告警统计列表
     * @param dto 查询参数
     * @param response response
     */
    @ApiOperation("导出全省各地市监控告警统计列表")
    @PostMapping("exportMonitorAlarmCityInfo")
    public void exportMonitorAlarmCityInfo(@RequestBody AlarmViewDto dto, HttpServletResponse response) {
        List<Map<String, Object>> dataList;
        Columns columns = new MonitorAlarmColumn();
        try {
            dataList = alarmViewService.queryMonitorAlarm(dto);
            ExportUtil.downloadExcel(columns.getFileName(), columns.getTitles(), columns.getColumns(), dataList, response);
        } catch (Exception e) {
            LOGGER.error("export monitorAlarmCity data error: {}", e.getMessage());
            throw new BusinessException(ErrorEnum.EXPORT_MONITOR_ALARM_CITY_ERROR);
        }
    }
}
