package com.sicau.platform.enums;

/**
 * @author liuyuehe
 * @description 文章状态枚举
 * @date 2019/9/12
 */
public enum ArticleTypeEnum {
    // 文章状态
    NOTICE("党内通知", 0),
    DYNAMIC("党建动态", 1),
    IMPORTANT("必学新闻", 2),
    HOT("热点新闻", 3),
    VIDEO("视频", 4);

    String info;
    Integer code;

    private ArticleTypeEnum(String info, Integer code) {
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
