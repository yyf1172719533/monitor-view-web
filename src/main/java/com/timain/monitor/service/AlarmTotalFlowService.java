package com.timain.monitor.service;

import com.timain.monitor.pojo.dto.AlarmTotalFlowDto;
import com.timain.monitor.pojo.vo.AreaResInfoVo;
import com.timain.monitor.pojo.vo.VoiceVo;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/6 09:25
 */
public interface AlarmTotalFlowService {

    /**
     * 查询告警总流水窗口资源信息
     * @param dto 查询参数
     * @return 资源信息
     * @throws Exception
     */
    AreaResInfoVo queryResourceInfo(AlarmTotalFlowDto dto) throws Exception;

    /**
     * 查询用户定制的视图信息
     * @param dto 查询参数
     * @return 视图信息
     */
    List<Map<String, Object>> queryUserView(AlarmTotalFlowDto dto);

    /**
     * 查询权限内菜单json数据（xml格式）
     * @param dto 查询参数
     * @return 菜单json
     */
    String queryAreaMenuXml(AlarmTotalFlowDto dto);

    /**
     * 查询声音配置列表
     * @param dto 查询参数
     * @return 声音配置
     */
    List<VoiceVo> queryVoiceConfList(AlarmTotalFlowDto dto);

    /**
     * 查询视图下的窗口配置
     * @param viewId 视图ID
     * @param defaultViewId 默认视图ID
     * @return 窗口配置
     */
    List<Map<String, Object>> queryUserViewWin(String viewId, String defaultViewId);

    /**
     * 查询窗口的子窗口配置
     * @param dto 查询参数
     * @return 子窗口配置信息
     */
    List<Map<String, Object>> queryChildWin(AlarmTotalFlowDto dto);

    /**
     * 查询视图下各窗口的状态栏配置
     * @param viewId 视图ID
     * @param defaultViewId 默认视图ID
     * @return 状态栏配置
     */
    List<Map<String, Object>> queryViewWinStateConfig(String viewId, String defaultViewId);

    /**
     * 查询视图下各窗口的工具栏配置
     * @param viewId 视图ID
     * @param defaultViewId 默认视图ID
     * @return 工具栏配置
     */
    List<Map<String, Object>> queryViewWinToolsConfig(String viewId, String defaultViewId);

    /**
     * 查询视图下的列配置
     * @param viewId 视图ID
     * @param defaultViewId 默认视图ID
     * @param moduleKey 窗口key值
     * @return 列配置
     */
    List<Map<String, Object>> queryViewWinColumnConfig(String viewId, String defaultViewId, String moduleKey);

    /**
     * 查询用户属地列表
     * @param dto 查询参数
     * @return 属地列表
     */
    List<Map<String, Object>> queryCity(AlarmTotalFlowDto dto);
}
