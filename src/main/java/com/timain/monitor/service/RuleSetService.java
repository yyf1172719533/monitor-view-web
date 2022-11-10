package com.timain.monitor.service;

import com.timain.monitor.pojo.vo.NodeVo;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/9 16:55
 */
public interface RuleSetService {

    /**
     * 过滤器
     */
    int FILTER = 1;
    /**
     * 规则树
     */
    int RULE = 2;
    /**
     * 流水菜单
     */
    int ITEM = 3;

    int[] TYPES = {FILTER, RULE, ITEM};

    /**
     * 初始化数据
     *
     * @param type 1:过滤器,2:规则设置,3:流水菜单
     */
    void initData(Integer type);

    /**
     * 根据key查询node节点
     *
     * @param sql key值
     * @return node节点
     */
    List<NodeVo> getNodeList(String sql);
}
