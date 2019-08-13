package com.sicau.platform.dao;

import com.sicau.platform.entity.PunchInRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author liuyuehe
 */
public interface PunchInRecordDao extends JpaSpecificationExecutor<PunchInRecord>, JpaRepository<PunchInRecord, Integer> {
    /**
     * 查询当日阅读完成文章
     * @param userId 用户id
     * @return 用户阅读文章数量
     */
    @Query(value = "SELECT count(*) FROM study_record WHERE status=1 AND DATEDIFF(accomplish_time,NOW())=0 AND userid = ?1", nativeQuery = true)
    public Integer findReadedAritcleNumber(Long userId);

    public Integer findAllByUserId(Long userId);
}
