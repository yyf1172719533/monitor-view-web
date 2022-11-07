package com.timain.monitor.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 专业对象
 * @author yyf
 * @version 1.0
 * @date 2022/11/6 09:42
 */
@Data
public class SpecVo implements Serializable {

    private static final long serialVersionUID = 5129983521459452977L;

    /**
     * 专业id
     */
    private String specId;

    /**
     * 专业外部id
     */
    private String specIdEx;

    /**
     * 专业名称
     */
    private String specName;

    /**
     * 排序权重
     */
    private Integer weight;
}
