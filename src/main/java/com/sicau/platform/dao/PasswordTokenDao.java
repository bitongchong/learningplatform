package com.sicau.platform.dao;

import com.sicau.platform.entity.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author liuyuehe
 * @description
 * @date 2019/8/12
 */
public interface PasswordTokenDao extends JpaSpecificationExecutor<PasswordToken>, JpaRepository<PasswordToken, Integer> {
    PasswordToken findByToken(Long token);
}
