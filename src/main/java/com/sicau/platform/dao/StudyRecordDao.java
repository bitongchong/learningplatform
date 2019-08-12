package com.sicau.platform.dao;

import com.sicau.platform.entity.StudyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudyRecordDao extends JpaSpecificationExecutor<StudyRecord>, JpaRepository<StudyRecord, Long> {
    // 试了一试@Query的用法，还挺好用的，注意要将其设置为 nativeQuery = true，默认为false的，作用是是否将sql语句转化为原始sql语句
    @Query(value = "select * from study_record where article_id = ?1 and userid = ?2", nativeQuery = true)
    public StudyRecord findRecord(Long articleId, Long userid);
/*
    @Query(value = "SELECT * FROM study_record WHERE user_id = ?2 and status = 1 order by updatetime desc limit 0,?1", nativeQuery = true)
    public List<StudyRecord> findByUserid(Integer size, Long userId);*/

    public StudyRecord findOneByUseridAndArticleId(Long articleId, Long userid);
}
