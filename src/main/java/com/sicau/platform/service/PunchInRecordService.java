package com.sicau.platform.service;

import com.sicau.platform.dao.PunchInRecordDao;
import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.entity.PunchInRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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
    @Value("${punch_in_number}")
    Integer punchInNumber;

    /**
     * 判断是否能够打卡
     * @return
     */
    public boolean canPunchIn() {
        Long userId = hostHolder.getUser().getUserid();
        if (!isPunchIn()){
            Integer readedAritcleNumber = punchInRecordDao.findReadedAritcleNumber(userId);
            return readedAritcleNumber >= punchInNumber;
        }
        return false;
    }

    /**
     * 打卡接口
     * @return
     */
    public boolean punchIn() {
        PunchInRecord punchInRecord = PunchInRecord.builder().punchInTime(new Date()).status(1).userId(hostHolder.getUser()
                .getUserid()).build();
        return Objects.nonNull(punchInRecordDao.save(punchInRecord));
    }

    /**
     * 今日是否打卡
     * @return
     */
    public boolean isPunchIn() {
        Long userId = hostHolder.getUser().getUserid();
        Integer allByUserId = punchInRecordDao.findTodayPunchInRecord(userId);
        return allByUserId.equals(1);
    }
}
