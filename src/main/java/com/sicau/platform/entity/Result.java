package com.sicau.platform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyuehe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private boolean flag;
    private Integer code;
    private String message;
    private Object data;

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
