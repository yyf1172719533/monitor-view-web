package com.timain.monitor.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 窗口配置对象
 * @author yyf
 * @version 1.0
 * @date 2022/11/6 16:16
 */
@Data
public class WinVo implements Serializable {

    private static final long serialVersionUID = -6327705747313433011L;

    private String windowId;

    private String windowName;

    private String windowX;

    private String windowY;

    private String windowW;

    private String windowH;

    private String windowDepth;

    private String windowUniquekey;

    private String loadCustalarms;

    private String childviewname;

    private String childviewkey;
}
