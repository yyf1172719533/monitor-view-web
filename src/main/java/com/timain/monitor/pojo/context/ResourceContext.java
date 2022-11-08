package com.timain.monitor.pojo.context;

import com.timain.monitor.mapper.AlarmTotalFlowMapper;
import com.timain.monitor.pojo.dto.AlarmTotalFlowDto;
import com.timain.monitor.service.AlarmTotalFlowService;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 页面资源数据上下文对象
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 22:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceContext extends AlarmTotalFlowDto {

    private static final long serialVersionUID = -7401561747792427488L;

    private String viewId;

    private String defaultViewId;

    private AlarmTotalFlowMapper alarmTotalFlowMapper;

    private AlarmTotalFlowService alarmTotalFlowService;
}
