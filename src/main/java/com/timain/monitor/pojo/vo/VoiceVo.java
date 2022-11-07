package com.timain.monitor.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 声音配置对象
 * @author yyf
 * @version 1.0
 * @date 2022/11/6 15:21
 */
@Data
public class VoiceVo implements Serializable {

    private static final long serialVersionUID = 6229951251536135311L;

    /**
     * 登陆账号
     */
    private String accLoginname;

    /**
     * 专业id
     */
    private String specId;

    /**
     * 告警等级
     */
    private String severity;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 描述
     */
    private String descr;
}
