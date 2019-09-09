package com.sicau.platform.service;

import com.sicau.platform.dao.LoginTicketDao;
import com.sicau.platform.dao.PasswordTokenDao;
import com.sicau.platform.dao.UserDao;
import com.sicau.platform.dao.UserDetailDao;
import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.entity.PasswordToken;
import com.sicau.platform.entity.User;
import com.sicau.platform.entity.UserDetail;
import com.sicau.platform.exception.EmailSendFailException;
import com.sicau.platform.util.IdGenerator;
import com.sicau.platform.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author liuyuehe
 * @description
 * @date 2019/8/13
 */
@Service
public class PasswordService {
    @Autowired
    LoginTicketDao loginTicketDao;
    @Autowired
    UserDetailDao userDetailDao;
    @Autowired
    PasswordTokenDao passwordTokenDao;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserDao userDao;
    @Autowired
    MailSender mailSender;

    public String findEmailByUserId(Long userId) {
        UserDetail userDetail = userDetailDao.findBySid(userId);
        if (Objects.isNull(userDetail) && Objects.isNull(userDetail.getEmail())) {
            return null;
        } else {
            return userDetail.getEmail();
        }
    }

    public boolean addPasswordToken(PasswordToken token) {
        PasswordToken save = passwordTokenDao.save(token);
        return !Objects.isNull(save);
    }

    public boolean initPassword(String account, PasswordToken passwordToken) {
        User user = userDao.findByAccount(account);
        user.setPassword("00000000");
        User save = userDao.save(user);
        passwordToken.setStatus(1);
        passwordTokenDao.save(passwordToken);
        return !Objects.isNull(save);
    }

    public PasswordToken findPasswordToken(Long token) {
        PasswordToken passwordToken = passwordTokenDao.findByToken(token);
        return passwordToken;
    }

    public boolean changePassword(String newPassword) {
        User user = hostHolder.getUser();
        user.setPassword(newPassword);
        User save = userDao.save(user);
        return !Objects.isNull(save);
    }

    public void sendTokenEmail(Long userId, String email) throws EmailSendFailException {
        long token = IdGenerator.nextId();
        Date now = new Date();
        now.setTime(10 * 60 * 1000 + now.getTime());
        User userEntity = userDao.findByUserid(userId);

        PasswordToken passwordToken = PasswordToken.builder().token(token).account(userEntity
                .getAccount()).status(0).expired(now).build();
        addPasswordToken(passwordToken);
        boolean sendEmailResult = mailSender.sendEmail(email, "密码找回邮件", token);
        if (!sendEmailResult) {
            throw new EmailSendFailException("邮件发送失败");
        }
    }
}
