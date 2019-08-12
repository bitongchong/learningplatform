package com.sicau.platform.dao;

import com.sicau.platform.entity.LoginTicket;
import com.sicau.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface LoginTicketDao extends JpaSpecificationExecutor<LoginTicket>, JpaRepository<LoginTicket, Long> {
    public LoginTicket findByTicket(String ticket);
}
