package com.sicau.platform.service;

import com.sicau.platform.dao.AdminDao;
import com.sicau.platform.dao.FileDao;
import com.sicau.platform.entity.Admin;
import com.sicau.platform.entity.FileEntity;
import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.util.IdGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.Optional;

/**
 * @author boot liu
 */
@Service
@Transactional
public class FileService {

    private final FileDao fileDao;
    private final HostHolder hostHolder;
    private final AdminDao adminDao;

    public FileService(FileDao fileDao, HostHolder hostHolder, AdminDao adminDao) {
        this.fileDao = fileDao;
        this.hostHolder = hostHolder;
        this.adminDao = adminDao;
    }

    public Page<FileEntity> getFileByType(Integer fileType, int page, int size) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Specification<FileEntity> specification = (Specification<FileEntity>) (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("fileType").as(Integer.class), fileType);
        return fileDao.findAll(specification, pageRequest);
    }

    public FileEntity getFileById(Long fileId) {
        FileEntity fileEntity;
/*        if (fileDao.findById(fileId).isPresent()) {
            throw new RuntimeException("文件获取失败");
        }*/
        Optional<FileEntity> optionalFileEntity = fileDao.findById(fileId);
        boolean result = fileDao.findById(fileId).isPresent();
        if (!result) {
            return null;
        }
        fileEntity = optionalFileEntity.get();
        return fileEntity;
    }

    public Long uploadFile(MultipartFile file, String path, Integer fileType) throws Exception {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileType(fileType);
        fileEntity.setCreateDate(new Date());
        fileEntity.setFileName(file.getOriginalFilename());
        Long fileId = IdGenerator.nextId();
        fileEntity.setFileId(fileId);
        fileEntity.setFileOwner(hostHolder.getUser().getAccount());
        fileEntity.setFileUrl(saveFileToServer(file, path));
        fileDao.save(fileEntity);
        return fileId;
    }

    public String saveFileToServer(MultipartFile multifile, String path)
            throws IOException {
        // 创建目录
        File dir = new File(path);
        if (!dir.exists()) {
            dir.setWritable(true);
            dir.mkdir();
        }
        // 读取文件流并保持在指定路径
        InputStream inputStream = multifile.getInputStream();
        OutputStream outputStream = new FileOutputStream(path
                + multifile.getOriginalFilename());
        byte[] buffer = multifile.getBytes();
        int bytesum = 0;
        int byteread = 0;
        while ((byteread = inputStream.read(buffer)) != -1) {
            bytesum += byteread;
            outputStream.write(buffer, 0, byteread);
            outputStream.flush();
        }
        outputStream.close();
        inputStream.close();

        return path + multifile.getOriginalFilename();
    }

    public boolean deleteFile(Long fileId) {
        String userAccount = hostHolder.getUser().getAccount();
        Long userId = hostHolder.getUser().getUserid();
        Admin byAdminId = adminDao.getByAdminId(userId);
        if (byAdminId != null || userAccount.equals(fileDao.findAllByFileId(fileId).getFileOwner())) {
            fileDao.deleteFileEntityByFileId(fileId);
            return true;
        }
        return false;
    }
}
