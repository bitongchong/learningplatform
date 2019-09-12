package com.sicau.platform.dao;

import com.sicau.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author boot liu
 */
public interface UserDao extends JpaSpecificationExecutor<User>, JpaRepository<User,String> {
    public User findByAccount(String account);
    public User findByUserId(Long id);
}
