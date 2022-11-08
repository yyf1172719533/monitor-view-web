package com.timain.monitor.service;

import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 17:49
 */
public interface AlarmSetService {

    /**
     * 查询视图配置数据
     * @param hangToolBar hangToolBar
     * @param monitorViewName monitorViewName
     * @return 视图配置
     */
    Map<String, Object> queryViewSet(String hangToolBar, String monitorViewName);
}
