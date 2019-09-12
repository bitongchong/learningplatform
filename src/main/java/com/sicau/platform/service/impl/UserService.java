package com.sicau.platform.service.impl;

import com.sicau.platform.dao.LoginTicketDao;
import com.sicau.platform.dao.UserDao;
import com.sicau.platform.dao.UserDetailDao;
import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.entity.LoginTicket;
import com.sicau.platform.entity.User;
import com.sicau.platform.entity.UserDetail;
import com.sicau.platform.enums.TimeEnum;
import com.sicau.platform.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author boot liu
 */
@Service
public class UserService {
    private final UserDao userDao;
    private final UserDetailDao userDetailDao;
    private final LoginTicketDao loginTicketDao;
    private final HostHolder hostHolder;

    @Autowired
    public UserService(UserDao userDao, UserDetailDao userDetailDao, LoginTicketDao loginTicketDao, HostHolder hostHolder) {
        this.userDao = userDao;
        this.userDetailDao = userDetailDao;
        this.loginTicketDao = loginTicketDao;
        this.hostHolder = hostHolder;
    }

    public User findByUserAccount(String account) {
        return userDao.findByAccount(account);
    }

    public void changeStatus(String ticket) {
        LoginTicket findTicket = loginTicketDao.findByTicket(ticket);
        findTicket.setStatus(1);
        loginTicketDao.save(findTicket);
    }

    public Boolean addUser(User user) {
        user.setUserId(IdGenerator.nextId());
        UserDetail userDetail = new UserDetail();
        userDetail.setSid(user.getUserId());
        // 初始化一个学生详细信息
        addUserDetail(userDetail);
        userDao.save(user);
        return true;
    }

    private void addUserDetail(UserDetail userDetail) {
        userDetail.setDetailid(IdGenerator.nextId());
        userDetailDao.save(userDetail);
    }

    /**
     * 将前端传过来的UserDetail设置好detail的id，然后直接更新
     *
     * @param userDetail -
     * @return 是否成功添加
     */
    public boolean updateUserDetail(UserDetail userDetail) {
        userDetail.setDetailid(getUserDetail(hostHolder.getUser().getUserId()).getDetailid());
        userDetail.setSid(hostHolder.getUser().getUserId());
        userDetailDao.save(userDetail);
        return true;
    }

    /**
     * 在更新学生详细信息时，先查找userDetail表中当前学生的信息id
     *
     * @param sid 学生id
     * @return 返回学生信息细节实例
     */
    public UserDetail getUserDetail(Long sid) {
        return userDetailDao.findBySid(sid);
    }

    public String addTicket(Long userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setId(IdGenerator.nextId());
        ticket.setUserId(userId);
        ticket.setStatus(0);
        Date now = new Date();
        now.setTime(7 * TimeEnum.ONE_DAY.getTime() + now.getTime());
        ticket.setExpired(now);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDao.save(ticket);
        return ticket.getTicket();
    }
}
