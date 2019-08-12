package com.sicau.platform.dao;

import com.sicau.platform.entity.QuestionnaireRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuertionnaireRecordDao extends JpaSpecificationExecutor<QuestionnaireRecord>,JpaRepository<QuestionnaireRecord,Long>{

}
