package com.sicau.platform.controller;

import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.entity.Result;
import com.sicau.platform.entity.StatusCode;
import com.sicau.platform.exception.UserLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author liuyuehe
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    HostHolder hostHolder;
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e) {
        if (e instanceof UserLoginException) {
            logger.info(e.getMessage());
            return new Result(false, StatusCode.ACCESSERROR, e.getMessage());
        }
        logger.error("系统发生异常：" + e);
        return new Result(false, StatusCode.INTERNALSERVERERROR, "服务端执行请求时出错");
    }
}
