package com.sicau.platform.entity;

import lombok.Data;
import java.util.HashMap;

@Data
public class IndexEntity {
    private boolean flag;
    private Integer code;
    private String message;
    private HashMap data;

}
