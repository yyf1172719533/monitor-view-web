package com.timain.monitor.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/5 09:13
 */
@ApiModel("告警窗口页面查询对象")
@Data
public class AlarmViewDto implements Serializable {

    private static final long serialVersionUID = -8997250249983640789L;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("地域ID")
    private String areaId;
}
