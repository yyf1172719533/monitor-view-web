package com.timain.monitor.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜单对象
 * @author yyf
 * @version 1.0
 * @date 2022/11/6 15:00
 */
@Data
public class MenuVo implements Serializable {

    private static final long serialVersionUID = 3598451952818476352L;

    private String name;

    private String multiple;

    private String icon;

    private String update;

    private String type;

    private String action;

    private String filter;
}
