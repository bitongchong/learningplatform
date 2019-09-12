package com.sicau.platform.service.impl;

import com.sicau.platform.dao.StudyRecordDao;
import com.sicau.platform.dao.UserDetailDao;
import com.sicau.platform.entity.*;
import com.sicau.platform.enums.RecordStatusEnum;
import com.sicau.platform.enums.TimeEnum;
import com.sicau.platform.util.IdGenerator;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author boot liu
 */
@Service
public class StudyRecordService {
    private final StudyRecordDao studyRecordDao;
    private final HostHolder hostHolder;
    private final PunchInRecordService punchInRecordService;
    private final UserDetailDao userDetailDao;
    @Value("${read_time}")
    Integer timeNeedToRead;

    public StudyRecordService(StudyRecordDao studyRecordDao, HostHolder hostHolder, PunchInRecordService punchInRecordService, UserDetailDao userDetailDao) {
        this.studyRecordDao = studyRecordDao;
        this.hostHolder = hostHolder;
        this.punchInRecordService = punchInRecordService;
        this.userDetailDao = userDetailDao;
    }

    public void initRecord(Article article) {
        StudyRecord findRecord = studyRecordDao.findRecord(article.getArticleId(), hostHolder.getUser().getUserId());
        if (findRecord != null) {
            findRecord.setOpenTime(new Date());
            studyRecordDao.save(findRecord);
            return;
        }
        StudyRecord record = new StudyRecord();
        record.setOpenTime(new Date());
        record.setRecordId(IdGenerator.nextId());
        record.setArticleId(article.getArticleId());
        record.setUserId(hostHolder.getUser().getUserId());
        record.setArticleTitle(article.getTitle());
        record.setStatus(RecordStatusEnum.UN_FINISH.getCode());
        record.setAccomplishTime(new Date());
        studyRecordDao.save(record);
    }

    public StudyRecord findRecord(Long articleId) {
        return studyRecordDao.findOneByUserIdAndArticleId(hostHolder.getUser().getUserId(), articleId);
    }

    public Result finishRecord(StudyRecord studyRecord) throws Exception {
        Date now = new Date();
        if (studyRecord == null) {
            return new Result(true, StatusCode.ARTICLEUNREAD, "请先点击文章进行阅读");
        }
        if (studyRecord.getStatus().equals(RecordStatusEnum.FINISH.getCode())) {
            return new Result(false, StatusCode.ARTICLREADEDED, "本篇文章已经学习过了");
        }
        studyRecord.setStatus(RecordStatusEnum.FINISH.getCode());
        if (now.getTime() - studyRecord.getOpenTime().getTime() < timeNeedToRead * TimeEnum.ONE_MINITE.getTime()) {
            return new Result(false, StatusCode.ARTICLRUNFINISH, "阅读时间不够");
        }
        studyRecord.setAccomplishTime(now);
        studyRecord.setUserName(getUserName(hostHolder.getUser().getUserId()));
        studyRecordDao.save(studyRecord);
        if (punchInRecordService.canPunchIn()) {
            boolean punchInResult = punchInRecordService.punchIn();
            if (!punchInResult) {
                throw new Exception("打卡失败");
            }
        }
        return new Result(true, StatusCode.OK, "本篇文章学习完成");
    }

    private String getUserName(Long sid) {
        UserDetail userDetail = userDetailDao.findBySid(sid);
        if (ObjectUtils.isNotEmpty(userDetail)) {
            return userDetail.getName();
        } else {
            return null;
        }
    }

    public Page<StudyRecord> getStudyRecord(int size, int page) {
        Long userId = hostHolder.getUser().getUserId();
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "accomplishTime");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Specification<StudyRecord> specification = (Specification<StudyRecord>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userId").as(Long.class), userId);
        return studyRecordDao.findAll(specification, pageRequest);
    }
}
