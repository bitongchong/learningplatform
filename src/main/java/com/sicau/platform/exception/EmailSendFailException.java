package com.sicau.platform.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liuyuehe
 * @description
 * @date 2019/8/12
 */
@Getter
@Setter
@AllArgsConstructor
public class EmailSendFailException extends Exception {
    public String message;
}