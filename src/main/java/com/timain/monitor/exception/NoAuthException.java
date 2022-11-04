package com.timain.monitor.exception;

import com.timain.monitor.enums.ErrorEnum;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 19:53
 */
public class NoAuthException extends RuntimeException {

    private static final long serialVersionUID = -3551926783953841458L;

    private final Integer code;

    public NoAuthException(ErrorEnum errorEnum) {
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
