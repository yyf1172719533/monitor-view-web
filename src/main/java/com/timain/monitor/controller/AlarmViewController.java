package com.timain.monitor.controller;

import com.timain.monitor.constants.AlarmOverviewColumn;
import com.timain.monitor.constants.Columns;
import com.timain.monitor.constants.KeyConstants;
import com.timain.monitor.context.LoginContext;
import com.timain.monitor.enums.ErrorEnum;
import com.timain.monitor.exception.BusinessException;
import com.timain.monitor.pojo.DataResult;
import com.timain.monitor.pojo.dto.TimeDto;
import com.timain.monitor.service.AlarmViewService;
import com.timain.monitor.utils.ExportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 11:50
 */
@RestController
@RequestMapping("alarmView")
public class AlarmViewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmViewController.class);

    @Autowired
    private AlarmViewService alarmViewService;

    @PostMapping("querySpecGeneralAlarmStatByDate")
    public DataResult querySpecGeneralAlarmStatByDate(Map<String, Object> params) {

        return null;
    }

    @PostMapping("loadSheetDetailDataByDate")
    public DataResult loadSheetDetailDataByDate(Map<String, Object> params) {
        return DataResult.buildSuccess("查询成功", alarmViewService.loadSheetDetailDataByDate(params));
    }

    /**
     * 导出全专业告警概况表
     * @param dto 查询参数
     */
    @PostMapping("exportAlarmOverview")
    public void exportAlarmOverview(@RequestBody TimeDto dto,  HttpServletResponse response) {
        Object areaId = LoginContext.get(KeyConstants.AREA_ID);
        if (Objects.isNull(areaId)) {
            throw new BusinessException(ErrorEnum.LACK_PARAM);
        }
        List<Map<String, Object>> dataList;
        Columns columns = new AlarmOverviewColumn();
        try {
            dataList = alarmViewService.queryAlarmOverview(dto, String.valueOf(areaId));
            ExportUtil.downloadExcel(columns.getFileName(), columns.getTitles(), columns.getColumns(), dataList, response);
        } catch (Exception e) {
            LOGGER.error("export alarmOverview data error: {}", e.getMessage());
            throw new BusinessException(ErrorEnum.EXPORT_ALARM_OVERVIEW_ERROR);
        }
    }
}
