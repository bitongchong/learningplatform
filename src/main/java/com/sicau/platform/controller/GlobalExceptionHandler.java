package com.sicau.platform.controller;

import com.sicau.platform.entity.Result;
import com.sicau.platform.entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e) {
        e.printStackTrace();
        if ("未登录".equals(e.getMessage())){
            return new Result(false, StatusCode.ACCESSERROR, "未登录");
        }
        return new Result(false, StatusCode.INTERNALSERVERERROR, "服务端执行请求时出错");
    }
}
