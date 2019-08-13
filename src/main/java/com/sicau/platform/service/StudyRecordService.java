package com.sicau.platform.service;

import com.sicau.platform.dao.StudyRecordDao;
import com.sicau.platform.entity.*;
import com.sicau.platform.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author boot liu
 */
@Service
public class StudyRecordService {
    @Autowired
    StudyRecordDao studyRecordDao;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    PunchInRecordService punchInRecordService;
    @Value("${read_time}")
    Integer timeNeedToRead;
    private static final Integer ONE_MINITE = 60 * 1000;

    public boolean initRecord(Article article) {
        StudyRecord findRecord = studyRecordDao.findRecord(article.getArticleId(), hostHolder.getUser().getUserid());
        if (findRecord != null) {
            findRecord.setOpenTime(new Date());
            studyRecordDao.save(findRecord);
            return true;
        }
        StudyRecord record = new StudyRecord();
        record.setOpenTime(new Date());
        record.setRecordId(IdGenerator.nextId());
        record.setArticleId(article.getArticleId());
        record.setUserid(hostHolder.getUser().getUserid());
        record.setArticleTitle(article.getTitle());
        record.setStatus(RecordStatus.UNFINISH);
        record.setAccomplishTime(new Date());
        studyRecordDao.save(record);
        return true;
    }

    public StudyRecord findRecord(Long articleId) {
        return studyRecordDao.findOneByUseridAndArticleId(hostHolder.getUser().getUserid(), articleId);
    }

    public Result finishRecord(StudyRecord studyRecord) throws Exception {
        // 这儿和前面所有的service都可以独立封装出一个专门反会结果的类，来封装result和msg
        HashMap<String, String> map = new HashMap<>();
        Date now = new Date();
        if (studyRecord == null) {
            return new Result(true, StatusCode.ARTICLEUNREAD, "请先点击文章进行阅读");
        }
        if (studyRecord.getStatus() == RecordStatus.FINISH) {
            return new Result(false, StatusCode.ARTICLREADEDED, "本篇文章已经学习过了");
        }
        studyRecord.setStatus(RecordStatus.FINISH);
        // todo 改成配置文件修改，这儿设置为3秒进行测试
        if (now.getTime() - studyRecord.getOpenTime().getTime() <  timeNeedToRead * ONE_MINITE) {
            return new Result(false, StatusCode.ARTICLRUNFINISH, "阅读时间不够");
        }
        studyRecord.setAccomplishTime(now);
        studyRecordDao.save(studyRecord);
        if (punchInRecordService.canPunchIn()) {
            boolean punchInResult = punchInRecordService.punchIn();
            if (!punchInResult) {
                throw new Exception("打卡失败");
            }
        }
        return new Result(true, StatusCode.OK, "本篇文章学习完成");
    }

    public Page<StudyRecord> getStudyRecord(int size, int page) {
        Long userid = hostHolder.getUser().getUserid();
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "accomplishTime");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Specification<StudyRecord> specification = (Specification<StudyRecord>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userid").as(Long.class), hostHolder.getUser().getUserid());
        return studyRecordDao.findAll(specification, pageRequest);
    }

    static class RecordStatus {
        public static int UNFINISH = 0;
        public static int FINISH = 1;
    }
}
