package com.timain.monitor.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/5 15:36
 */
@ApiModel("工单详情查询对象")
@Data
public class QueryDetailDto implements Serializable {

    private static final long serialVersionUID = -554324547144421388L;

    private String queryType;

    private String cityName;

    private String startTime;

    private String endTime;

    private String specName;

    private Integer curPage;

    private Integer pageSize;
}
