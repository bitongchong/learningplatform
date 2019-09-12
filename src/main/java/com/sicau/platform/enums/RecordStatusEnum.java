package com.sicau.platform.enums;

/**
 * @author liuyuehe
 */

public enum RecordStatusEnum {
    /**
     * 文章阅读是否完成
     */
    UN_FINISH("未完成", 0),
    FINISH("完成", 1);

    String info;
    Integer code;
    private RecordStatusEnum(String info, Integer code) {
        this.info = info;
        this.code = code;
    }
    public String getInfo() {
        return info;
    }
    public Integer getCode() {
        return code;
    }
}
