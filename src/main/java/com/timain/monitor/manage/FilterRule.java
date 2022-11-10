package com.timain.monitor.manage;

import com.google.common.collect.Maps;
import com.timain.monitor.mapper.AlarmSetMapper;
import com.timain.monitor.service.impl.RuleSetServiceImpl;

import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/9 16:04
 */
public class FilterRule extends AbstractRule {

    public FilterRule(AlarmSetMapper alarmSetMapper) {
        super(alarmSetMapper, new RuleSetServiceImpl());
    }

    /**
     * 解析规则内容
     *
     * @param ruleContext 规则内容
     * @return 解析后的数据
     */
    @Override
    public Map<String, Map<String, String>> parseRuleContext(String ruleContext) {
        Map<String, Map<String, String>> result = Maps.newHashMap();
        if (isNull(ruleContext)) {
            LOGGER.error("规则内容为空!");
            return result;
        }
        Map<String, String> tm;
        String[] rcs = ruleContext.split("\\)\\&\\(");
        if (rcs.length > 1) {
            int len = rcs.length;
            for (int i = 0; i < len; i++) {
                if (i == 0) {
                    rcs[i] = rcs[i] + rb;
                } else if (i == len - 1) {
                    rcs[i] = lb + rcs[i];
                } else {
                    rcs[i] = lb + rcs[i] + rb;
                }
            }
        }
        for (String r : rcs) {
            if (r.startsWith(lb)) {
                r = r.substring(1);
            }
            if (r.endsWith(rb)) {
                r = r.substring(0, r.length() - 1);
            }
            String[] nodes;
            if (r.contains("&")) {
                nodes = r.split(and);
            } else {
                nodes = r.split(or);
            }
            for (String node : nodes) {
                node = node.trim();
                int cnt = 0;
                String op_non = "";
                for (int i = 0, size = node.length(); i < size; i++) {
                    char c = node.charAt(i);
                    String k = null;
                    if (c == eq && node.charAt(i - 1) != per) {
                        // 以第一次出现=后面的作为节点值
                        if (cnt == 0) {
                            k = node.substring(0, i);
                            // 如果是逻辑非则key需要去掉!
                            if (k.endsWith("!")) {
                                k = k.substring(0, k.length() - 1);
                                op_non = "!";
                            }
                            tm = result.get(k);
                            if (tm == null) {
                                tm = Maps.newHashMap();
                                result.put(k, tm);
                            }
                            String v = node.substring(i + 1);
                            if (v.startsWith(dsq)) {
                                v = v.substring(1, v.length() - 1);
                            }
                            tm.put(v, cc);
                            // 节点操作符
                            tm.put(v + "_oper", op_non + "=");
                        }
                        cnt++;
                    } else if (c == per && node.charAt(i + 1) == eq) {
                        // 以第一次出现%=后面的作为节点值
                        if (cnt == 0) {
                            k = node.substring(0, i);
                            // 如果是逻辑非则key需要去掉!
                            if (k.endsWith("!")) {
                                k = k.substring(0, k.length() - 1);
                                op_non = "!";
                            }
                            tm = result.get(k);
                            if (tm == null) {
                                tm = Maps.newHashMap();
                                result.put(k, tm);
                            }
                            String v = node.substring(i + 2);
                            if (v.startsWith(dsq)) {
                                v = v.substring(1, v.length() - 1);
                            }
                            tm.put(v, cc);
                            tm.put(v + "_oper", op_non + "%=");
                        }
                        cnt++;
                    }
                }
            }
        }
        return result;
    }
}
