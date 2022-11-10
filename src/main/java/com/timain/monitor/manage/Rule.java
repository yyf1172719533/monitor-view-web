package com.timain.monitor.manage;

import com.timain.monitor.pojo.Field;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/9 15:58
 */
public interface Rule {

    /**
     * 组装过滤器规则树
     * @param fieldList 规则字段数据
     * @param ruleContext 规则内容
     * @return 规则树
     */
    String assembleRuleTreeData(List<Field> fieldList, String ruleContext);

    /**
     * 解析规则内容
     * @param ruleContext 规则内容
     * @return 解析后的数据
     */
    Map<String, Map<String, String>> parseRuleContext(String ruleContext);
}
