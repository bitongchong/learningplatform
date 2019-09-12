package com.sicau.platform.service.impl;

import com.sicau.platform.dao.FeedbackDao;
import com.sicau.platform.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author liuyuehe
 * @description -
 * @date 2019/8/13
 */
@Service
public class FeedbackService {
    private final FeedbackDao feedbackDao;

    public FeedbackService(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }

    public boolean initialFeedback(Feedback feedback) {
        feedback.setCreateTime(new Date());
        feedbackDao.save(feedback);
        return true;
    }

    public Page<Feedback> showFeedback(int page, int size) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest of = PageRequest.of(page, size, sort);

        return feedbackDao.findAll(of);
    }
}
