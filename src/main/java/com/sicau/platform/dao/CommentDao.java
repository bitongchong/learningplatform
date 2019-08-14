package com.sicau.platform.dao;

import com.sicau.platform.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author liuyuehe
 * @description
 * @date 2019/8/14
 */
public interface CommentDao  extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
    /**
     * 通过文章id找出评论
     * @param articleId
     * @return
     */
    List<Comment> findAllByArticleId(Long articleId);
}
