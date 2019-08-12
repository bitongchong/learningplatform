package com.sicau.platform.service;

import com.sicau.platform.dao.LoginTicketDao;
import com.sicau.platform.dao.PasswordTokenDao;
import com.sicau.platform.dao.UserDao;
import com.sicau.platform.dao.UserDetailDao;
import com.sicau.platform.entity.*;
import com.sicau.platform.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    private final UserDao userDao;
    private final UserDetailDao userDetailDao;
    private final LoginTicketDao loginTicketDao;
    private final HostHolder hostHolder;
    private final PasswordTokenDao passwordTokenDao;

    @Autowired
    public UserService(UserDao userDao, UserDetailDao userDetailDao, LoginTicketDao loginTicketDao, HostHolder hostHolder, PasswordTokenDao passwordTokenDao) {
        this.userDao = userDao;
        this.userDetailDao = userDetailDao;
        this.loginTicketDao = loginTicketDao;
        this.hostHolder = hostHolder;
        this.passwordTokenDao = passwordTokenDao;
    }

    public User findByUserAccount(String account) {
        return userDao.findByAccount(account);
    }

    public Boolean addUser(User user) {
        user.setUserid(IdGenerator.nextId());
        UserDetail userDetail = new UserDetail();
        userDetail.setSid(user.getUserid());
        // 初始化一个学生详细信息
        addUserDetail(userDetail);
        return userDao.save(user) == null ? false : true;
    }

    public boolean addUserDetail(UserDetail userDetail) {
        userDetail.setDetailid(IdGenerator.nextId());
        return userDetailDao.save(userDetail) == null ? false : true;
    }

    /**
     * 将前端传过来的UserDetail设置好detail的id，然后直接更新
     *
     * @param userDetail
     * @return 是否成功添加
     */
    public boolean updateUserDetail(UserDetail userDetail) throws Exception {
        userDetail.setDetailid(getUserDetail(hostHolder.getUser().getUserid()).getDetailid());
        userDetail.setSid(hostHolder.getUser().getUserid());
        return userDetailDao.save(userDetail) == null ? false : true;
    }

    /**
     * 在更新学生详细信息时，先查找userdetail表中当前学生的信息id
     *
     * @param sid 学生id
     * @return 返回学生信息细节实例
     */
    public UserDetail getUserDetail(Long sid) {
        return userDetailDao.findBySid(sid);
    }

    public boolean changeStatus(String ticket) {
        LoginTicket findTicket = loginTicketDao.findByTicket(ticket);
        findTicket.setStatus(1);
        loginTicketDao.save(findTicket);
        return true;
    }

    public String findEmailByUserId(Long userId) {
        UserDetail userDetail = userDetailDao.findBySid(userId);
        if (Objects.isNull(userDetail.getEmail())) {
            return null;
        } else {
            return userDetail.getEmail();
        }
    }

    public boolean addPasswordToken(PasswordToken token) {
        PasswordToken save = passwordTokenDao.save(token);
        return !Objects.isNull(save);
    }

    public boolean initPassword(String account) {
        User user = userDao.findByAccount(account);
        user.setPassword("00000000");
        User save = userDao.save(user);
        return !Objects.isNull(save);
    }

    public PasswordToken findPasswordToken(Long token) {
        PasswordToken passwordToken = passwordTokenDao.findByToken(token);
        // 代表token已经使用过
        passwordToken.setStatus(1);
        passwordTokenDao.save(passwordToken);
        return passwordToken;
    }

    public boolean changePassword(String newPassword) {
        User user = hostHolder.getUser();
        user.setPassword(newPassword);
        User save = userDao.save(user);
        return !Objects.isNull(save);
    }

    public String addTicket(Long userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setId(IdGenerator.nextId());
        ticket.setUserId(userId);
        ticket.setStatus(0);
        Date now = new Date();
        now.setTime(24 * 60 * 60 * 7 * 1000 + now.getTime());
        ticket.setExpired(now);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDao.save(ticket);
        return ticket.getTicket();
    }
}
