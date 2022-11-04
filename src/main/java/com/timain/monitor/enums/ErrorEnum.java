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
    TOPIC_MSG_ERROR(40005, "队列处理消息失败");

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
