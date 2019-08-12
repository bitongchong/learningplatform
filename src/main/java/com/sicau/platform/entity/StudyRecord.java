package com.sicau.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "study_record")
public class StudyRecord  implements Serializable {
    @Id
    private Long recordId;
    private Long articleId;
    private String articleTitle;
    private Long userid;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date openTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date accomplishTime;
    private Integer status;

    public StudyRecord() {
    }


    public Long getRecordId() {
        return this.recordId;
    }

    public Long getArticleId() {
        return this.articleId;
    }

    public String getArticleTitle() {
        return this.articleTitle;
    }

    public Long getUserid() {
        return this.userid;
    }

    public Date getOpenTime() {
        return this.openTime;
    }

    public Date getAccomplishTime() {
        return this.accomplishTime;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public void setAccomplishTime(Date accomplishTime) {
        this.accomplishTime = accomplishTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String toString() {
        return "StudyRecord(recordId=" + this.getRecordId() + ", articleId=" + this.getArticleId() + ", articleTitle=" + this.getArticleTitle() + ", userid=" + this.getUserid() + ", openTime=" + this.getOpenTime() + ", accomplishTime=" + this.getAccomplishTime() + ", status=" + this.getStatus() + ")";
    }
}
