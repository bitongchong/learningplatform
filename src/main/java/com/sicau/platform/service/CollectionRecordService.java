package com.sicau.platform.service;

import com.sicau.platform.dao.CollectionRecordDao;
import com.sicau.platform.entity.CollectionRecord;
import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.util.IdGenerator;
import org.apache.catalina.Host;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

/**
 * @author liuyuehe
 * @description
 * @date 2019/8/15
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class CollectionRecordService {
    @Autowired
    CollectionRecordDao collectionRecordDao;
    @Autowired
    HostHolder hostHolder;

    public boolean getCollectionStatus(Long articleId) {
        Long userId = hostHolder.getUser().getUserid();
        CollectionRecord byUserId = collectionRecordDao.findByUserIdAndArticleId(userId, articleId);
        return ObjectUtils.isEmpty(byUserId);
    }

    public boolean addCollectionRecord(CollectionRecord collectionRecord) {
        collectionRecord.setCollectionId(IdGenerator.nextId());
        collectionRecord.setCreateTime(new Date());
        collectionRecord.setUserId(hostHolder.getUser().getUserid());
        collectionRecordDao.save(collectionRecord);
        // 当然不能直接返回
        return true;
    }

    public Page<CollectionRecord> getAllCollectionRecord(int page, int size) {
        Long userId = hostHolder.getUser().getUserid();
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest of = PageRequest.of(page, size, sort);
        Specification<CollectionRecord> specification = new Specification<CollectionRecord>() {
            @Override
            public Predicate toPredicate(Root<CollectionRecord> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("userId").as(Long.class), userId);
            }
        };
        return collectionRecordDao.findAll(specification, of);
    }

    public void cancelCollection(Long articleId) {
        collectionRecordDao.deleteByArticleIdAndUserId(articleId, hostHolder.getUser().getUserid());
    }
}
