package com.sicau.platform.service;

import com.sicau.platform.dao.AdminDao;
import com.sicau.platform.dao.UserDao;
import com.sicau.platform.entity.Admin;
import com.sicau.platform.entity.User;
import com.sicau.platform.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author boot liu
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class AdminService {
    private final AdminDao adminDao;
    private final UserDao userDao;

    @Autowired
    public AdminService(AdminDao adminDao, UserDao userDao) {
        this.adminDao = adminDao;
        this.userDao = userDao;
    }

    public boolean regist(Admin admin) {

        if (adminDao.getByAccount(admin.getAccount()) != null) {
            return false;
        }
        admin.setAdminId(IdGenerator.nextId());
        adminDao.save(admin);
        User adminUser = User.builder().userid(admin.getAdminId()).
                account(admin.getAccount()).password(admin.getPassword()).build();
        adminUser.setUserid(admin.getAdminId());
        adminUser.setAccount(admin.getAccount());
        adminUser.setPassword(admin.getPassword());
        userDao.save(adminUser);
        return true;
    }
}
