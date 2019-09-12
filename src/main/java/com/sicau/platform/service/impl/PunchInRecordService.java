package com.sicau.platform.service.impl;

import com.sicau.platform.dao.AdminDao;
import com.sicau.platform.dao.PunchInRecordDao;
import com.sicau.platform.dao.UserDetailDao;
import com.sicau.platform.entity.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author liuyuehe
 * @description 打卡记录服务
 * @date 2019/8/13
 */
@Service
public class PunchInRecordService {
    private final HostHolder hostHolder;
    private final PunchInRecordDao punchInRecordDao;
    private final AdminDao adminDao;
    private final UserDetailDao userDetailDao;
    @Value("${punch_in_number}")
    Integer punchInNumber;

    public PunchInRecordService(HostHolder hostHolder, PunchInRecordDao punchInRecordDao, AdminDao adminDao, UserDetailDao userDetailDao) {
        this.hostHolder = hostHolder;
        this.punchInRecordDao = punchInRecordDao;
        this.adminDao = adminDao;
        this.userDetailDao = userDetailDao;
    }

    /**
     * 判断是否能够打卡
     * @return -
     */
    boolean canPunchIn() {
        Long userId = hostHolder.getUser().getUserId();
        if (!isPunchIn()) {
            Integer readArticleNumber = punchInRecordDao.findReadAritcleNumber(userId);
            return readArticleNumber >= punchInNumber;
        }
        return false;
    }

    /**
     * 打卡接口
     * @return -
     */
    boolean punchIn() {
        User user = hostHolder.getUser();
        PunchInRecord punchInRecord = PunchInRecord.builder().punchInTime(new Date()).status(1).userId(user
                .getUserId()).userName(getUserName(user.getUserId())).build();
        punchInRecordDao.save(punchInRecord);
        return true;
    }

    private String getUserName(Long sid) {
        UserDetail userDetail = userDetailDao.findBySid(sid);
        if (ObjectUtils.isNotEmpty(userDetail)) {
            return userDetail.getName();
        } else {
            return null;
        }
    }

    /**
     * 今日是否打卡
     * @return -
      */
    public boolean isPunchIn() {
        Long userId = hostHolder.getUser().getUserId();
        Integer allByUserId = punchInRecordDao.findTodayPunchInRecord(userId);
        return allByUserId.equals(1);
    }

    public Page<PunchInRecord> getPunchInRecord(int size, int page) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "punchInTime");
        Long userId = hostHolder.getUser().getUserId();
        Admin byAdminId = adminDao.getByAdminId(userId);
        if (byAdminId != null) {
            PageRequest of = PageRequest.of(page, size, sort);
            return punchInRecordDao.findAll(of);
        } else {
            PageRequest pageRequest = PageRequest.of(page, size, sort);
            Specification<PunchInRecord> specification = (Specification<PunchInRecord>) (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("userId").as(Long.class), userId);
            return punchInRecordDao.findAll(specification, pageRequest);
        }
    }
}
