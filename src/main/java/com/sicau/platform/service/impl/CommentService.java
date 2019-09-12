package com.sicau.platform.service.impl;

import com.sicau.platform.dao.AdminDao;
import com.sicau.platform.dao.CommentDao;
import com.sicau.platform.dao.UserDetailDao;
import com.sicau.platform.entity.Admin;
import com.sicau.platform.entity.Comment;
import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.entity.UserDetail;
import com.sicau.platform.util.IdGenerator;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author boot liu
 */
@Service
public class CommentService {
    private final CommentDao commentDao;
    private final HostHolder hostHolder;
    private final UserDetailDao userDetailDao;
    private final AdminDao adminDao;

    public CommentService(CommentDao commentDao, HostHolder hostHolder, UserDetailDao userDetailDao, AdminDao adminDao) {
        this.commentDao = commentDao;
        this.hostHolder = hostHolder;
        this.userDetailDao = userDetailDao;
        this.adminDao = adminDao;
    }

    public void addComment(Comment comment) {
        comment.setCommentId(IdGenerator.nextId());
        comment.setCreateTime(new Date());
        Long userId = hostHolder.getUser().getUserId();
        comment.setUserId(userId);
        comment.setUserName(getUserName(userId));
        // 当然，这儿可能失败，but don`t want to fix it :)
        commentDao.save(comment);
    }

    private String getUserName(Long sid) {
        UserDetail userDetail = userDetailDao.findBySid(sid);
        if (ObjectUtils.isNotEmpty(userDetail)) {
            return userDetail.getName();
        } else {
            return null;
        }
    }

    public List<Comment> findAllComment(Long articleId) {
        return commentDao.findAllByArticleId(articleId);
    }

    public boolean deleteComment(Long commentId) {
        Long userId = hostHolder.getUser().getUserId();
        Admin byAdminId = adminDao.getByAdminId(userId);
        if (!commentDao.findById(commentId).isPresent()) {
            return false;
        }
        if (byAdminId != null || userId.equals(commentDao.findById(commentId).get().getUserId())) {
            commentDao.deleteById(commentId);
            return true;
        }
        return false;
    }

    public Page<Comment> getAllCommentByPage(int size, int page) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest of = PageRequest.of(page, size, sort);

        return commentDao.findAll(of);
    }
}
