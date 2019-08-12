package com.sicau.platform.dao;

import com.sicau.platform.entity.Admin;
import com.sicau.platform.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminDao  extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {
    public Admin getByAdminId(Long adminId);
    public Admin getByAccount(String account);
}
