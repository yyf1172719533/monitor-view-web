package com.timain.monitor.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 窗口配置信息表单对象
 * @author yyf
 * @version 1.0
 * @date 2022/11/9 19:06
 */
@ApiModel("窗口配置信息表单对象")
@Data
public class WinFormDto implements Serializable {

    private static final long serialVersionUID = -8114368809905709694L;

    private Integer windowId;

    private String windowName;

    private String windowDesc;

    private Integer windowEnable;

    private String windowUniqueKey;

    private String monitorViewName;

    private Integer loadCustAlarm;

    private String childViewName;

    private String displayColumns;

    private String alarmStatuses;

    private Integer preloadType;

    private Integer preloadPara;

    private String ruleIds;

    private String toolbars;

    private String winSyn;

    private String viewName;

    private Integer viewId;

    private String creator;

    private long createTime;

    private Integer windowNum;

    private Integer isPublic;
}
