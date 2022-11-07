package com.timain.monitor.mapper;

import com.timain.monitor.pojo.vo.SpecVo;
import com.timain.monitor.pojo.vo.VoiceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/6 09:25
 */
@Mapper
public interface AlarmTotalFlowMapper {

    /**
     * 获取所有专业列表
     * @return 专业列表
     */
    List<SpecVo> selectSpecList();

    /**
     * 获取用户视图定制信息
     * @param viewId 视图ID
     * @param moduleKey 窗口key值
     * @return 用户视图定制信息
     */
    List<Map<String, Object>> selectUserViewByViewId(@Param("viewId") String viewId, @Param("moduleKey") String moduleKey);

    /**
     * 获取用户已定制视图信息
     * @param account 账户信息
     * @param moduleKey 窗口key值
     * @return 用户已定制视图信息
     */
    List<Map<String, Object>> selectUserViewByAccount(@Param("account") String account, @Param("moduleKey") String moduleKey);

    /**
     * 获取用户视图定制信息
     * @param account 账户信息
     * @param moduleKey 窗口key值
     * @return 获取用户视图定制信息
     */
    List<Map<String, Object>> selectUserView(@Param("account") String account, @Param("moduleKey") String moduleKey);

    /**
     * 查询窗口的菜单配置
     * @param moduleKey 窗口key值
     * @param roleId 角色ID
     * @return 菜单配置
     */
    List<Map<String, Object>> selectMenuConfig(@Param("moduleKey") String moduleKey, @Param("roleId") String roleId);

    /**
     * 查询声音配置列表
     * @param account 账户信息
     * @return 声音配置列表
     */
    List<VoiceVo> selectVoiceConfig(@Param("account") String account);

    /**
     * 查询视图窗口配置信息
     * @param viewId 视图ID
     * @return 窗口配置信息
     */
    List<Map<String, Object>> selectUserViewWin(@Param("viewId") String viewId);

    /**
     * 查询窗口的子窗口配置信息
     * @param moduleKey 窗口key值
     * @return 子窗口配置信息
     */
    List<Map<String, Object>> selectChildWin(@Param("moduleKey") String moduleKey);

    /**
     * 查询视图下各窗口的状态栏配置
     * @param viewId 视图ID
     * @return 状态栏配置
     */
    List<Map<String, Object>> selectViewWinStateConf(@Param("viewId") String viewId);

    /**
     * 查询视图下各窗口的工具栏配置
     * @param viewId 视图ID
     * @return 工具栏配置
     */
    List<Map<String, Object>> selectViewWinToolsConf(@Param("viewId") String viewId);

    /**
     * 查询视图下的列配置
     * @param viewId 视图ID
     * @param moduleKey 窗口key值
     * @return 列配置
     */
    List<Map<String, Object>> selectViewWinColumnConf(@Param("viewId") String viewId, @Param("moduleKey") String moduleKey);

    /**
     * 查询用户属地列表
     * @param areaId 域ID
     * @return 属地列表
     */
    List<Map<String, Object>> selectCity(@Param("areaId") String areaId);
}
