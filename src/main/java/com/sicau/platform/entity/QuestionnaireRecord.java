package com.sicau.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "questionnaire_record")
public class QuestionnaireRecord {
    @Id
    private Long qid;

    private Integer score;

    @JsonFormat(timezone = "GMT+8" ,pattern ="yyyy-MM-dd HH:mm:ss")
    private Date testtime;

    public Long getQid() {
        return qid;
    }

    public void setQid(Long qid) {
        this.qid = qid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getTesttime() {
        return testtime;
    }

    public void setTesttime(Date testtime) {
        this.testtime = testtime;
    }

    @Override
    public String toString() {
        return "QuestionnaireRecord{" +
                "qid=" + qid +
                ", score=" + score +
                ", testtime=" + testtime +
                '}';
    }
}
