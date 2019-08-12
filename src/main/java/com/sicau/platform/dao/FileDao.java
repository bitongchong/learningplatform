package com.sicau.platform.dao;

import com.sicau.platform.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileDao extends JpaSpecificationExecutor<FileEntity>, JpaRepository<FileEntity, Long> {
    FileEntity findAllByFileId(Long fileId);
    void deleteFileEntityByFileId(Long fileId);
}
