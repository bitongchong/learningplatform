package com.sicau.platform.enums;

/**
 * 时间相关枚举
 * @author boot liu
 */
public enum TimeEnum {
    /**
     * 时间相关
     */
    ONE_MINITE(60),
    ONE_HOUR(60 * 60),
    ONE_DAY(60 * 60 * 24);
    /**
     * 时间数
     */
    private Integer time;
    private TimeEnum(Integer time) {
        this.time = time;
    }
    public Integer getTime() {
        return time;
    }
}
