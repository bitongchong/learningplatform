package com.sicau.platform.controller;

import com.sicau.platform.entity.*;
import com.sicau.platform.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyuehe
 * @description 用户反馈相关controller
 * @date 2019/8/13
 */
@RestController
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/feedback")
    public Result initFeedback(Feedback feedback) {
        // 这个写法当然不行，为了方便而已
        return feedbackService.initiatFeedback(feedback) ? new Result(true, StatusCode.OK, "反馈提交成功")
                : new Result(false, StatusCode.INTERNALSERVERERROR, "反馈提交失败，请联系管理员");
    }

    @GetMapping("/showFeedback/{size}/{page}")
    public Result showFeedback(@PathVariable("size") int size, @PathVariable("page") int page) {
        Page<Feedback> feedback = feedbackService.showFeedback(size,page);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<Feedback>(feedback.getTotalElements(), feedback.getContent()));
    }
}
