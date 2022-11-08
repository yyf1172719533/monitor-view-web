package com.timain.monitor.controller;

import com.timain.monitor.pojo.DataResult;
import com.timain.monitor.service.AlarmSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 17:42
 */
@Api(description = "视图配置接口管理")
@RestController
@RequestMapping("alarmSet")
public class AlarmSetController {

    @Autowired
    private AlarmSetService alarmSetService;

    @ApiOperation("查询初始化视图配置数据")
    @PostMapping("initViewSet")
    public DataResult initViewSet(@RequestParam String hangToolBar, @RequestParam String monitorViewName) {
        return null;
    }
}
