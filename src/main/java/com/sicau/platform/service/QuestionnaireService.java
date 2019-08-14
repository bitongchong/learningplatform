package com.sicau.platform.service;

import com.sicau.platform.dao.AdminDao;
import com.sicau.platform.dao.QuestionnaireDao;
import com.sicau.platform.dao.QuestionnaireRecordDao;
import com.sicau.platform.entity.Admin;
import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.entity.Questionnaire;
import com.sicau.platform.entity.QuestionnaireRecord;
import com.sicau.platform.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class QuestionnaireService {
    @Autowired
    QuestionnaireDao questionnaireDao;
    @Autowired
    QuestionnaireRecordDao questionnaireRecord;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    QuestionnaireRecordDao questionnaireRecordDao;
    @Autowired
    AdminDao adminDao;

    public boolean addQuestionnaire(String url, String title) {
        Questionnaire questionnaire = Questionnaire.builder().qid(IdGenerator.nextId()).title(title)
                .updatetime(new Date()).url(url).userid(hostHolder.getUser()
                        .getUserid()).build();
        questionnaireDao.save(questionnaire);
        return true;
    }

    public Page<Questionnaire> getAllQuestionnaireByPage(int size, int page) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "updatetime");
        PageRequest of = PageRequest.of(page, size, sort);

        return questionnaireDao.findAll(of);
    }


    public boolean addQuertionnaireRecords(String resultImgUrl, Long questionnaireId) {
        Optional<Questionnaire> questionnaireDaoById = questionnaireDao.findById(questionnaireId);
        if (!questionnaireDaoById.isPresent()) {
            return false;
        }
        String questionnaireTitle = questionnaireDaoById.get().getTitle();
        QuestionnaireRecord questionnaireRecord = QuestionnaireRecord.builder().finishTime(new Date()).recordId(IdGenerator.nextId())
                .resultImgUrl(resultImgUrl).questionnaireTitle(questionnaireTitle).userId(hostHolder.getUser().getUserid()).status(0)
                .build();
        questionnaireRecordDao.save(questionnaireRecord);
        return true;
    }


    public Page<QuestionnaireRecord> getAllQuestionnaireRecordsByPage(int size, int page) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "finishTime");
        // todo 多处使用到管理员权限验证，考虑独立成拦截器或者上权限框架
        Long userId = hostHolder.getUser().getUserid();
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
