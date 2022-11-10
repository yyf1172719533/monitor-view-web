package com.timain.monitor.enums;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 20:04
 */
public enum ErrorEnum {

    NO_AUTH(40001, "尚未登录"),
    PARAM_ERROR(40002, "非法参数"),
    LACK_PARAM(40003, "缺少参数"),
    EXPORT_ALARM_OVERVIEW_ERROR(40004, "导出全专业告警概况表失败"),
    CORBA_TOPIC_MSG_ERROR(40005, "Corba处理消息失败"),
    EXPORT_MONITOR_ALARM_CITY_ERROR(40005, "导出全省各地市监控告警统计列表失败"),
    QUERY_ALARM_DETAIL_INFO_ERROR(40006, "查询工单详情信息失败"),
    QUERY_RES_INFO_ERROR(40007, "查询告警总流水窗口资源信息失败"),
    BEAN_CONVERT_TYPE_ERROR(40008, "bean类型转换异常"),
    INIT_ALARM_VIEW_DATA_ERROR(40009, "告警窗口页面数据初始化失败"),
    INIT_VIEW_CONFIG_DATA_ERROR(40010, "初始化视图配置数据失败"),
    QUERY_WIN_BY_CREATOR_ERROR(40011, "根据功能点和账号查询对应的窗口信息失败"),
    QUERY_ALL_WIN_CONFIG_INFO_ERROR(40013, "查询所有窗口配置信息失败：包括展示列设置、状态标识设置、预装设置、过滤器、工具栏"),
    QUERY_FILTER_RULE_JSON_ERROR(40014, "查询过滤器规则树失败");

    private final Integer code;

    private final String msg;

    ErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
