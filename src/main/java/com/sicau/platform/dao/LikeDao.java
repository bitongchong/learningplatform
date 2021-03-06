package com.sicau.platform.dao;

import com.sicau.platform.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface LikeDao  extends JpaSpecificationExecutor<Like>, JpaRepository<Like, Long> {
    void deleteLikeByArticleIdAndAndUserId(Long articleId, Long userId);
    @Query(value = "select count(*) from like_record where article_id = ?", nativeQuery = true)
    Integer getLikeCount(Long articleId);
    Like findLikeByArticleIdAndUserId(Long articleId, Long userId);
}
