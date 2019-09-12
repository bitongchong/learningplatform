package com.sicau.platform.service.impl;

import com.sicau.platform.dao.CollectionRecordDao;
import com.sicau.platform.entity.CollectionRecord;
import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.util.IdGenerator;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author liuyuehe
 * @description
 * @date 2019/8/15
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class CollectionRecordService {
    private final CollectionRecordDao collectionRecordDao;
    private final HostHolder hostHolder;

    public CollectionRecordService(CollectionRecordDao collectionRecordDao, HostHolder hostHolder) {
        this.collectionRecordDao = collectionRecordDao;
        this.hostHolder = hostHolder;
    }

    public boolean getCollectionStatus(Long articleId) {
        Long userId = hostHolder.getUser().getUserId();
        CollectionRecord byUserId = collectionRecordDao.findByUserIdAndArticleId(userId, articleId);
        return ObjectUtils.isNotEmpty(byUserId);
    }

    public boolean addCollectionRecord(CollectionRecord collectionRecord) {
        collectionRecord.setCollectionId(IdGenerator.nextId());
        collectionRecord.setCreateTime(new Date());
        collectionRecord.setUserId(hostHolder.getUser().getUserId());
        if (getCollectionStatus(collectionRecord.getArticleId())) {
            return false;
        }
        collectionRecordDao.save(collectionRecord);
        return true;
    }

    public Page<CollectionRecord> getAllCollectionRecord(int page, int size) {
        Long userId = hostHolder.getUser().getUserId();
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest of = PageRequest.of(page, size, sort);
        Specification<CollectionRecord> specification = (Specification<CollectionRecord>) (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("userId").as(Long.class), userId);
        return collectionRecordDao.findAll(specification, of);
    }

    public void cancelCollection(Long articleId) {
        collectionRecordDao.deleteCollectionRecordByArticleIdAndUserId(articleId, hostHolder.getUser().getUserId());
    }
}
