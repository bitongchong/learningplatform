package com.sicau.platform.exception;

import lombok.*;

/**
 * @author liuyuehe
 * @description 用户相关异常
 * @date 2019/8/12
 */
@Getter
@Setter
@AllArgsConstructor
public class UserLoginException extends Exception {
    public String message;
}
