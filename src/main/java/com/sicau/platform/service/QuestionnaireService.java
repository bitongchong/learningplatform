package com.sicau.platform.service;

import com.sicau.platform.dao.QuertionnaireRecordDao;
import com.sicau.platform.dao.QuestionnaireDao;
import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.entity.Questionnaire;
import com.sicau.platform.entity.QuestionnaireRecord;
import com.sicau.platform.entity.User;
import com.sicau.platform.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QuestionnaireService {
    @Autowired
    QuestionnaireDao questionnaireDao;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    QuertionnaireRecordDao quertionnaireRecordDao;

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


    public boolean addQuertionnaireRecords(QuestionnaireRecord questionnaireRecord) {

        quertionnaireRecordDao.save(questionnaireRecord);
        return true;
    }


    public Page<QuestionnaireRecord> getAllQuestionnaireRecordsByPage(int size, int page) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "testtime");
        PageRequest of = PageRequest.of(page, size, sort);

        return quertionnaireRecordDao.findAll(of);
    }
}
