package com.timain.monitor.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/6 09:28
 */
@ApiModel("告警总流水窗口查询对象")
@Data
public class AlarmTotalFlowDto implements Serializable {

    private static final long serialVersionUID = -1271918266001376699L;

    private String account;

    private String areaId;

    private String moduleKey;

    private String remoteAddr;

    private String uuid;

    private String roleId;

    private String sessionId;

    private String context;

    private String viewId;
}
