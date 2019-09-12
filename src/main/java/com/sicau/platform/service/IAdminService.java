package com.sicau.platform.service;

import com.sicau.platform.entity.Admin;

/**
 * @author liuyuehe
 * @description
 * @date 2019/9/12
 */
public interface IAdminService {
    /**
     * 管理员注册
     * @param admin -
     * @return -
     */
    boolean regist(Admin admin);
}
