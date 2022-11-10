package com.timain.monitor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.timain.monitor.constants.KeyConstants;
import com.timain.monitor.mapper.AlarmTotalFlowMapper;
import com.timain.monitor.pojo.context.ResourceContext;
import com.timain.monitor.pojo.dto.AlarmTotalFlowDto;
import com.timain.monitor.pojo.vo.*;
import com.timain.monitor.service.AlarmTotalFlowService;
import com.timain.monitor.support.factory.ResourceFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/6 09:25
 */
@Service
public class AlarmTotalFlowServiceImpl implements AlarmTotalFlowService {

    @Autowired
    private AlarmTotalFlowMapper alarmTotalFlowMapper;

    /**
     * 查询告警总流水窗口资源信息
     *
     * @param dto 查询对象
     * @return 资源信息
     */
    @Override
    public AreaResInfoVo queryResourceInfo(AlarmTotalFlowDto dto) {
        ResourceContext context = new ResourceContext();
        BeanUtil.copyProperties(dto, context);
        context.setAlarmTotalFlowMapper(alarmTotalFlowMapper);
        context.setAlarmTotalFlowService(this);
        return ResourceFactory.builder(context).getResInfoVo();
    }

    /**
     * 查询用户定制的视图信息
     *
     * @param dto 查询参数
     * @return 视图信息
     */
    @Override
    public List<Map<String, Object>> queryUserView(AlarmTotalFlowDto dto) {
        String account = dto.getAccount();
        String moduleKey = dto.getModuleKey();
        String viewId = dto.getViewId();
        List<Map<String, Object>> mapList;
        // 视图ID不为空则根据视图ID查询
        if (StringUtils.isNotBlank(viewId)) {
            mapList = alarmTotalFlowMapper.selectUserViewByViewId(viewId, moduleKey);
            if (CollectionUtil.isNotEmpty(mapList)) {
                return mapList;
            }
        }
        // 根据用户账号查询
        mapList = alarmTotalFlowMapper.selectUserViewByAccount(account, moduleKey);
        if (CollectionUtil.isNotEmpty(mapList) && mapList.size() == 1) {
            return mapList;
        }
        // 查询未选择的视图
        return alarmTotalFlowMapper.selectUserView(account, moduleKey);
    }

    /**
     * 查询权限内菜单json数据（xml格式）
     *
     * @param dto 查询参数
     * @return 菜单json
     */
    @Override
    public String queryAreaMenuXml(AlarmTotalFlowDto dto) {
        String moduleKey = dto.getModuleKey();
        String roleId = dto.getRoleId();
        List<Map<String, Object>> mapList = alarmTotalFlowMapper.selectMenuConfig(moduleKey, roleId);
        if (CollectionUtil.isEmpty(mapList)) {
            mapList = alarmTotalFlowMapper.selectMenuConfig(moduleKey, "-1");
        }
        List<MenuVo> menuVoList = mapList.stream().map(item -> {
            MenuVo menuVo = new MenuVo();
            menuVo.setName(String.valueOf(item.get(KeyConstants.ITEM_NAME)));
            menuVo.setMultiple(String.valueOf(item.get(KeyConstants.IS_MULTI)));
            menuVo.setIcon(String.valueOf(item.get(KeyConstants.ITEM_PIC_NAME)));
            menuVo.setUpdate(String.valueOf(item.get(KeyConstants.UPDATE_ATTR)));
            menuVo.setType(String.valueOf(item.get(KeyConstants.ITEM_TYPE)));
            menuVo.setAction(String.valueOf(item.get(KeyConstants.ITEM_URL)));
            menuVo.setFilter(String.valueOf(item.get(KeyConstants.ITEM_ACCEPT)));
            return menuVo;
        }).collect(Collectors.toList());
        return JSON.toJSONString(menuVoList);
    }

