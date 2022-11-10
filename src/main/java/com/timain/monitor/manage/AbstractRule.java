package com.timain.monitor.manage;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.timain.monitor.mapper.AlarmSetMapper;
import com.timain.monitor.pojo.Field;
import com.timain.monitor.pojo.Node;
import com.timain.monitor.pojo.vo.NodeVo;
import com.timain.monitor.service.RuleSetService;
import com.timain.monitor.utils.DataUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/9 16:03
 */
public abstract class AbstractRule implements Rule {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    protected final AlarmSetMapper alarmSetMapper;

    protected final RuleSetService ruleSetService;

    protected final static String n_key = "key";
    protected final static String ssc = ";alk;";
    protected final static String scn = ":alk:";
    protected final static byte frs_fix = 0x0;
    public final static byte frs_sql = 0x1;
    protected final static String and = "\\&";
    protected final static String or = "\\|";
    protected final static String dsq = "\"";
    protected final static char eq = '=';
    protected final static char per = '%';
    protected final static String lb = "(";
    protected final static String rb = ")";
    protected final static String cc = "1";
    protected final static String ca = ",";
    protected final static String qk = "{";
    protected final static String hk = "}";
    protected final static String qz = "[";
    protected final static String hz = "]";

    public AbstractRule(AlarmSetMapper alarmSetMapper, RuleSetService ruleSetService) {
        this.alarmSetMapper = alarmSetMapper;
        this.ruleSetService = ruleSetService;
    }

    /**
     * 组装过滤器规则树
     *
     * @param fieldList   规则字段数据
     * @param ruleContext 规则内容
     * @return 规则树
     */
    @Override
    public String assembleRuleTreeData(List<Field> fieldList, String ruleContext) {
        LOGGER.info("开始组装过滤规则树");
        if (CollectionUtil.isEmpty(fieldList)) {
            LOGGER.error("没有字段属性信息!");
            return qz + hz;
        }
        Map<String, Map<String, String>> data = parseRuleContext(ruleContext);
        List<Field> newFieldList = fieldList.stream().peek(item -> assembleField(item, data)).collect(Collectors.toList());
        return JSON.toJSONString(newFieldList);
    }

    protected void assembleField(Field field, Map<String, Map<String, String>> data) {
        convertField(field, data);
        assembleNode(field);
        Map<String, String> map = data.get(field.getFieldCode());
        List<Node> nodeList = field.getNodes();
        if (CollectionUtil.isNotEmpty(nodeList)) {
            nodeList.forEach(node -> setNode(node, field, map));
        } else if (CollectionUtil.isNotEmpty(map)) {
            field.setNodes(getNodeList(map));
            field.getNodes().forEach(node -> setNode(node, field, map));
        }
    }

    private void assembleNode(Field field) {
        byte frs = Byte.parseByte(field.getFieldRs());
        if (frs_fix == frs) {
            setNodeByFixed(field);
            return;
        }
        setNodeBySql(field);
    }

    private void convertField(Field field, Map<String, Map<String, String>> data) {
        field.setIsConf(CollectionUtil.isNotEmpty(data.get(field.getFieldCode())) ? "1" : "0");
    }

    private void setNodeByFixed(Field field) {
        String[] filedStr = field.getFieldFixed().split(ssc);
        List<Node> nodes = field.getNodes();
        for (String s : filedStr) {
            String[] str = s.split(scn);
            Node node = new Node(str[0], str[1], "0");
            nodes.add(node);
        }
    }

    private void setNodeBySql(Field field) {
        List<NodeVo> nodeVoList;
        if (null == ruleSetService) {
            nodeVoList = alarmSetMapper.queryNodesByFieldSql(field.getFieldSql());
        } else {
            nodeVoList = ruleSetService.getNodeList(field.getFieldSql());
        }
        List<Node> nodes = field.getNodes();
        nodeVoList.forEach(item -> {
            Node node = new Node(item.getKey(), item.getVal(), "0");
            nodes.add(node);
        });
    }

    private void setNode(Node node, Field field, Map<String, String> map) {
        boolean isConf = checkNodeStatus(node, field, map);
        node.setIsConf(isConf ? "1" : "0");
        if (isConf) {
            node.setNodeOper(map.get(node.getNodeV()) + "_oper");
        }
    }

    private boolean checkNodeStatus(Node node, Field field, Map<String, String> map) {
        if (CollectionUtil.isEmpty(map)) {
            return false;
        }
        if (n_key.equals(field.getFieldSave())) {
            String k = DataUtils.replaceQS(node.getNodeK());
            return StringUtils.isNotBlank(map.get(k));
        }
        String v = DataUtils.replaceQS(node.getNodeV());
        return StringUtils.isNotBlank(map.get(v));
    }

    private List<Node> getNodeList(Map<String, String> map) {
        return map.keySet().stream().map(key -> {
            if (!key.endsWith("_oper")) {
                return new Node(key, key, "1");
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    protected boolean isNull(String s) {
        return null == s || "".equals(s.trim()) || "null".equals(s.trim());
    }
}
