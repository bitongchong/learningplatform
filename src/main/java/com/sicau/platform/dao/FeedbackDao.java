package com.sicau.platform.dao;

import com.sicau.platform.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author boot liu
 */
public interface FeedbackDao extends JpaRepository<Feedback, Integer>, JpaSpecificationExecutor<Feedback> {

}
