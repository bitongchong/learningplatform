package com.sicau.platform.service;

import com.sicau.platform.dao.QuertionnaireRecordDao;
import com.sicau.platform.dao.QuestionnaireDao;
import com.sicau.platform.entity.*;
import com.sicau.platform.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class QuestionnaireService {
    @Autowired
    QuestionnaireDao questionnaireDao;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    IdGenerator idGenerator;
    @Autowired
    QuertionnaireRecordDao quertionnaireRecordDao;
    public boolean addQuestionnaire(Questionnaire questionnaire){
        User user = hostHolder.getUser();
        questionnaire.setUserid(user.getUserid());
        questionnaire.setQid(idGenerator.nextId());

        questionnaireDao.save(questionnaire);

        return  true;

    }

    public Page<Questionnaire> getAllQuestionnaireByPage(int size,int page){
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "updatetime");
        PageRequest of = PageRequest.of(page, size, sort);

        return questionnaireDao.findAll(of);
    }


    public boolean addQuertionnaireRecords(QuestionnaireRecord questionnaireRecord){

        quertionnaireRecordDao.save(questionnaireRecord);
        return true;
    }


    public Page<QuestionnaireRecord> getAllQuestionnaireRecordsByPage(int size,int page){
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "testtime");
        PageRequest of = PageRequest.of(page, size, sort);

        return quertionnaireRecordDao.findAll(of);
    }
}
