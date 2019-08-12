package com.sicau.platform.dao;

import com.sicau.platform.entity.Questionnaire;
import com.sicau.platform.entity.StudyRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestionnaireDao extends JpaSpecificationExecutor<Questionnaire>, JpaRepository<Questionnaire, Long> {


}
