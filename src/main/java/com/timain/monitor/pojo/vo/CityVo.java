package com.timain.monitor.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/4 14:44
 */
@Data
public class CityVo implements Serializable {

    private static final long serialVersionUID = -398130321172842443L;

    /**
     * 属地id
     */
    private String cityId;

    /**
     * 属地名称
     */
    private String cityName;

    /**
     * 属地层次
     */
    private int cityLayer;

    /**
     * 排序权重
     */
    private Integer weight;
}
