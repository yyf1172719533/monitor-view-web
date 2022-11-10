package com.timain.monitor.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.timain.monitor.manage.AbstractRule;
import com.timain.monitor.mapper.AlarmSetMapper;
import com.timain.monitor.pojo.Field;
import com.timain.monitor.pojo.vo.NodeVo;
import com.timain.monitor.service.RuleSetService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/9 17:00
 */
@Component
public class RuleSetServiceImpl implements RuleSetService, InitializingBean {

    @Autowired
    private AlarmSetMapper alarmSetMapper;

    /**
     * KEY:type
     */
    private Map<Integer, List<Field>> fieldEntry = new HashMap<>(4);
    /**
     * KEY:sql
     */
    private Map<String, List<NodeVo>> nodeEntry = new HashMap<>(20);

    /**
     * 初始化数据
     *
     * @param type 1:过滤器,2:规则设置,3:流水菜单
     */
    @Override
    public void initData(Integer type) {
        List<Field> fieldList = null;
        if (type == FILTER) {
            fieldList = alarmSetMapper.querySupportWarnFields();
            fieldList = fieldList.stream().map(Field::convert).collect(Collectors.toList());
        }
        if (CollectionUtil.isEmpty(fieldList)) {
            return;
        }
        fieldList.forEach(item -> {
            if (AbstractRule.frs_sql == Byte.parseByte(item.getFieldRs())) {
                nodeEntry.put(item.getFieldSql(), alarmSetMapper.queryNodesByFieldSql(item.getFieldSql()));
            }
        });
        fieldEntry.put(type, fieldList);
    }

    /**
     * 根据key查询node节点
     *
     * @param sql key值
     * @return node节点
     */
    @Override
    public List<NodeVo> getNodeList(String sql) {
        return nodeEntry.get(sql);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (int type : TYPES) {
            initData(type);
        }
    }
}
