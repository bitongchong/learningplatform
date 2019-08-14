package com.sicau.platform.controller;

import com.sicau.platform.entity.Comment;
import com.sicau.platform.entity.PageResult;
import com.sicau.platform.entity.Result;
import com.sicau.platform.entity.StatusCode;
import com.sicau.platform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author liuyuehe
 * @description 为了快速上线，太多地方没有进行参数判断了。。
 * @date 2019/8/14
 */
@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/addComment")
    public Result addComment(Comment comment) {
        commentService.addComment(comment);
        return new Result(true, StatusCode.OK, "评论添加成功");
    }

    @GetMapping("/getComment")
    public Result getComment(Long articleId) {
        List<Comment> allComment = commentService.findAllComment(articleId);
        return new Result(true, StatusCode.OK, "查询成功");
    }

    @DeleteMapping("/deleteComment")
    public Result deleteComment(Long commentId) {
        commentService.deleteComment(commentId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @GetMapping("/showAllComment/{size}/{page}")
    public Result showAllComment(@PathVariable("size") int size, @PathVariable("page") int page) {
        Page<Comment> allCommentByPage = commentService.getAllCommentByPage(size, page);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<Comment>(allCommentByPage.getTotalElements(), allCommentByPage.getContent()));
    }
}
