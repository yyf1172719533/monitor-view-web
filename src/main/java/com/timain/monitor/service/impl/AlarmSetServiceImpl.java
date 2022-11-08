package com.timain.monitor.service.impl;

import com.timain.monitor.mapper.AlarmSetMapper;
import com.timain.monitor.pojo.dto.AlarmSetDto;
import com.timain.monitor.service.AlarmSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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
        return null;
    }
}
