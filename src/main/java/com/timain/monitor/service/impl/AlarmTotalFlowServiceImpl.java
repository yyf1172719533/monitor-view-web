package com.timain.monitor.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.timain.monitor.constants.KeyConstants;
import com.timain.monitor.constants.SysConstants;
import com.timain.monitor.convert.ColumnDataConvert;
import com.timain.monitor.convert.StateDataConvert;
import com.timain.monitor.convert.ToolDataConvert;
import com.timain.monitor.mapper.AlarmTotalFlowMapper;
import com.timain.monitor.pojo.dto.AlarmTotalFlowDto;
import com.timain.monitor.pojo.vo.*;
import com.timain.monitor.service.AlarmTotalFlowService;
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
        AreaResInfoVo infoVo = new AreaResInfoVo();
        // 获取所有专业列表
        List<SpecVo> specList = alarmTotalFlowMapper.selectSpecList();
        Map<Object, Object> dataMap = specList.stream().collect(HashMap::new, (map, specVo) -> map.put(specVo.getSpecId(), specVo.getSpecName()), HashMap::putAll);
        infoVo.setSpecLabelMap(JSON.toJSONString(dataMap));
        // 获取视图数据
        List<Map<String, Object>> viewList = this.queryUserView(dto);
        List<ViewVo> voList = viewList.stream().filter(e -> viewList.size() == 1 || !KeyConstants.DEFAULT_CREATOR.equals(String.valueOf(e.get(KeyConstants.CREATOR))))
                .map(item -> new ViewVo(String.valueOf(item.get(KeyConstants.VIEW_ID)), String.valueOf(item.get(KeyConstants.VIEW_NAME)),
                        String.valueOf(item.get(KeyConstants.MONITOR_VIEW_NAME)), String.valueOf(item.get(KeyConstants.CREATOR)),
                        String.valueOf(item.get(KeyConstants.WINDOW_NUM)))).collect(Collectors.toList());
        infoVo.setViewJson(JSON.toJSONString(voList));

        Map<Object, Object> userViewMap = viewList.stream().filter(e -> viewList.size() != 1 && KeyConstants.DEFAULT_CREATOR.equals(String.valueOf(e.get(KeyConstants.CREATOR))))
                .collect(HashMap::new, (map, view) -> map.put(String.valueOf(view.get(KeyConstants.CREATOR)), view.get(KeyConstants.VIEW_ID)), HashMap::putAll);

        // 退出，要求用户创建
        if (CollectionUtil.isEmpty(viewList)) {
            return infoVo;
        }
        String viewId;
        if (userViewMap.size() == 1) {
            // 存在一个视图
            viewId = Optional.ofNullable(userViewMap.values().iterator().next()).orElse("").toString();
        } else if (userViewMap.size() == 2 && userViewMap.containsKey(KeyConstants.DEFAULT_CREATOR)) {
            // 存在两个视图， 一个为默认视图
            userViewMap.remove(KeyConstants.DEFAULT_CREATOR);
            viewId = Optional.ofNullable(userViewMap.values().iterator().next()).orElse("").toString();
        } else if (userViewMap.containsKey(dto.getAccount())) {
            // 用户自定义视图
            viewId = Optional.ofNullable(userViewMap.get(dto.getAccount())).orElse("").toString();
        } else {
            // 其他情况
            return infoVo;
        }

        // 权限内菜单
        String menuXml = this.queryAreaMenuXml(dto);
        infoVo.setMenuJson(menuXml);

        // 声音配置
        List<VoiceVo> voiceVos = this.queryVoiceConfList(dto);
        List<Map<String, Object>> mapList = voiceVos.stream().map(item -> {
            Map<String, Object> map = Maps.newHashMap();
            map.put(KeyConstants.LEVEL, item.getSeverity());
            map.put(KeyConstants.FILE, SysConstants.VOICE_RESOURCE_PATH + "/" + item.getFileName());
            return map;
        }).collect(Collectors.toList());
        infoVo.setVoiceConfigJson(JSON.toJSONString(mapList));

        // 默认视图ID
        String defaultViewId = String.valueOf(userViewMap.get(KeyConstants.DEFAULT_CREATOR));

        // 窗口配置
        // 查询用户选择的视图或默认视图下的窗口配置信息
        List<Map<String, Object>> viewWinList = this.queryUserViewWin(viewId, defaultViewId);
        // 查询窗口对应的子窗口配置信息
        List<Map<String, Object>> childWinList = this.queryChildWin(dto);
        Map<Object, Object> childWinMap = childWinList.stream().collect(HashMap::new, (map, item) ->
                map.put(String.valueOf(item.get(KeyConstants.CHILD_WIN_KEY)), item.get(KeyConstants.CHILD_WIN_NAME)), HashMap::putAll);
        List<WinVo> winVoList = viewWinList.stream().map(item -> {
            String childViewName = Optional.ofNullable(item.get(KeyConstants.CHILD_VIEW_NAME)).orElse("").toString();
            String[] viewNames = childViewName.split(",");
            List<String> winNames = Lists.newArrayList();
            for (String viewName : viewNames) {
                winNames.add(null == childWinMap.get(viewName) ? "" : childWinMap.get(viewName).toString());
            }
            WinVo winVo = new WinVo();
            winVo.setWindowId(String.valueOf(item.get(KeyConstants.WINDOW_ID)));
            winVo.setWindowName(String.valueOf(item.get(KeyConstants.WINDOW_NAME)));
            winVo.setWindowX(String.valueOf(item.get(KeyConstants.WINDOW_X)));
            winVo.setWindowY(String.valueOf(item.get(KeyConstants.WINDOW_Y)));
            winVo.setWindowW(String.valueOf(item.get(KeyConstants.WINDOW_W)));
            winVo.setWindowH(String.valueOf(item.get(KeyConstants.WINDOW_H)));
            winVo.setWindowDepth(String.valueOf(item.get(KeyConstants.WINDOW_DEPTH)));
            winVo.setWindowUniquekey(String.valueOf(item.get(KeyConstants.WINDOW_UNIQUE_KEY)));
            winVo.setLoadCustalarms(String.valueOf(item.get(KeyConstants.LOAD_CUST_ALARMS)));
            winVo.setChildviewname(String.join(",", winNames));
            winVo.setChildviewkey(String.valueOf(item.get(KeyConstants.CHILD_VIEW_NAME)));
            return winVo;
        }).collect(Collectors.toList());
        infoVo.setWinJson(JSON.toJSONString(winVoList));

        // 状态栏配置
        List<Map<String, Object>> winStateConfigs = this.queryViewWinStateConfig(viewId, defaultViewId);
        StateDataConvert stateDataConvert = new StateDataConvert(new StateVo(), winStateConfigs);
        Map<String, List<StateVo>> stateMap = stateDataConvert.convertDataToMap();
        infoVo.setStateJson(JSON.toJSONString(stateMap));

        // 工具栏配置
        List<Map<String, Object>> toolsConfigs = this.queryViewWinToolsConfig(viewId, defaultViewId);
        ToolDataConvert toolDataConvert = new ToolDataConvert(new ToolBarVo(), toolsConfigs);
        Map<String, List<ToolBarVo>> toolMap = toolDataConvert.convertDataToMap();
        infoVo.setToolJson(JSON.toJSONString(toolMap));

        // 告警列配置
        List<Map<String, Object>> columnConfigs = this.queryViewWinColumnConfig(viewId, defaultViewId, dto.getModuleKey());
        ColumnDataConvert columnDataConvert = new ColumnDataConvert(new ColumnVo(), columnConfigs);
        Map<String, List<ColumnVo>> columnMap = columnDataConvert.convertDataToMap();
        infoVo.setAlarmColumnJson(JSON.toJSONString(columnMap));

        // 用户属地
        List<Map<String, Object>> cityList = this.queryCity(dto);
        infoVo.setCityJson(JSON.toJSONString(cityList));

        return infoVo;
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
        List<Map<String, Object>> mapList = null;
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
