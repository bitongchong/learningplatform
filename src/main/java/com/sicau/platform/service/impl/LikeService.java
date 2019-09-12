package com.sicau.platform.service.impl;

import com.sicau.platform.dao.LikeDao;
import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.entity.Like;
import com.sicau.platform.util.IdGenerator;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author liuyuehe
 * @description 本来应该用redis，留给后面的来搞吧
 * @date 2019/8/15
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class LikeService {
    private final HostHolder hostHolder;
    private final LikeDao likeDao;

    public LikeService(HostHolder hostHolder, LikeDao likeDao) {
        this.hostHolder = hostHolder;
        this.likeDao = likeDao;
    }

    public boolean addLike(Like like) {
        like.setCreateTime(new Date());
        like.setLikeId(IdGenerator.nextId());
        like.setUserId(hostHolder.getUser().getUserId());
        boolean likeStatus = getLikeStatus(like.getArticleId());
        if (likeStatus) {
            return false;
        }
        Like save = likeDao.save(like);
        return ObjectUtils.isNotEmpty(save);
    }

    public boolean cancelLike(Long articleId) {
        likeDao.deleteLikeByArticleIdAndAndUserId(articleId, hostHolder.getUser().getUserId());
        return true;
    }

    public boolean getLikeStatus(Long articleId) {
        Like like = likeDao.findLikeByArticleIdAndUserId(articleId, hostHolder.getUser().getUserId());
        return ObjectUtils.isNotEmpty(like);
    }

    public Integer getArticleLikeCount(Long articleId) {
        return likeDao.getLikeCount(articleId);
    }
}
