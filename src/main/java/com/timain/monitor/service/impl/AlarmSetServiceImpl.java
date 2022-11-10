package com.timain.monitor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.timain.monitor.constants.Const;
import com.timain.monitor.constants.KeyConstants;
import com.timain.monitor.constants.SysConstants;
import com.timain.monitor.exception.BusinessException;
import com.timain.monitor.manage.FilterRule;
import com.timain.monitor.mapper.AlarmSetMapper;
import com.timain.monitor.pojo.Field;
import com.timain.monitor.pojo.dto.AlarmSetDto;
import com.timain.monitor.pojo.dto.QueryAlarmConfDto;
import com.timain.monitor.pojo.dto.WinFormDto;
import com.timain.monitor.service.AlarmSetService;
import com.timain.monitor.utils.DataUtils;
import com.timain.monitor.utils.StringUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 17:49
 */
@Service
public class AlarmSetServiceImpl implements AlarmSetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmSetServiceImpl.class);

    @Autowired
    private AlarmSetMapper alarmSetMapper;

    private final Lock lock = new ReentrantLock();

    /**
     * 查询视图配置数据
     *
     * @param hangToolBar     hangToolBar
     * @param monitorViewName monitorViewName
     * @return 视图配置
     */
    @Override
    public Map<String, Object> queryViewSet(String hangToolBar, String monitorViewName) {
        AlarmSetDto dto = new AlarmSetDto();
        dto.setViewPage(false);
        dto.setHangToolBar(hangToolBar);
        dto.setMonitorViewName(monitorViewName);
        if (dto.isOpenWin()) {
            if (dto.isAddWin()) {
                LOGGER.info("当班、自定义窗口新增窗口");
            } else if (dto.isSpecialWin()) {
                LOGGER.info("点专题监控流水工具图标弹出页面");
            } else if (dto.isMutiView()) {
                LOGGER.info("多视图流水工具图标弹出页面");
            } else {
                LOGGER.info("单视图流水工具图标弹出页面");
            }
        } else {
            // 菜单（展示列、告警状态、工具栏、预装）
            dto.setShowMk(true);
        }
        // 设置功能点列表
        dto.setMks(this.queryFunctionList(monitorViewName));
        DataUtils.setKey(dto);
        return BeanUtil.beanToMap(dto);
    }

    /**
     * 查询功能点列表
     *
     * @param monitorViewName monitorViewName
     * @return 功能点列表
     */
    @Override
    public List<Map<String, Object>> queryFunctionList(String monitorViewName) {
        List<Map<String, Object>> functionList = alarmSetMapper.selectFunctionList();
        boolean b = StringUtil.empty(monitorViewName);
        return functionList.stream().peek(item -> {
            if (item.containsKey(KeyConstants.MK)) {
                boolean flag = b && monitorViewName.equals(String.valueOf(item.get(KeyConstants.MK)));
                item.put(KeyConstants.CONFIG, flag);
            }
        }).collect(Collectors.toList());
    }

    /**
     * 查询用户配置的窗口信息
     *
     * @param creator         创建人
     * @param monitorViewName monitorViewName
     * @return 窗口json
     */
    @Override
    public String queryWin(String creator, String monitorViewName) {
        List<Map<String, Object>> winList = alarmSetMapper.selectWinByCreator(creator, monitorViewName);
        if (CollectionUtil.isEmpty(winList)) {
            LOGGER.error("用户【" + creator + "】对应功能点【" + monitorViewName + "】未创建窗口!");
            creator = Const.DEFAULT_CREATOR;
            winList = alarmSetMapper.selectWinByCreator(creator, monitorViewName);
        }
        List<Map<String, Object>> mapList = winList.stream().peek(item -> {
            for (Map.Entry<String, Object> entry : item.entrySet()) {
                if (StringUtil.empty(entry.getValue())) {
                    entry.setValue(Const.NA);
                }
            }
        }).collect(Collectors.toList());
        return JSON.toJSONString(mapList);
    }

    /**
     * 查询窗口配置数据
     *
     * @param dto 查询对象
     * @return 窗口配置
     */
    @Override
    public Map<String, Object> queryWinConf(QueryAlarmConfDto dto) {
        boolean b = this.checkWinByUser(dto);
        if (!b) {
            LOGGER.error("用户【" + dto.getCreator() + "】未配置窗口信息，查询默认配置信息!");
            dto.setCreator(Const.DEFAULT_CREATOR);
            List<Map<String, Object>> mapList = alarmSetMapper.selectDefaultViewAndWin(dto);
            if (CollectionUtil.isEmpty(mapList)) {
                throw new BusinessException("窗口默认配置信息查询失败", 40012);
            }
            Map<String, Object> map = mapList.get(0);
            dto.setViewId(String.valueOf(map.get(KeyConstants.VIEW_ID)));
            dto.setWindowId(String.valueOf(map.get(KeyConstants.WINDOW_ID)));
        }
        Map<String, Object> winInfo = alarmSetMapper.selectWinInfo(dto);
        // 查询子窗口列表
        List<Map<String, Object>> childWinList = alarmSetMapper.selectDefaultChildWinList(dto);
        DataUtils.setChildWin(winInfo, childWinList);

        // 展示列配置
        List<Map<String, Object>> showColumns = alarmSetMapper.selectShowColumns(dto);
        // 状态标识配置
        List<Map<String, Object>> status = alarmSetMapper.selectStatus(dto);
        // 预装配置
        Map<String, Object> preInstall = this.queryPreInstall(dto);
        // 过滤器
        List<Map<String, Object>> filters = this.queryFilter(dto);
        // 工具栏配置
        List<Map<String, Object>> toolBars = alarmSetMapper.selectToolBar(dto);

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put(Const.KEY_WI, DataUtils.buildNewList(Collections.singletonList(winInfo), SysConstants.WIN_INFO_LIST).get(0));
        resultMap.put(Const.KEY_CW, DataUtils.buildNewMap(winInfo, SysConstants.CHILD_WIN_INFO_LIST));
        resultMap.put(Const.KEY_DS, DataUtils.buildNewList(showColumns, SysConstants.DISPLAY_COLUMN_INFO_LIST));
        resultMap.put(Const.KEY_AS, DataUtils.buildNewList(status, SysConstants.STATE_INFO_LIST));
        resultMap.put(Const.KEY_PL, DataUtils.buildNewList(Collections.singletonList(preInstall), SysConstants.PRE_LOAD_INFO_LIST).get(0));
        resultMap.put(Const.KEY_F, DataUtils.buildNewList(filters, SysConstants.WIN_FILTER_INFO_LIST));
        resultMap.put(Const.KEY_TB, DataUtils.buildNewList(toolBars, SysConstants.TOOL_BAR_INFO_LIST));

        return resultMap;
    }

    /**
     * 校验当前用户选择的功能点是否配置了窗口
     *
     * @param dto 查询对象
     * @return true：是 false：否
     */
    @Override
    public boolean checkWinByUser(QueryAlarmConfDto dto) {
        Integer win = alarmSetMapper.selectCountWin(dto);
        return null != win && win > 0;
    }

    /**
     * 查询指定账号、功能点、窗口的预装配置信息
     *
     * @param dto 查询参数
     * @return 预装配置信息
     */
    @Override
    public Map<String, Object> queryPreInstall(QueryAlarmConfDto dto) {
        List<Map<String, Object>> mapList = alarmSetMapper.selectPreInstall(dto);
        if (CollectionUtil.isNotEmpty(mapList)) {
            return mapList.get(0);
        }
        // 默认预装配置，给当班、自定义窗口提供
        Map<String, Object> map = Maps.newHashMap();
        map.put("pt", 0);
        map.put("pp", 12);
        return map;
    }

    /**
     * 查询指定账号、功能点、窗口的过滤器信息
     *
     * @param dto 查询参数
     * @return 过滤器信息
     */
    @Override
    public List<Map<String, Object>> queryFilter(QueryAlarmConfDto dto) {
        List<Map<String, Object>> filterList = alarmSetMapper.selectFilter(dto);
        List<String> ruleIds = alarmSetMapper.selectRuleIdByViewIdAndWindowId(dto.getViewId(), dto.getWindowId());
        return filterList.stream().peek(item -> {
            String ruleId = Optional.ofNullable(item.get(KeyConstants.RULE_ID)).orElse("").toString();
            item.put(KeyConstants.BINDING, ruleIds.contains(ruleId));
            this.judgeKey(item);
        }).collect(Collectors.toList());
    }

    /**
     * 查询过滤器规则树json数据
     *
     * @param ruleId 规则ID
     * @return 规则树json
     */
    @Override
    public String queryFilterRuleJson(String ruleId) {
        Map<String, Object> ruleMap = alarmSetMapper.selectFilterRuleByRuleId(ruleId);
        if (CollectionUtil.isEmpty(ruleMap)) {
            return StringUtils.EMPTY;
        }
        String ruleContext = DataUtils.parseData(ruleMap);
        List<Field> fieldList = alarmSetMapper.querySupportWarnFields();
        fieldList = fieldList.stream().map(Field::convert).collect(Collectors.toList());
        FilterRule filterRule = new FilterRule(alarmSetMapper);
        return filterRule.assembleRuleTreeData(fieldList, ruleContext);
    }

    /**
     * 保存窗口配置信息
     *
     * @param dto 窗口配置对象
     * @return 操作结果行
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addWin(WinFormDto dto) {
        lock.lock();
        try {
            if (Const.MODULE_KEYS.contains(dto.getMonitorViewName())) {
                // 当班、自定义窗口
                return this.saveDutyCustomWin(dto);
            }
            if (StringUtil.empty(dto.getWindowUniqueKey())) {
                // 关联窗口
                return this.saveRelationWin(dto);
            }
            // 其他窗口[例如总流水]
            return this.saveOtherWin(dto);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 保存当班、自定义窗口配置信息
     *
     * @param dto 窗口配置对象
     * @return 操作结果行
     */
    @Override
    public int saveDutyCustomWin(WinFormDto dto) {
        String viewId;
        if (StringUtil.empty(dto.getViewName())) {
            viewId = alarmSetMapper.selectViewIdByMonitorViewName(dto);
            if (StringUtils.isBlank(viewId)) {
                viewId = alarmSetMapper.selectMaxViewId();
            }
        } else {
            viewId = alarmSetMapper.selectMaxViewId();
        }
        dto.setCreateTime(System.currentTimeMillis() / 1000L);
        dto.setWindowNum(2);
        dto.setIsPublic(0);
        alarmSetMapper.insertView(dto, viewId);
        if (alarmSetMapper.selectCountViewByMonitorViewName(dto) == 0) {
            LOGGER.info("账号：【" + dto.getCreator() + "】，功能点：【" + dto.getMonitorViewName() + "】还未配置默认视图，使用view_id:【" + viewId + "】作为默认视图！！");
            alarmSetMapper.insertViewChoose(dto.getCreator(), dto.getMonitorViewName(), viewId);
        }
        // 先删窗口配置
        if (null != dto.getWindowId() && dto.getWindowId() > -1) {
            this.deleteConfigure(dto.getWindowId(), "");
            this.deleteWinConfigure(dto.getWindowId());
        }
        // 同步[展示列、告警状态标识、预装、工具栏、过滤器]配置数据给该账号该功能点下已经存在的其他的窗口
        if (!StringUtil.empty(dto.getWinSyn())) {
            List<Map<String, Object>> mapList = alarmSetMapper.selectViewIdAndWindowId(dto);
            mapList.forEach(item -> {
                Integer windowId = Integer.valueOf(String.valueOf(item.get(KeyConstants.WINDOW_ID)));
                Integer vId = Integer.valueOf(String.valueOf(item.get(KeyConstants.VIEW_ID)));
                this.deleteConfigure(windowId, dto.getWinSyn());
                this.saveConfigure(dto, windowId, vId, dto.getWinSyn());
            });
        }
        Integer windowId = alarmSetMapper.selectMaxWindowId();
        alarmSetMapper.insertViewWin(-1, windowId, dto);
        saveConfigure(dto, windowId, -1, "");
        return 1;
    }

    /**
     * 保存关联窗口配置信息
     *
     * @param dto 窗口配置对象
     * @return 操作结果行
     */
    @Override
    public int saveRelationWin(WinFormDto dto) {
        // 判断指定账号、功能、窗口唯一标识下是否已经配置过窗口
        List<Map<String, Object>> winConfig = alarmSetMapper.selectWinConfig(dto);
        if (CollectionUtil.isNotEmpty(winConfig)) {
            winConfig.forEach(item -> {
                Integer viewId = Integer.valueOf(String.valueOf(item.get(KeyConstants.VIEW_ID)));
                Integer windowId = Integer.valueOf(String.valueOf(item.get(KeyConstants.WINDOW_ID)));
                this.deleteConfigure(windowId, "");
                this.saveConfigure(dto, windowId, viewId, "");
            });
            return 1;
        }
        // 查询视图是否存在
        String viewId = alarmSetMapper.selectViewIdByMonitorViewName(dto);
        if (StringUtils.isBlank(viewId)) {
            viewId = alarmSetMapper.selectMaxViewId();
            dto.setCreateTime(System.currentTimeMillis() / 1000L);
            dto.setWindowNum(2);
            dto.setIsPublic(0);
            alarmSetMapper.insertViewDef(viewId, dto);
        }
        Integer windowId = alarmSetMapper.selectMaxWindowId();
        this.saveConfigure(dto, windowId, Integer.valueOf(viewId), "");
        final Map<String, Object> map = this.queryWindowNameByWindowUniqueKey(dto.getWindowUniqueKey());
        dto.setWindowName(String.valueOf(map.get(KeyConstants.WINDOW_NAME)));
        dto.setWindowDesc(String.valueOf(map.get(KeyConstants.WINDOW_DESC)));
        dto.setChildViewName(String.valueOf(map.get(KeyConstants.CHILD_VIEW_NAME)));
        dto.setWindowEnable(1);
        alarmSetMapper.insertViewWinDef(Integer.valueOf(viewId), windowId, dto);
        return 1;
    }

    /**
     * 保存其他窗口配置信息
     *
     * @param dto 窗口配置对象
     * @return 操作结果行
     */
    @Override
    public int saveOtherWin(WinFormDto dto) {
        // 判断指定账号、功能点下是否配置过窗口
        List<Map<String, Object>> mapList = alarmSetMapper.selectViewIdAndWindowId(dto);
        if (CollectionUtil.isNotEmpty(mapList)) {
            mapList.forEach(item -> {
                Integer viewId = Integer.valueOf(String.valueOf(item.get(KeyConstants.VIEW_ID)));
                Integer windowId = Integer.valueOf(String.valueOf(item.get(KeyConstants.WINDOW_ID)));
                this.deleteConfigure(windowId, "");
                this.saveConfigure(dto, windowId, viewId, "");
            });
            return 1;
        }
        Integer windowId = alarmSetMapper.selectMaxWindowId();
        String viewId = alarmSetMapper.selectMaxViewId();
        dto.setCreateTime(System.currentTimeMillis() / 1000L);
        dto.setWindowNum(1);
        dto.setIsPublic(0);
        // 新增视图
        alarmSetMapper.insertViewDef(viewId, dto);

        Map<String, Object> map = alarmSetMapper.selectWindowNameAndChildViewName(dto.getMonitorViewName());
        dto.setWindowEnable(1);
        String windowName = String.valueOf(map.get(KeyConstants.WINDOW_NAME));
        String childViewName = String.valueOf(map.get(KeyConstants.CHILD_VIEW_NAME));
        WinFormDto formDto = new WinFormDto();
        BeanUtil.copyProperties(dto, formDto);
        formDto.setWindowName(windowName);
        formDto.setChildViewName(childViewName);
        formDto.setWindowDesc(windowName);
        formDto.setWindowEnable(1);
        formDto.setWindowUniqueKey(null);
        alarmSetMapper.insertViewWinDef(Integer.valueOf(viewId), windowId, formDto);
        this.saveConfigure(dto, windowId, Integer.valueOf(viewId), "");
        return 1;
    }

    /**
     * 删除窗口基本信息外的其他配置数据[展示列、告警状态标识、预装、工具栏、过滤器]
     *
     * @param windowId windowId
     * @param winSyn   winSyn
     */
    @Override
    public void deleteConfigure(Integer windowId, String winSyn) {
        String[] str;
        if (!StringUtil.empty(winSyn)) {
            str = winSyn.split(Const.CA);
        } else {
            str = new String[]{Const.KEY_DS, Const.KEY_AS, Const.KEY_PL, Const.KEY_TB, Const.KEY_F};
        }
        for (String s : str) {
            alarmSetMapper.deleteConfigureByWindowId(windowId, s);
        }
    }

    /**
     * 删除窗口基本信息
     *
     * @param windowId windowId
     */
    @Override
    public void deleteWinConfigure(Integer windowId) {
        alarmSetMapper.deleteWinConfigure(windowId);
    }

    /**
     * 保存除窗口基本信息外的其他配置数据[展示列、告警状态标识、预装、工具栏、过滤器]
     *
     * @param dto      表单对象
     * @param windowId windowId
     * @param viewId   viewId
     * @param winSyn   winSyn
     */
    @Override
    public void saveConfigure(WinFormDto dto, Integer windowId, Integer viewId, String winSyn) {
        if (StringUtil.empty(winSyn)) {
            this.saveShowColumns(dto, windowId, viewId);
            this.saveAlarmState(dto, windowId, viewId);
            this.savePreLoad(dto, windowId, viewId);
            this.saveToolbars(dto, windowId, viewId);
            this.saveFilter(dto, windowId, viewId);
            return;
        }
        String[] str = winSyn.split(Const.CA);
        for (String s : str) {
            if (Const.KEY_DS.equals(s)) {
                this.saveShowColumns(dto, windowId, viewId);
            } else if (Const.KEY_AS.equals(s)) {
                this.saveAlarmState(dto, windowId, viewId);
            } else if (Const.KEY_PL.equals(s)) {
                this.savePreLoad(dto, windowId, viewId);
            } else if (Const.KEY_TB.equals(s)) {
                this.saveToolbars(dto, windowId, viewId);
            } else if (Const.KEY_F.equals(s)) {
                this.saveFilter(dto, windowId, viewId);
            }
        }
    }

    /**
     * 保存展示列配置
     *
     * @param dto      表单对象
     * @param windowId windowId
     * @param viewId   viewId
     */
    @Override
    public void saveShowColumns(WinFormDto dto, Integer windowId, Integer viewId) {
        String[] str = dto.getDisplayColumns().split(Const.CA);
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ta_displaycolumn(window_id,view_id,monitor_viewname,field_name,usetype,sequence) values ");
        List<String> valueList = Lists.newArrayList();
        for (int i = 0; i < str.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("('").append(windowId).append("','").append(viewId).append("','")
                    .append(dto.getMonitorViewName()).append("','")
                    .append(str[i]).append("',").append(0).append(",").append(i).append(")");
            valueList.add(sb.toString());
        }
        sql.append(String.join(",", valueList));
        alarmSetMapper.saveShowColumns(sql.toString());
    }

    /**
     * 保存告警状态标识配置
     *
     * @param dto      表单对象
     * @param windowId windowId
     * @param viewId   viewId
     */
    @Override
    public void saveAlarmState(WinFormDto dto, Integer windowId, Integer viewId) {
        String[] str = dto.getAlarmStatuses().split(Const.CA);
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ta_state_usrdef(view_id,window_id,state_enname,sequence) values ");
        List<String> valueList = Lists.newArrayList();
        for (int i = 0; i < str.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("('").append(viewId).append("','").append(windowId).append("','").append(str[i]).append("',").append(i).append(")");
            valueList.add(sb.toString());
        }
        sql.append(String.join(",", valueList));
        alarmSetMapper.saveAlarmState(sql.toString());
    }

    /**
     * 保存预装配置
     *
     * @param dto      表单对象
     * @param windowId windowId
     * @param viewId   viewId
     */
    @Override
    public void savePreLoad(WinFormDto dto, Integer windowId, Integer viewId) {
        alarmSetMapper.savePreLoad(viewId, windowId, dto);
    }

    /**
     * 保存工具栏配置
     *
     * @param dto      表单对象
     * @param windowId windowId
     * @param viewId   viewId
     */
    @Override
    public void saveToolbars(WinFormDto dto, Integer windowId, Integer viewId) {
        if (StringUtil.empty(dto.getToolbars())) {
            return;
        }
        String[] str = dto.getToolbars().split(Const.CA);
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ta_toolbardef(view_id,window_id,toolbar_enname,sequence) values ");
        List<String> valueList = Lists.newArrayList();
        for (int i = 0; i < str.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("('").append(viewId).append("','").append(windowId).append("','").append(str[i]).append("',").append(i).append(")");
            valueList.add(sb.toString());
        }
        sql.append(String.join(",", valueList));
        alarmSetMapper.saveToolbars(sql.toString());
    }

    /**
     * 保存窗口绑定的过滤器配置
     *
     * @param dto      表单对象
     * @param windowId windowId
     * @param viewId   viewId
     */
    @Override
    public void saveFilter(WinFormDto dto, Integer windowId, Integer viewId) {
        if (StringUtil.empty(dto.getRuleIds())) {
            return;
        }
        String[] str = dto.getRuleIds().split(Const.CA);
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ta_windowfilter(view_id,window_id,rule_id) values ");
        List<String> valueList = Lists.newArrayList();
        for (String id : str) {
            StringBuilder sb = new StringBuilder();
            sb.append("('").append(viewId).append("','").append(windowId).append("','").append(id).append("')");
            valueList.add(sb.toString());
        }
        sql.append(String.join(",", valueList));
        alarmSetMapper.saveFilter(sql.toString());
    }

    /**
     * 根据窗口唯一标识查询窗口名称、描述、子窗口key
     *
     * @param windowUniqueKey 窗口唯一标识
     * @return 窗口名称、描述、子窗口key
     */
    @Override
    public Map<String, Object> queryWindowNameByWindowUniqueKey(String windowUniqueKey) {
        return alarmSetMapper.selectWindowNameByWindowUniqueKey(windowUniqueKey);
    }

    private void judgeKey(Map<String, Object> item) {
        if (item.containsKey(KeyConstants.CREATE_TIME)) {
            try {
                item.put(KeyConstants.CREATE_TIME_LABEL, DateFormatUtils.format(
                        Long.parseLong(String.valueOf(item.get(KeyConstants.CREATE_TIME))) * 1000L, Const.TP));
            } catch (Exception e) {
                item.put(KeyConstants.CREATE_TIME_LABEL, Const.EMPTY);
            }
        }
        if (item.containsKey(KeyConstants.STARTED)) {
            try {
                item.put(KeyConstants.STARTED_LABEL, 0 == Byte.parseByte(String.valueOf(item.get(KeyConstants.STARTED))) ? "否" : "是");
            } catch (Exception e) {
                item.put(KeyConstants.STARTED_LABEL, "未配置");
            }
        }
        if (item.containsKey(KeyConstants.IS_PUBLIC)) {
            try {
                item.put(KeyConstants.IS_PUBLIC_LABEL, 0 == Byte.parseByte(String.valueOf(item.get(KeyConstants.IS_PUBLIC))) ? "否" : "是");
            } catch (Exception e) {
                item.put(KeyConstants.IS_PUBLIC_LABEL, "未配置");
            }
        }
    }
}
