package com.sicau.platform.controller;

import com.sicau.platform.entity.Like;
import com.sicau.platform.entity.Result;
import com.sicau.platform.entity.StatusCode;
import com.sicau.platform.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyuehe
 * @description
 * @date 2019/8/15
 */
@RestController
public class LikeController {
    @Autowired
    LikeService likeService;

    @PostMapping("/addLike")
    public Result addLike(Like like) {
        boolean b = likeService.addLike(like);
        return b ? new Result(true, StatusCode.OK, "点赞成功")
                : new Result(false, StatusCode.INTERNALSERVERERROR, "点赞失败");
    }

    @GetMapping("/getLikeStatus")
    public Result getLikeStatus(Long articleId) {
        boolean likeStatus = likeService.getLikeStatus(articleId);
        return new Result(true, StatusCode.OK, "查询成功", likeStatus);
    }

    @DeleteMapping("/cancelLike")
    public Result cancelLike(Long articleId) {
        boolean cancelLike = likeService.cancelLike(articleId);
        return cancelLike ? new Result(true, StatusCode.OK, "取消点赞成功")
                : new Result(false, StatusCode.INTERNALSERVERERROR, "取消失败");
    }

    @GetMapping("/getLikeCount")
    public Result getLikeCount(Long articleId) {
        Integer articleLikeCount = likeService.getArticleLikeCount(articleId);
        return new Result(true, StatusCode.OK, "查询成功", articleLikeCount);
    }
}
