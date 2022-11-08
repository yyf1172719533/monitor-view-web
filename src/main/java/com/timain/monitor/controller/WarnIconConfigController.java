package com.timain.monitor.controller;

import com.timain.monitor.pojo.DataResult;
import com.timain.monitor.service.WarnIconConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 15:46
 */
@Api(description = "告警图标接口管理")
@RestController
@RequestMapping("warnIconConfig")
public class WarnIconConfigController {

    @Autowired
    private WarnIconConfigService warnIconConfigService;

    @ApiOperation("查询告警图标")
    @PostMapping("queryWarnIcon")
    public DataResult queryWarnIcon() {
        return DataResult.buildSuccess("查询成功", warnIconConfigService.queryWarnIconList());
    }
}
