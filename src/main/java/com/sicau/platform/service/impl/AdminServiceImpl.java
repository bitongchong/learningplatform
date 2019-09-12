package com.sicau.platform.service.impl;

import com.sicau.platform.dao.AdminDao;
import com.sicau.platform.dao.UserDao;
import com.sicau.platform.entity.Admin;
import com.sicau.platform.entity.User;
import com.sicau.platform.service.IAdminService;
import com.sicau.platform.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author boot liu
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class AdminServiceImpl implements IAdminService {
    private final AdminDao adminDao;
    private final UserDao userDao;

    @Autowired
    public AdminServiceImpl(AdminDao adminDao, UserDao userDao) {
        this.adminDao = adminDao;
        this.userDao = userDao;
    }

    @Override
    public boolean regist(Admin admin) {
        if (adminDao.getByAccount(admin.getAccount()) != null) {
            return false;
        }
        admin.setAdminId(IdGenerator.nextId());
        adminDao.save(admin);
        User adminUser = User.builder().userId(admin.getAdminId()).
                account(admin.getAccount()).password(admin.getPassword()).build();
        adminUser.setUserId(admin.getAdminId());
        adminUser.setAccount(admin.getAccount());
        adminUser.setPassword(admin.getPassword());
        userDao.save(adminUser);
        return true;
    }
}
