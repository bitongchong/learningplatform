package com.sicau.platform.service;

import com.sicau.platform.dao.CommentDao;
import com.sicau.platform.dao.UserDetailDao;
import com.sicau.platform.entity.Comment;
import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.entity.UserDetail;
import com.sicau.platform.util.IdGenerator;
import org.apache.commons.lang3.ObjectUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    CommentDao commentDao;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserDetailDao userDetailDao;

    public void addComment(Comment comment) {
        comment.setCommentId(IdGenerator.nextId());
        comment.setCreateTime(new Date());
        Long userid = hostHolder.getUser().getUserid();
        comment.setUserId(userid);
        comment.setUserName(getUserName(userid));
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
        commentDao.deleteById(commentId);
        return true;
    }

    public Page<Comment> getAllCommentByPage(int size, int page) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest of = PageRequest.of(page, size, sort);

        return commentDao.findAll(of);
    }
}
