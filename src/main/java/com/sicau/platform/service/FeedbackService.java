package com.sicau.platform.service;

import com.sicau.platform.dao.FeedbackDao;
import com.sicau.platform.entity.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author liuyuehe
 * @description
 * @date 2019/8/13
 */
@Service
public class FeedbackService {
    @Autowired
    FeedbackDao feedbackDao;

    public boolean initiatFeedback(Feedback feedback) {
        feedback.setCreateDate(new Date());
        Feedback saveResult = feedbackDao.save(feedback);
        return Objects.nonNull(saveResult);
    }

    public Page<Feedback> showFeedback(int page, int size) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest of = PageRequest.of(page, size, sort);

        return feedbackDao.findAll(of);
    }
}
