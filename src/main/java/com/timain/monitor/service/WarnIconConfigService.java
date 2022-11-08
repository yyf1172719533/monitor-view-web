package com.timain.monitor.service;

import com.timain.monitor.pojo.vo.WarnIconVo;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 15:48
 */
public interface WarnIconConfigService {

    /**
     * 查询告警图标列表
     * @return 告警图标
     */
    Map<String, List<WarnIconVo>> queryWarnIconList();
}
