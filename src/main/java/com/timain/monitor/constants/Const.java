package com.timain.monitor.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 17:27
 */
public final class Const {

    /**
     * 是否打开窗口关键字
     */
    static final public String IS_OPEN_WIN = "yes";
    /**
     * null字符串值
     */
    static final public String NULL = "null";
    /**
     * 空字符串
     */
    static final public String EMPTY = "";
    /**
     * session中当前用户关键字
     */
    static final public String ACC_LOGIN_NAME = "acc_loginname";
    /**
     * 逗号
     */
    static final public String CA = ",";
    /**
     * 单引号
     */
    static final public String SQ = "'";
    /**
     * 冒号
     */
    static final public String MQ = ":";
    /**
     * 错误数据
     */
    static final public String NA = "NODATA";
    /**
     * 时间模版
     */
    static final public String TP = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认用户
     */
    static final public String DEFAULT_CREATOR = "-1";
    /**
     * 窗口信息关键字
     */
    static final public String KEY_WI = "windowinfo";
    /**
     * 窗口信息关键字对应描述
     */
    static final public String KEY_WI_LABEL = "窗口信息";
    /**
     * 子窗口关键字
     */
    static final public String KEY_CW = "childwindow";
    /**
     * 子窗口关键字对应描述
     */
    static final public String KEY_CW_LABEL = "子窗口";
    /**
     * 展示列关键字
     */
    static final public String KEY_DS = "ta_displaycolumn";
    /**
     * 展示列关键字对应描述
     */
    static final public String KEY_DS_LABEL = "展示列设置";
    /**
     * 告警状态关键字
     */
    static final public String KEY_AS = "ta_state_usrdef";
    /**
     * 告警状态关键字对应描述
     */
    static final public String KEY_AS_LABEL = "状态标识设置";
    /**
     * 预装关键字
     */
    static final public String KEY_PL = "ta_preload";
    /**
     * 预装关键字对应描述
     */
    static final public String KEY_PL_LABEL = "预装设置";
    /**
     * 过滤器关键字
     */
    static final public String KEY_F = "ta_windowfilter";
    /**
     * 预装关键字对应描述
     */
    static final public String KEY_F_LABEL = "过滤器";
    /**
     * 工具栏关键字
     */
    static final public String KEY_TB = "ta_toolbardef";
    /**
     * 工具栏关键字对应描述
     */
    static final public String KEY_TB_LABEL = "工具栏";
    /**
     * 当班、自定义、预处理数组
     */
    static final public String[] MKS = {"speccustomrulealarm", "dutyalarm", "sheetprealarm", "inspectalarm"};
    /**
     * 当班、自定义、预处理list
     */
    static final public List<String> MODULE_KEYS = Arrays.asList(MKS);
    /**
     * 子窗口key需符合0~5的任意组合规则
     */
    static final public String CWK_PATTERN = "([0-5],*)+";
}