    /**
     * 查询声音配置列表
     *
     * @param dto 查询参数
     * @return 声音配置
     */
    @Override
    public List<VoiceVo> queryVoiceConfList(AlarmTotalFlowDto dto) {
        List<VoiceVo> voiceVoList = alarmTotalFlowMapper.selectVoiceConfig(dto.getAccount());
        if (CollectionUtil.isEmpty(voiceVoList)) {
            voiceVoList = alarmTotalFlowMapper.selectVoiceConfig("-1");
        }
        return voiceVoList;
    }

    /**
     * 查询视图下的窗口配置
     *
     * @param viewId        视图ID
     * @param defaultViewId 默认视图ID
     * @return 窗口配置
     */
    @Override
    public List<Map<String, Object>> queryUserViewWin(String viewId, String defaultViewId) {
        // 先查询用户已选择的视图配置
        List<Map<String, Object>> mapList = alarmTotalFlowMapper.selectUserViewWin(viewId);
        if (CollectionUtil.isNotEmpty(mapList)) {
            return mapList;
        }
        // 用户未选择，查询默认的视图配置
        if (StringUtils.isNotBlank(defaultViewId)) {
            return alarmTotalFlowMapper.selectUserViewWin(defaultViewId);
        }
        return Lists.newArrayList();
    }

    /**
     * 查询窗口的子窗口配置
     *
     * @param dto 查询参数
     * @return 子窗口配置信息
     */
    @Override
    public List<Map<String, Object>> queryChildWin(AlarmTotalFlowDto dto) {
        return alarmTotalFlowMapper.selectChildWin(dto.getModuleKey());
    }

    /**
     * 查询视图下各窗口的状态栏配置
     *
     * @param viewId        视图ID
     * @param defaultViewId 默认视图ID
     * @return 状态栏配置
     */
    @Override
    public List<Map<String, Object>> queryViewWinStateConfig(String viewId, String defaultViewId) {
        List<Map<String, Object>> winStateConfList = alarmTotalFlowMapper.selectViewWinStateConf(viewId);
        if (CollectionUtil.isNotEmpty(winStateConfList)) {
            return winStateConfList;
        }
        if (StringUtils.isNotBlank(defaultViewId)) {
            return alarmTotalFlowMapper.selectViewWinStateConf(defaultViewId);
        }
        return Lists.newArrayList();
    }

    /**
     * 查询视图下各窗口的工具栏配置
     *
     * @param viewId        视图ID
     * @param defaultViewId 默认视图ID
     * @return 工具栏配置
     */
    @Override
    public List<Map<String, Object>> queryViewWinToolsConfig(String viewId, String defaultViewId) {
        List<Map<String, Object>> toolsConf = alarmTotalFlowMapper.selectViewWinToolsConf(viewId);
        if (CollectionUtil.isNotEmpty(toolsConf)) {
            return toolsConf;
        }
        if (StringUtils.isNotBlank(defaultViewId)) {
            return alarmTotalFlowMapper.selectViewWinToolsConf(defaultViewId);
        }
        return Lists.newArrayList();
    }

    /**
     * 查询视图下的列配置
     *
     * @param viewId        视图ID
     * @param defaultViewId 默认视图ID
     * @param moduleKey     窗口key值
     * @return 列配置
     */
    @Override
    public List<Map<String, Object>> queryViewWinColumnConfig(String viewId, String defaultViewId, String moduleKey) {
        List<Map<String, Object>> columnConf = alarmTotalFlowMapper.selectViewWinColumnConf(viewId, moduleKey);
        if (CollectionUtil.isNotEmpty(columnConf)) {
            return columnConf;
        }
        if (StringUtils.isNotBlank(defaultViewId)) {
            return alarmTotalFlowMapper.selectViewWinColumnConf(defaultViewId, moduleKey);
        }
        return Lists.newArrayList();
    }

    /**
     * 查询用户属地列表
     *
     * @param dto 查询参数
     * @return 属地列表
     */
    @Override
    public List<Map<String, Object>> queryCity(AlarmTotalFlowDto dto) {
        return alarmTotalFlowMapper.selectCity(dto.getAreaId());
    }
}
