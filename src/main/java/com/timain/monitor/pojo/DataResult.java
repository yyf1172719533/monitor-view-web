package com.timain.monitor.pojo;

import lombok.Data;

/**
 * 数据返回的Code码
 *
 * @author zhurq
 * @Date 2022-10-8
 * @Des
 */

@Data
public class DataResult {
    private Integer code;
    private String message;
    private boolean success;
    private Object data;

    public DataResult(Integer code, String message, boolean success, Object data) {
        super();
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public DataResult() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static DataResult buildSuccess() {
        return new DataResult(DataResultCode.success_code, "成功", true, null);
    }

    public static DataResult buildSuccess(Object t) {
        return new DataResult(DataResultCode.success_code, "成功", true, t);
    }

    public static <T> DataResult buildSuccess(String msg, T t) {
        return new DataResult(DataResultCode.success_code, msg, true, t);
    }

    public static DataResult buildSuccess(Integer code, String msg, Object t) {
        return new DataResult(code, msg, true, t);
    }

    public static DataResult buildFail() {
        return new DataResult(DataResultCode.error_code, "返回失败", false, null);
    }

    public static DataResult buildFail(String msg) {
        return new DataResult(DataResultCode.error_code, msg, false, null);
    }

    public static DataResult buildFail(Integer code, String msg) {
        return new DataResult(code, msg, false, null);
    }

    public static DataResult buildFail(String msg, Object data) {
        return new DataResult(DataResultCode.error_code, msg, false, data);
    }

    public static DataResult buildFail(Integer code, String msg, Object obj) {
        return new DataResult(code, msg, false, obj);
    }

    public static DataResult buildByInter(Integer curdRes) {
        if (curdRes > 0) {
            return buildSuccess();
        }
        return buildFail();
    }


    public static DataResult buildByStr(String res) {
        if (res == null) {
            return buildSuccess();
        }
        return buildFail(res);
    }

}

