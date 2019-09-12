package com.sicau.platform.service.impl;

import com.sicau.platform.dao.AdminDao;
import com.sicau.platform.dao.QuestionnaireDao;
import com.sicau.platform.dao.QuestionnaireRecordDao;
import com.sicau.platform.dao.UserDetailDao;
import com.sicau.platform.entity.*;
import com.sicau.platform.util.IdGenerator;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author liuyuehe
 */
@Service
public class QuestionnaireService {
    private final QuestionnaireDao questionnaireDao;
    private final HostHolder hostHolder;
    private final QuestionnaireRecordDao questionnaireRecordDao;
    private final AdminDao adminDao;
    private final UserDetailDao userDetailDao;

    public QuestionnaireService(QuestionnaireDao questionnaireDao, HostHolder hostHolder, QuestionnaireRecordDao questionnaireRecordDao, AdminDao adminDao, UserDetailDao userDetailDao) {
        this.questionnaireDao = questionnaireDao;
        this.hostHolder = hostHolder;
        this.questionnaireRecordDao = questionnaireRecordDao;
        this.adminDao = adminDao;
        this.userDetailDao = userDetailDao;
    }

    public void addQuestionnaire(String url, String title) {
        Questionnaire questionnaire = Questionnaire.builder().qid(IdGenerator.nextId()).title(title)
                .updateTime(new Date()).url(url).userId(hostHolder.getUser()
                        .getUserId()).build();
        questionnaireDao.save(questionnaire);
    }

    public Page<Questionnaire> getAllQuestionnaireByPage(int size, int page) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        PageRequest of = PageRequest.of(page, size, sort);

        return questionnaireDao.findAll(of);
    }


    public void addQuestionnaireRecords(String resultImgUrl, Long questionnaireId) {
        Optional<Questionnaire> questionnaireDaoById = questionnaireDao.findById(questionnaireId);
        if (!questionnaireDaoById.isPresent()) {
            return;
        }
        String questionnaireTitle = questionnaireDaoById.get().getTitle();
        User user = hostHolder.getUser();
        QuestionnaireRecord questionnaireRecord = QuestionnaireRecord.builder().finishTime(new Date()).recordId(IdGenerator.nextId())
                .resultImgUrl(resultImgUrl).questionnaireTitle(questionnaireTitle).userId(user.getUserId()).status(0)
                .userName(getUserName(user.getUserId())).build();
        questionnaireRecordDao.save(questionnaireRecord);
    }


    public Page<QuestionnaireRecord> getAllQuestionnaireRecordsByPage(int size, int page) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "finishTime");
        Long userId = hostHolder.getUser().getUserId();
        Admin byAdminId = adminDao.getByAdminId(userId);
        if (byAdminId != null) {
            PageRequest of = PageRequest.of(page, size, sort);
            return questionnaireRecordDao.findAll(of);
        } else {
            PageRequest pageRequest = PageRequest.of(page, size, sort);
            Specification<QuestionnaireRecord> specification = (Specification<QuestionnaireRecord>) (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("userId").as(Long.class), userId);
            return questionnaireRecordDao.findAll(specification, pageRequest);
        }
    }

    private String getUserName(Long sid) {
        UserDetail userDetail = userDetailDao.findBySid(sid);
        if (ObjectUtils.isNotEmpty(userDetail)) {
            return userDetail.getName();
        } else {
            return null;
        }
    }

    public boolean changeTheStatus(Long questionnaireRecordId) {
        Optional<QuestionnaireRecord> byId = questionnaireRecordDao.findById(questionnaireRecordId);
        if (byId.isPresent()) {
            QuestionnaireRecord questionnaireRecord = byId.get();
            questionnaireRecord.setStatus(1);
            questionnaireRecordDao.save(questionnaireRecord);
            return true;
        }
        return false;
    }
}
