package com.timain.monitor.controller;

import com.timain.monitor.enums.ErrorEnum;
import com.timain.monitor.exception.BusinessException;
import com.timain.monitor.pojo.DataResult;
import com.timain.monitor.pojo.dto.AlarmTotalFlowDto;
import com.timain.monitor.pojo.vo.AreaResInfoVo;
import com.timain.monitor.service.AlarmTotalFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/6 09:23
 */
@Api(description = "告警总流水窗口接口管理")
@RestController
@RequestMapping("alarmTotalFlow")
public class AlarmTotalFlowController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmTotalFlowController.class);

    @Autowired
    private AlarmTotalFlowService alarmTotalFlowService;

    @ApiOperation("查询资源信息")
    @PostMapping("queryResourceInfo")
    public DataResult queryResourceInfo(@RequestBody AlarmTotalFlowDto dto) {
        try {
            AreaResInfoVo resInfoVo = alarmTotalFlowService.queryResourceInfo(dto);
            return DataResult.buildSuccess("查询成功", resInfoVo);
        } catch (Exception e) {
            LOGGER.error("查询告警总流水窗口资源信息失败：{}", e.getMessage());
            throw new BusinessException(ErrorEnum.QUERY_RES_INFO_ERROR);
        }
    }
}
