package com.sicau.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Entity
@Table(name = "file_info")
public class FileEntity {
    @Id
    private Long fileId;
    private String fileName;
    private String fileUrl;
    private Integer fileType;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String fileOwner;

    public FileEntity() {
    }


    public Long getFileId() {
        return this.fileId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public Integer getFileType() {
        return this.fileType;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public String getFileOwner() {
        return this.fileOwner;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setFileOwner(String fileOwner) {
        this.fileOwner = fileOwner;
    }

    public String toString() {
        return "FileEntity(fileId=" + this.getFileId() + ", fileName=" + this.getFileName() + ", fileUrl=" + this.getFileUrl() + ", fileType=" + this.getFileType() + ", createDate=" + this.getCreateDate() + ", fileOwner=" + this.getFileOwner() + ")";
    }
}
