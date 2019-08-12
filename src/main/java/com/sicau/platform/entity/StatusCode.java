package com.sicau.platform.entity;

public class StatusCode {
    /**
     * 这个异常Code只是简单地做了个约定，每个结果返回都有对应的msg描述
     */
    public static final int OK = 20001;//成功
    public static final int ERROR = 40001;//失败
    public static final int LOGINERROR = 40002;//用户名或密码错误
    public static final int REGISTERROR = 40003;//注册失败，用户存在或输入参数格式错误
    public static final int UPDATEERROR = 40004;//信息更新失败，比如当前用户无该文件权限
    public static final int ARTICLEERROR = 400005;//文章获取或阅读错误
    public static final int ACCESSERROR = 40006;//权限不足
    public static final int RESOURCEERROR = 40007;//资源访问错误
    public static final int GETINFOEERROR = 40008;//用户详细信息获取失败
    public static final int ARTICLEUNREAD = 400009;// 文章未阅读
    public static final int ARTICLREADEDED = 400010; // 文章已阅读
    public static final int ARTICLRUNFINISH = 400011; // 文章阅读时长不够

    public static final int INTERNALSERVERERROR = 50001; //服务器内部错误（做为全局异常的状态码返回）
}