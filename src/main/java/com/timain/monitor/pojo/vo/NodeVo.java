package com.timain.monitor.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/9 16:56
 */
@Data
public class NodeVo implements Serializable {

    private static final long serialVersionUID = -8512934300912514214L;

    private String key;

    private String val;
}
