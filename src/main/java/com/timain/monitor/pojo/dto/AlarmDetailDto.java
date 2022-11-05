package com.timain.monitor.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/5 15:13
 */
@ApiModel("工单统计明细查询对象")
@EqualsAndHashCode(callSuper = true)
@Data
public class AlarmDetailDto extends AlarmViewDto {

    private static final long serialVersionUID = 7334927765288496589L;

    private String xId;

    private String yId;
}
