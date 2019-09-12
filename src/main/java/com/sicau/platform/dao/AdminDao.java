package com.sicau.platform.dao;

import com.sicau.platform.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author liuyuehe
 */
public interface AdminDao extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {
    /**
     * 获取通过管理员id来获取管理员相关信息
     * @param adminId 管理员id
     * @return 管理员相关信息
     */
    Admin getByAdminId(Long adminId);
    /**
     * 获取通过管理员账号来获取管理员相关信息
     * @param account 管理员账号
     * @return 管理员相关信息
     */
    Admin getByAccount(String account);
}
