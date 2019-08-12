package com.sicau.platform.dao;

import com.sicau.platform.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDetailDao extends JpaSpecificationExecutor<UserDetail>, JpaRepository<UserDetail, Long> {
    public UserDetail findBySid(Long sid);
    public UserDetail findByIdentity(String id);
}
