package com.sicau.platform.service;

import com.sicau.platform.dao.AdminDao;
import com.sicau.platform.dao.PunchInRecordDao;
import com.sicau.platform.dao.UserDetailDao;
import com.sicau.platform.entity.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    HostHolder hostHolder;
    @Autowired
    PunchInRecordDao punchInRecordDao;
    @Autowired
    AdminDao adminDao;
    @Autowired
    UserDetailDao userDetailDao;
    @Value("${punch_in_number}")
    Integer punchInNumber;

    /**
     * 判断是否能够打卡
     *
     * @return
     */
    public boolean canPunchIn() {
        Long userId = hostHolder.getUser().getUserid();
        if (!isPunchIn()) {
            Integer readAritcleNumber = punchInRecordDao.findReadAritcleNumber(userId);
            return readAritcleNumber >= punchInNumber;
        }
        return false;
    }

    /**
     * 打卡接口
     *
     * @return
     */
    boolean punchIn() {
        User user = hostHolder.getUser();
        PunchInRecord punchInRecord = PunchInRecord.builder().punchInTime(new Date()).status(1).userId(user
                .getUserid()).userName(getUserName(user.getUserid())).build();
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
     *
     * @return
     */
    public boolean isPunchIn() {
        Long userId = hostHolder.getUser().getUserid();
        Integer allByUserId = punchInRecordDao.findTodayPunchInRecord(userId);
        return allByUserId.equals(1);
    }

    public Page<PunchInRecord> getPunchInRecord(int size, int page) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "punchInTime");
        Long userId = hostHolder.getUser().getUserid();
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
