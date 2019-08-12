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
@Table(name = "article")
public class Article implements Serializable {
    @Id
    private Long articleId;
    @JsonFormat(timezone = "GMT+8" ,pattern ="yyyy-MM-dd HH:mm:ss")
    private Date updatetime;
    private String title;
    private String content;
    private String author;
    private Integer viewCount;
    private String url;
    private int type;

    public Article() {
    }

    public Long getArticleId() {
        return this.articleId;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getAuthor() {
        return this.author;
    }

    public Integer getViewCount() {
        return this.viewCount;
    }

    public String getUrl() {
        return this.url;
    }

    public int getType() {
        return this.type;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toString() {
        return "Article(articleId=" + this.getArticleId() + ", updatetime=" + this.getUpdatetime() + ", title=" + this.getTitle() + ", content=" + this.getContent() + ", author=" + this.getAuthor() + ", viewCount=" + this.getViewCount() + ", url=" + this.getUrl() + ", type=" + this.getType() + ")";
    }
}