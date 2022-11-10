package com.timain.monitor.mapper;

import com.timain.monitor.pojo.Field;
import com.timain.monitor.pojo.dto.QueryAlarmConfDto;
import com.timain.monitor.pojo.dto.WinFormDto;
import com.timain.monitor.pojo.vo.NodeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 17:54
 */
@Mapper
public interface AlarmSetMapper {

    /**
     * 查询功能点列表
     * @return 功能点列表
     */
    List<Map<String, Object>> selectFunctionList();

    /**
     * 根据登录账号功能点获取窗口信息
     * @param creator creator
     * @param monitorViewName monitorViewName
     * @return 窗口信息
     */
    List<Map<String, Object>> selectWinByCreator(@Param("creator") String creator, @Param("monitorViewName") String monitorViewName);

    /**
     * 统计当前用户配置的窗口数
     * @param dto 查询参数
     * @return 窗口数
     */
    Integer selectCountWin(QueryAlarmConfDto dto);

    /**
     * 查询默认配置的视图和窗口ID
     * @param dto 查询参数
     * @return 视图和窗口ID
     */
    List<Map<String, Object>> selectDefaultViewAndWin(QueryAlarmConfDto dto);

    /**
     * 查询窗口信息
     * @param dto 查询参数
     * @return 窗口信息
     */
    Map<String, Object> selectWinInfo(QueryAlarmConfDto dto);

    /**
     * 查询指定功能点下默认子窗口列表
     * @param dto 查询参数
     * @return 默认子窗口列表
     */
    List<Map<String, Object>> selectDefaultChildWinList(QueryAlarmConfDto dto);

    /**
     * 查询指定账号、功能点、窗口的展示列配置信息
     * @param dto 查询参数
     * @return 展示列配置信息
     */
    List<Map<String, Object>> selectShowColumns(QueryAlarmConfDto dto);

    /**
     * 查询指定账号、功能点、窗口的状态标识配置信息
     * @param dto 查询参数
     * @return 状态标识配置信息
     */
    List<Map<String, Object>> selectStatus(QueryAlarmConfDto dto);

    /**
     * 查询指定账号、功能点、窗口的预装配置信息
     * @param dto 查询参数
     * @return 预装配置信息
     */
    List<Map<String, Object>> selectPreInstall(QueryAlarmConfDto dto);

    /**
     * 查询指定账号、功能点、窗口的过滤器信息
     * @param dto 查询参数
     * @return 过滤器信息
     */
    List<Map<String, Object>> selectFilter(QueryAlarmConfDto dto);

    /**
     * 查询窗口已经绑定的过滤器
     * @param viewId 视图ID
     * @param windowId 窗口ID
     * @return 过滤器ID
     */
    List<String> selectRuleIdByViewIdAndWindowId(@Param("viewId") String viewId, @Param("windowId") String windowId);

    /**
     * 查询指定账号、功能点、窗口的工具栏信息
     * @param dto 查询参数
     * @return 工具栏信息
     */
    List<Map<String, Object>> selectToolBar(QueryAlarmConfDto dto);

    /**
     * 查询过滤器规则树
     * @param ruleId 规则ID
     * @return 规则树
     */
    Map<String, Object> selectFilterRuleByRuleId(@Param("ruleId") String ruleId);

    /**
     * 查询告警过滤器管理支持的告警列
     * @return 告警列
     */
    List<Field> querySupportWarnFields();

    /**
     * 根据数据来源为sql查询对应的子节点数据
     * @param sql sql
     * @return 子节点数据
     */
    List<NodeVo> queryNodesByFieldSql(@Param("sql") String sql);

    /**
     * 查询当前账号功能点下是否存在视图
     * @param dto 查询对象
     * @return 视图ID
     */
    String selectViewIdByMonitorViewName(WinFormDto dto);

    /**
     * 查询视图ID
     * @return 视图ID
     */
    String selectMaxViewId();

