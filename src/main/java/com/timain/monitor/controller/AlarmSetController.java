package com.timain.monitor.controller;

import com.timain.monitor.enums.ErrorEnum;
import com.timain.monitor.exception.BusinessException;
import com.timain.monitor.pojo.DataResult;
import com.timain.monitor.pojo.dto.QueryAlarmConfDto;
import com.timain.monitor.pojo.dto.WinFormDto;
import com.timain.monitor.service.AlarmSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 17:42
 */
@Api(description = "视图配置接口管理")
@RestController
@RequestMapping("alarmSet")
public class AlarmSetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmSetController.class);

    @Autowired
    private AlarmSetService alarmSetService;

    @ApiOperation("查询初始化视图配置数据")
    @PostMapping("initViewSet")
    public DataResult initViewSet(@RequestParam String hangToolBar, @RequestParam String monitorViewName) {
        try {
            return DataResult.buildSuccess("查询成功", alarmSetService.queryViewSet(hangToolBar, monitorViewName));
        } catch (Exception e) {
            LOGGER.error("查询初始化视图配置数据失败：{}", e.getMessage());
            throw new BusinessException(ErrorEnum.INIT_VIEW_CONFIG_DATA_ERROR);
        }
    }

    @ApiOperation("根据功能点和账号查询对应的窗口信息")
    @PostMapping("queryWin")
    public DataResult queryWin(@RequestParam String creator, @RequestParam String monitorViewName) {
        try {
            return DataResult.buildSuccess("查询成功", alarmSetService.queryWin(creator, monitorViewName));
        } catch (Exception e) {
            LOGGER.error("根据功能点和账号查询对应的窗口信息失败：{}", e.getMessage());
            throw new BusinessException(ErrorEnum.QUERY_WIN_BY_CREATOR_ERROR);
        }
    }

    @ApiOperation("查询所有窗口配置信息：包括展示列设置、状态标识设置、预装设置、过滤器、工具栏")
    @PostMapping("queryAllWin")
    public DataResult queryAllWin(@RequestBody QueryAlarmConfDto dto) {
        try {
            return DataResult.buildSuccess("查询成功", alarmSetService.queryWinConf(dto));
        } catch (Exception e) {
            LOGGER.error("查询所有窗口配置信息失败：{}", e.getMessage());
            throw new BusinessException(ErrorEnum.QUERY_ALL_WIN_CONFIG_INFO_ERROR);
        }
    }

    @ApiOperation("查询过滤器规则树")
    @PostMapping("queryFilterRule")
    public DataResult queryFilterRule(@RequestParam String ruleId) {
        try {
            return DataResult.buildSuccess("查询成功", alarmSetService.queryFilterRuleJson(ruleId));
        } catch (Exception e) {
            LOGGER.error("查询过滤器规则树失败：{}", e.getMessage());
            throw new BusinessException(ErrorEnum.QUERY_FILTER_RULE_JSON_ERROR);
        }
    }

    @ApiOperation("保存窗口配置")
    @PostMapping("saveWin")
    public DataResult saveWin(@RequestBody WinFormDto dto) {
        return null;
    }
}
