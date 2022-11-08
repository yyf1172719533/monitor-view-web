package com.timain.monitor.support.builder;

import cn.hutool.core.collection.CollectionUtil;
import com.timain.monitor.constants.KeyConstants;
import com.timain.monitor.enums.ErrorEnum;
import com.timain.monitor.exception.BusinessException;
import com.timain.monitor.exception.CustomException;
import com.timain.monitor.mapper.AlarmTotalFlowMapper;
import com.timain.monitor.pojo.context.ResourceContext;
import com.timain.monitor.pojo.vo.AreaResInfoVo;
import com.timain.monitor.service.AlarmTotalFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 22:18
 */
public abstract class AbstractResourceBuilder {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    protected AreaResInfoVo resInfoVo;

    protected final AlarmTotalFlowMapper alarmTotalFlowMapper;

    protected final AlarmTotalFlowService alarmTotalFlowService;

    protected AbstractResourceBuilder(AreaResInfoVo resInfoVo, AlarmTotalFlowMapper alarmTotalFlowMapper, AlarmTotalFlowService alarmTotalFlowService) {
        this.resInfoVo = resInfoVo;
        this.alarmTotalFlowMapper = alarmTotalFlowMapper;
        this.alarmTotalFlowService = alarmTotalFlowService;
    }

    protected AreaResInfoVo getResInfoVo() {
        try {
             return assembleSpecLabelJson()
                    .assembleViewJson()
                    .assembleMenuJson()
                    .assembleVoiceJson()
                    .assembleViewWinJson()
                    .assembleStateJson()
                    .assembleToolJson()
                    .assembleAlarmColumnJson()
                    .assembleCityJson()
                    .builder()
                    .get();
        } catch (Exception e) {
            if (e instanceof CustomException) {
                return resInfoVo;
            }
            e.printStackTrace();
            LOGGER.error("获取资源数据失败：{}", e.getMessage());
            throw new BusinessException(ErrorEnum.QUERY_RES_INFO_ERROR);
        }
    }

    /**
     * 组装专业json数据
     * @return 专业json
     */
    abstract AbstractResourceBuilder assembleSpecLabelJson();

    /**
     * 组装视图json数据
     * @return 视图json
     */
    abstract AbstractResourceBuilder assembleViewJson();

    /**
     * 组装权限菜单json数据
     * @return 菜单json
     */
    abstract AbstractResourceBuilder assembleMenuJson();

    /**
     * 组装声音配置json数据
     * @return 声音配置json
     */
    abstract AbstractResourceBuilder assembleVoiceJson();

    /**
     * 组装视图窗口json数据
     * @return 窗口json
     */
    abstract AbstractResourceBuilder assembleViewWinJson();

    /**
     * 组装状态栏配置json数据
     * @return 状态栏配置json
     */
    abstract AbstractResourceBuilder assembleStateJson();

    /**
     * 组装工具栏配置json数据
     * @return 工具栏配置json
     */
    abstract AbstractResourceBuilder assembleToolJson();

    /**
     * 组装告警列配置json数据
     * @return 告警列配置json
     */
    abstract AbstractResourceBuilder assembleAlarmColumnJson();

    /**
     * 组装用户属地json数据
     * @return 用户属地json
     */
    abstract AbstractResourceBuilder assembleCityJson();

    protected AbstractResourceBuilder builder() {
        return this;
    }

    /**
     * 获取资源数据
     * @return 资源数据
     */
    abstract AreaResInfoVo get();

    protected void builderParam(List<Map<String, Object>> viewList, ResourceContext context) throws CustomException {
        Map<Object, Object> userViewMap = viewList.stream().filter(e -> viewList.size() != 1 && KeyConstants.DEFAULT_CREATOR.equals(String.valueOf(e.get(KeyConstants.CREATOR))))
                .collect(HashMap::new, (map, view) -> map.put(String.valueOf(view.get(KeyConstants.CREATOR)), view.get(KeyConstants.VIEW_ID)), HashMap::putAll);
        // 退出，要求用户创建
        if (CollectionUtil.isEmpty(viewList)) {
            throw new CustomException();
        }
        String viewId;
        if (userViewMap.size() == 1) {
            // 存在一个视图
            viewId = Optional.ofNullable(userViewMap.values().iterator().next()).orElse("").toString();
        } else if (userViewMap.size() == 2 && userViewMap.containsKey(KeyConstants.DEFAULT_CREATOR)) {
            // 存在两个视图， 一个为默认视图
            userViewMap.remove(KeyConstants.DEFAULT_CREATOR);
            viewId = Optional.ofNullable(userViewMap.values().iterator().next()).orElse("").toString();
        } else if (userViewMap.containsKey(context.getAccount())) {
            // 用户自定义视图
            viewId = Optional.ofNullable(userViewMap.get(context.getAccount())).orElse("").toString();
        } else {
            // 其他情况
            throw new CustomException();
        }
        // 默认视图ID
        String defaultViewId = String.valueOf(userViewMap.get(KeyConstants.DEFAULT_CREATOR));

        context.setViewId(viewId);
        context.setDefaultViewId(defaultViewId);
    }
}
