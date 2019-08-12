package com.sicau.platform.dao;

import com.sicau.platform.entity.PunchInRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author liuyuehe
 */
public interface PunchInTimeDao extends JpaSpecificationExecutor<PunchInRecord>, JpaRepository<PunchInRecord, Long> {
/*    public Integer find*/
}
