package com.timain.monitor.service;

import com.timain.monitor.pojo.dto.QueryAlarmConfDto;
import com.timain.monitor.pojo.dto.WinFormDto;

import java.util.List;
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

    /**
     * 查询功能点列表
     * @param monitorViewName monitorViewName
     * @return 功能点列表
     */
    List<Map<String, Object>> queryFunctionList(String monitorViewName);

    /**
     * 查询用户配置的窗口信息
     * @param creator 创建人
     * @param monitorViewName monitorViewName
     * @return 窗口json
     */
    String queryWin(String creator, String monitorViewName);

    /**
     * 查询窗口配置数据
     * @param dto 查询对象
     * @return 窗口配置
     */
    Map<String, Object> queryWinConf(QueryAlarmConfDto dto);

    /**
     * 校验当前用户选择的功能点是否配置了窗口
     * @param dto 查询对象
     * @return true：是 false：否
     */
    boolean checkWinByUser(QueryAlarmConfDto dto);

    /**
     * 查询指定账号、功能点、窗口的预装配置信息
     * @param dto 查询参数
     * @return 预装配置信息
     */
    Map<String, Object> queryPreInstall(QueryAlarmConfDto dto);

    /**
     * 查询指定账号、功能点、窗口的过滤器信息
     * @param dto 查询参数
     * @return 过滤器信息
     */
    List<Map<String, Object>> queryFilter(QueryAlarmConfDto dto);

    /**
     * 查询过滤器规则树json数据
     * @param ruleId 规则ID
     * @return 规则树json
     */
    String queryFilterRuleJson(String ruleId);

    /**
     * 保存窗口配置信息
     * @param dto 窗口配置对象
     * @return 操作结果行
     */
    int addWin(WinFormDto dto);

    /**
     * 保存当班、自定义窗口配置信息
     * @param dto 窗口配置对象
     * @return 操作结果行
     */
    int saveDutyCustomWin(WinFormDto dto);

    /**
     * 保存关联窗口配置信息
     * @param dto 窗口配置对象
     * @return 操作结果行
     */
    int saveRelationWin(WinFormDto dto);

    /**
     * 保存其他窗口配置信息
     * @param dto 窗口配置对象
     * @return 操作结果行
     */
    int saveOtherWin(WinFormDto dto);

    /**
     * 删除窗口基本信息外的其他配置数据[展示列、告警状态标识、预装、工具栏、过滤器]
     * @param windowId windowId
     * @param winSyn winSyn
     */
    void deleteConfigure(Integer windowId, String winSyn);

    /**
     * 删除窗口基本信息
     * @param windowId windowId
     */
    void deleteWinConfigure(Integer windowId);

    /**
     * 保存除窗口基本信息外的其他配置数据[展示列、告警状态标识、预装、工具栏、过滤器]
     * @param dto 表单对象
     * @param windowId windowId
     * @param viewId viewId
     * @param winSyn winSyn
     */
    void saveConfigure(WinFormDto dto, Integer windowId, Integer viewId, String winSyn);

    /**
     * 保存展示列配置
     * @param dto 表单对象
     * @param windowId windowId
     * @param viewId viewId
     */
    void saveShowColumns(WinFormDto dto, Integer windowId, Integer viewId);

    /**
     * 保存告警状态标识配置
     * @param dto 表单对象
     * @param windowId windowId
     * @param viewId viewId
     */
    void saveAlarmState(WinFormDto dto, Integer windowId, Integer viewId);

    /**
     * 保存预装配置
     * @param dto 表单对象
     * @param windowId windowId
     * @param viewId viewId
     */
    void savePreLoad(WinFormDto dto, Integer windowId, Integer viewId);

    /**
     * 保存工具栏配置
     * @param dto 表单对象
     * @param windowId windowId
     * @param viewId viewId
     */
    void saveToolbars(WinFormDto dto, Integer windowId, Integer viewId);

    /**
     * 保存窗口绑定的过滤器配置
     * @param dto 表单对象
     * @param windowId windowId
     * @param viewId viewId
     */
    void saveFilter(WinFormDto dto, Integer windowId, Integer viewId);

    /**
     * 根据窗口唯一标识查询窗口名称、描述、子窗口key
     * @param windowUniqueKey 窗口唯一标识
     * @return 窗口名称、描述、子窗口key
     */
    Map<String, Object> queryWindowNameByWindowUniqueKey(String windowUniqueKey);

}
