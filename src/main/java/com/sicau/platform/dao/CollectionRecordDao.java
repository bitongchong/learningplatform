package com.sicau.platform.dao;

import com.sicau.platform.entity.CollectionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author boot liu
 */
public interface CollectionRecordDao   extends JpaRepository<CollectionRecord, Long>, JpaSpecificationExecutor<CollectionRecord> {
    CollectionRecord findByUserId(Long userId);
}
