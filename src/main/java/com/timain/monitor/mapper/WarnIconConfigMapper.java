package com.timain.monitor.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 15:49
 */
@Mapper
public interface WarnIconConfigMapper {

    /**
     * 查询告警图标列表
     * @return 告警图标
     */
    List<Map<String, Object>> selectWarnIconList();
}
