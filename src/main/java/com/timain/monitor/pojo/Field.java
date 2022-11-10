package com.timain.monitor.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/9 15:38
 */
@Data
public class Field implements Serializable {

    private static final long serialVersionUID = 9077871042919632929L;

    /**
     * 规则属性英文名
     */
    private String fieldCode;
    /**
     * 规则属性中文描述
     */
    private String fieldDesCr;
    /**
     * 规则属性字段类型 0:数字/1:字符串
     */
    private String fieldType = "1";
    /**
     * 规则属性来源 0:字符串分隔/1:sql查询/2:界面手工输入
     */
    private String fieldRs = "2";
    /**
     * 规则属性和值之间的操作符[=、%=]
     */
    private String fieldOper = "%=";
    /**
     * 规则属性和值之间的操作符描述[等于、包含]
     */
    private String fieldOperDesCr = "包含";
    /**
     * 页面需要页面展示的类型key/val
     */
    private String fieldShow = "val";
    /**
     * 规则属性保存规则内容的类型key/val
     */
    private String fieldSave = "val";
    /**
     * 规则属性来源:字符串分隔需要的内容
     */
    private String fieldFixed;
    /**
     * 规则属性来源:对应的sql语句
     */
    private String fieldSql;
    /**
     * 属性是否配置
     */
    private String isConf = "0";
    /**
     * 子节点信息
     */
    private List<Node> nodes = new ArrayList<>();

    public static Field convert(Field field) {
        field.setFieldOperDesCr("=".equals(field.getFieldOper()) ? "等于" : "包含");
        return field;
    }

}
