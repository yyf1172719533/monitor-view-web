package com.timain.monitor.handler;

import com.timain.monitor.exception.BusinessException;
import com.timain.monitor.exception.NoAuthException;
import com.timain.monitor.pojo.DataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 19:58
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoAuthException.class)
    public DataResult authExceptionHandler(NoAuthException e) {
        LOGGER.error("NoAuthException: {}", e.getMessage());
        return DataResult.buildFail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public DataResult businessExceptionHandler(BusinessException e) {
        LOGGER.error("BusinessException: {}", e.getMessage());
        return DataResult.buildFail(e.getCode(), e.getMessage());
    }
}
