package com.sicau.platform.service.impl;

import com.sicau.platform.dao.PasswordTokenDao;
import com.sicau.platform.dao.UserDao;
import com.sicau.platform.dao.UserDetailDao;
import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.entity.PasswordToken;
import com.sicau.platform.entity.User;
import com.sicau.platform.entity.UserDetail;
import com.sicau.platform.enums.TimeEnum;
import com.sicau.platform.exception.EmailSendFailException;
import com.sicau.platform.util.IdGenerator;
import com.sicau.platform.util.MailSender;
import org.springframework.beans.factory.annotation.Value;
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
    private final UserDetailDao userDetailDao;
    private final PasswordTokenDao passwordTokenDao;
    private final HostHolder hostHolder;
    private final UserDao userDao;
    private final MailSender mailSender;
    @Value("{init_password}")
    private String initPassword;

    public PasswordService(UserDetailDao userDetailDao, PasswordTokenDao passwordTokenDao, HostHolder hostHolder,
                           UserDao userDao, MailSender mailSender) {
        this.userDetailDao = userDetailDao;
        this.passwordTokenDao = passwordTokenDao;
        this.hostHolder = hostHolder;
        this.userDao = userDao;
        this.mailSender = mailSender;
    }

    public String findEmailByUserId(Long userId) {
        UserDetail userDetail = userDetailDao.findBySid(userId);
        if (Objects.isNull(userDetail) || Objects.isNull(userDetail.getEmail())) {
            return null;
        } else {
            return userDetail.getEmail();
        }
    }

    private void addPasswordToken(PasswordToken token) {
        passwordTokenDao.save(token);
    }

    public boolean initPassword(String account, PasswordToken passwordToken) {
        User user = userDao.findByAccount(account);
        user.setPassword(initPassword);
        userDao.save(user);
        passwordToken.setStatus(1);
        passwordTokenDao.save(passwordToken);
        return true;
    }

    public PasswordToken findPasswordToken(Long token) {
        return passwordTokenDao.findByToken(token);
    }

    public void changePassword(String newPassword) {
        User user = hostHolder.getUser();
        user.setPassword(newPassword);
        userDao.save(user);
    }

    public void sendTokenEmail(Long userId, String email) throws EmailSendFailException {
        long token = IdGenerator.nextId();
        Date now = new Date();
        now.setTime(10 * TimeEnum.ONE_MINITE.getTime() + now.getTime());
        User userEntity = userDao.findByUserId(userId);

        PasswordToken passwordToken = PasswordToken.builder().token(token).account(userEntity
                .getAccount()).status(0).expired(now).build();
        addPasswordToken(passwordToken);
        boolean sendEmailResult = mailSender.sendEmail(email, "密码找回邮件", token);
        if (!sendEmailResult) {
            throw new EmailSendFailException("邮件发送失败");
        }
    }
}
