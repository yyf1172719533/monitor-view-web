package com.timain.monitor.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 窗口配置查询对象
 * @author yyf
 * @version 1.0
 * @date 2022/11/8 19:17
 */
@Data
public class QueryAlarmConfDto implements Serializable {

    private static final long serialVersionUID = -3351121847244484322L;

    private String creator;

    private String monitorViewName;

    private String viewId;

    private String windowId;

    private String windowUniqueKey;

    private boolean addWin = false;
}
