package com.sicau.platform.entity;

/**
 * @author liuyuehe
 */
public class StatusCode {
    /**
     * 这个异常Code只是简单地做了个约定，每个结果返回都有对应的msg描述
     */
    // 成功
    public static final int OK = 20001;
    // 失败
    public static final int ERROR = 40001;
    // 用户名或密码错误
    public static final int LOGINERROR = 40002;
    // 注册失败，用户存在或输入参数格式错误
    public static final int REGISTERROR = 40003;
    // 信息更新失败，比如当前用户无该文件权限
    public static final int UPDATEERROR = 40004;
    //文章获取或阅读错误
    public static final int ARTICLEERROR = 400005;
    //权限不足
    public static final int ACCESSERROR = 40006;
    //资源访问错误
    public static final int RESOURCEERROR = 40007;
    //用户详细信息获取失败
    public static final int GETINFOEERROR = 40008;
    // 文章未阅读
    public static final int ARTICLEUNREAD = 400009;
    // 文章已阅读
    public static final int ARTICLREADEDED = 400010;
    // 文章阅读时长不够
    public static final int ARTICLRUNFINISH = 400011;
    // 修改密码时，用户未完善email信息
    public static final int EMAILUNKNOWN = 400012;
    // 邮件发送失败
    public static final int EMAILSENDFAIL = 400013;
    // token过期
    public static final int TOKENEXPIRED = 400014;
    // 密码错误
    public static final int PASSWORDERRO = 400015;
    // 未打卡，本日任务未完成
    public static final int MISSION_NOT_COMPLETED = 400016;
    // 重复收藏同一篇文章
    public static final int COLLECTION_STATUS_ERROR = 400017;
    // 重复点赞
    public static final int REPEATE_LIKE = 400018;
    //服务器内部错误（做为全局异常的状态码返回）
    public static final int INTERNALSERVERERROR = 50001;
}