package com.timain.monitor.support.builder;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.timain.monitor.constants.KeyConstants;
import com.timain.monitor.constants.SysConstants;
import com.timain.monitor.convert.ColumnDataConvert;
import com.timain.monitor.convert.StateDataConvert;
import com.timain.monitor.convert.ToolDataConvert;
import com.timain.monitor.pojo.context.ResourceContext;
import com.timain.monitor.pojo.vo.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 21:55
 */
public class ResourceBuilder extends AbstractResourceBuilder {

    private ResourceContext context;

    private ResourceBuilder(ResourceContext context) {
        super(new AreaResInfoVo(), context.getAlarmTotalFlowMapper(), context.getAlarmTotalFlowService());
        this.context = context;
    }

    public static ResourceBuilder getInstance(ResourceContext context) {
        return new ResourceBuilder(context);
    }

    @Override
    public AreaResInfoVo getResInfoVo() {
        return super.getResInfoVo();
    }

    /**
     * 组装专业json数据
     *
     * @return 专业json
     */
    @Override
    public ResourceBuilder assembleSpecLabelJson() {
        List<SpecVo> specList = alarmTotalFlowMapper.selectSpecList();
        Map<Object, Object> dataMap = specList.stream().collect(HashMap::new, (map, specVo) -> map.put(specVo.getSpecId(), specVo.getSpecName()), HashMap::putAll);
        resInfoVo.setSpecLabelMap(JSON.toJSONString(dataMap));
        return this;
    }

    /**
     * 组装视图json数据
     *
     * @return 视图json
     */
    @Override
    public ResourceBuilder assembleViewJson() {
        List<Map<String, Object>> viewList = alarmTotalFlowService.queryUserView(context);
        List<ViewVo> voList = viewList.stream().filter(e -> viewList.size() == 1 || !KeyConstants.DEFAULT_CREATOR.equals(String.valueOf(e.get(KeyConstants.CREATOR))))
                .map(item -> new ViewVo(String.valueOf(item.get(KeyConstants.VIEW_ID)), String.valueOf(item.get(KeyConstants.VIEW_NAME)),
                        String.valueOf(item.get(KeyConstants.MONITOR_VIEW_NAME)), String.valueOf(item.get(KeyConstants.CREATOR)),
                        String.valueOf(item.get(KeyConstants.WINDOW_NUM)))).collect(Collectors.toList());
        resInfoVo.setViewJson(JSON.toJSONString(voList));

        builderParam(viewList, context);
        return this;
    }

    /**
     * 组装权限菜单json数据
     *
     * @return 菜单json
     */
    @Override
    public ResourceBuilder assembleMenuJson() {
        String menuXml = alarmTotalFlowService.queryAreaMenuXml(context);
        resInfoVo.setMenuJson(menuXml);
        return this;
    }

    /**
     * 组装声音配置json数据
     *
     * @return 声音配置json
     */
    @Override
    public ResourceBuilder assembleVoiceJson() {
        List<VoiceVo> voiceVos = alarmTotalFlowService.queryVoiceConfList(context);
        List<Map<String, Object>> mapList = voiceVos.stream().map(item -> {
            Map<String, Object> map = Maps.newHashMap();
            map.put(KeyConstants.LEVEL, item.getSeverity());
            map.put(KeyConstants.FILE, SysConstants.VOICE_RESOURCE_PATH + "/" + item.getFileName());
            return map;
        }).collect(Collectors.toList());
        resInfoVo.setVoiceConfigJson(JSON.toJSONString(mapList));
        return this;
    }

    /**
     * 组装视图窗口json数据
     *
     * @return 窗口json
     */
    @Override
    public ResourceBuilder assembleViewWinJson() {
        // 查询用户选择的视图或默认视图下的窗口配置信息
        List<Map<String, Object>> viewWinList = alarmTotalFlowService.queryUserViewWin(context.getViewId(), context.getDefaultViewId());
        // 查询窗口对应的子窗口配置信息
        List<Map<String, Object>> childWinList = alarmTotalFlowService.queryChildWin(context);
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
        resInfoVo.setWinJson(JSON.toJSONString(winVoList));
        return this;
    }

    /**
     * 组装状态栏配置json数据
     *
     * @return 状态栏配置json
     */
    @Override
    public ResourceBuilder assembleStateJson() {
        List<Map<String, Object>> winStateConfigs = alarmTotalFlowService.queryViewWinStateConfig(context.getViewId(), context.getDefaultViewId());
        StateDataConvert stateDataConvert = new StateDataConvert(new StateVo(), winStateConfigs);
        Map<String, List<StateVo>> stateMap = stateDataConvert.convertDataToMap(KeyConstants.WINDOW_ID);
        resInfoVo.setStateJson(JSON.toJSONString(stateMap));
        return this;
    }

    /**
     * 组装工具栏配置json数据
     *
     * @return 工具栏配置json
     */
    @Override
    public ResourceBuilder assembleToolJson() {
        List<Map<String, Object>> toolsConfigs = alarmTotalFlowService.queryViewWinToolsConfig(context.getViewId(), context.getDefaultViewId());
        ToolDataConvert toolDataConvert = new ToolDataConvert(new ToolBarVo(), toolsConfigs);
        Map<String, List<ToolBarVo>> toolMap = toolDataConvert.convertDataToMap(KeyConstants.WINDOW_ID);
        resInfoVo.setToolJson(JSON.toJSONString(toolMap));
        return this;
    }

    /**
     * 组装告警列配置json数据
     *
     * @return 告警列配置json
     */
    @Override
    public ResourceBuilder assembleAlarmColumnJson() {
        List<Map<String, Object>> columnConfigs = alarmTotalFlowService.queryViewWinColumnConfig(context.getViewId(), context.getDefaultViewId(), context.getModuleKey());
        ColumnDataConvert columnDataConvert = new ColumnDataConvert(new ColumnVo(), columnConfigs);
        Map<String, List<ColumnVo>> columnMap = columnDataConvert.convertDataToMap(KeyConstants.WINDOW_ID);
        resInfoVo.setAlarmColumnJson(JSON.toJSONString(columnMap));
        return this;
    }

    /**
     * 组装用户属地json数据
     *
     * @return 用户属地json
     */
    @Override
    public ResourceBuilder assembleCityJson() {
        List<Map<String, Object>> cityList = alarmTotalFlowService.queryCity(context);
        resInfoVo.setCityJson(JSON.toJSONString(cityList));
        return this;
    }

    /**
     * 获取资源数据
     *
     * @return 资源数据
     */
    @Override
    public AreaResInfoVo get() {
        return resInfoVo;
    }
}
