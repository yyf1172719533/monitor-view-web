package com.timain.monitor.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 14:47
 */
@Data
public class TimeDto implements Serializable {

    private static final long serialVersionUID = -3287707477672146740L;

    private String startTime;

    private String endTime;
}
