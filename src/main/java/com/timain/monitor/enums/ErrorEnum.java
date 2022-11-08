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
    TOPIC_MSG_ERROR(40005, "队列处理消息失败"),
    EXPORT_MONITOR_ALARM_CITY_ERROR(40005, "导出全省各地市监控告警统计列表失败"),
    QUERY_ALARM_DETAIL_INFO_ERROR(40006, "查询工单详情信息失败"),
    QUERY_RES_INFO_ERROR(40007, "查询告警总流水窗口资源信息失败"),
    BEAN_CONVERT_TYPE_ERROR(40008, "bean类型转换异常"),
    INIT_ALARM_VIEW_DATA_ERROR(40009, "告警窗口页面数据初始化失败");

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