    /**
     * 添加视图配置信息
     * @param dto 表单数据
     * @param viewId 视图ID
     * @return 操作结果行
     */
    int insertView(@Param("dto") WinFormDto dto, @Param("viewId") String viewId);

    /**
     * 统计当前账户的功能点是否存在默认视图
     * @param dto 查询对象
     * @return 结果数
     */
    int selectCountViewByMonitorViewName(WinFormDto dto);

    /**
     * 添加当前账户功能点下默认视图
     * @param creator 当前账户
     * @param monitorViewName 功能点
     * @param viewId 视图ID
     */
    void insertViewChoose(@Param("creator") String creator, @Param("monitorViewName") String monitorViewName, @Param("viewId") String viewId);

    /**
     * 删除窗口基本信息
     * @param windowId windowId
     */
    void deleteWinConfigure(@Param("windowId") Integer windowId);

    /**
     * 删除窗口基本信息外的其他配置数据[展示列、告警状态标识、预装、工具栏、过滤器]
     * @param windowId windowId
     * @param tableName tableName
     */
    void deleteConfigureByWindowId(@Param("windowId") Integer windowId, @Param("tableName") String tableName);

    /**
     * 查询视图ID和窗口ID
     * @param dto 查询参数
     * @return 视图ID和窗口ID
     */
    List<Map<String, Object>> selectViewIdAndWindowId(WinFormDto dto);

    /**
     * 保存展示列配置
     * @param sql sql
     */
    void saveShowColumns(@Param("sql") String sql);

    /**
     * 保存告警状态标识配置
     * @param sql sql
     */
    void saveAlarmState(@Param("sql") String sql);

    /**
     * 保存预装配置
     * @param viewId viewId
     * @param windowId windowId
     * @param dto dto
     */
    void savePreLoad(@Param("viewId") Integer viewId, @Param("windowId") Integer windowId, @Param("dto") WinFormDto dto);

    /**
     * 保存工具栏配置
     * @param sql sql
     */
    void saveToolbars(@Param("sql") String sql);

    /**
     * 保存窗口绑定的过滤器配置
     * @param sql sql
     */
    void saveFilter(@Param("sql") String sql);

    /**
     * 查询窗口配置ID
     * @return 窗口配置ID
     */
    Integer selectMaxWindowId();

    /**
     * 保存窗口配置信息
     * @param viewId viewId
     * @param windowId windowId
     * @param dto dto
     */
    void insertViewWin(@Param("viewId") Integer viewId, @Param("windowId") Integer windowId, @Param("dto") WinFormDto dto);

    /**
     * 查询指定账号，功能点，窗口唯一标识下的窗口配置信息
     * @param dto 查询参数
     * @return 窗口配置信息
     */
    List<Map<String, Object>> selectWinConfig(WinFormDto dto);

    /**
     * 保存视图配置
     * @param viewId 视图ID
     * @param dto 表单对象
     */
    void insertViewDef(@Param("viewId") String viewId, @Param("dto") WinFormDto dto);

    /**
     * 根据窗口唯一标识查询窗口名称、描述、子窗口key
     * @param windowUniqueKey 窗口唯一标识
     * @return 窗口名称、描述、子窗口key
     */
    Map<String, Object> selectWindowNameByWindowUniqueKey(@Param("windowUniqueKey") String windowUniqueKey);

    /**
     * 保存窗口配置信息
     * @param viewId viewId
     * @param windowId windowId
     * @param dto dto
     */
    void insertViewWinDef(@Param("viewId") Integer viewId, @Param("windowId") Integer windowId, @Param("dto") WinFormDto dto);

    /**
     * 查询窗口和子视图信息
     * @param monitorViewName monitorViewName
     * @return 窗口和子视图信息
     */
    Map<String, Object> selectWindowNameAndChildViewName(@Param("monitorViewName") String monitorViewName);
}
