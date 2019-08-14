package com.sicau.platform.dao;

import com.sicau.platform.entity.QuestionnaireRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface QuestionnaireRecordDao extends JpaSpecificationExecutor<QuestionnaireRecord>,JpaRepository<QuestionnaireRecord,Long>{
    List<QuestionnaireRecord> findAllByUserId(Long userId);
}
