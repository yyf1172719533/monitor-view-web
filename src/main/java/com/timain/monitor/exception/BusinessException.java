package com.timain.monitor.exception;

import com.timain.monitor.enums.ErrorEnum;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 19:56
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -5080265776199313417L;

    private final Integer code;

    public BusinessException(ErrorEnum errorEnum) {
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
    }

    public BusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
