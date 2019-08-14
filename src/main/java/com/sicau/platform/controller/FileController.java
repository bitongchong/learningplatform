package com.sicau.platform.controller;

import com.sicau.platform.entity.FileEntity;
import com.sicau.platform.entity.PageResult;
import com.sicau.platform.entity.Result;
import com.sicau.platform.entity.StatusCode;
import com.sicau.platform.service.FileService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author boot liu
 */
@RestController
public class FileController {
    @Autowired
    FileService fileService;

    @GetMapping("/file/{fileType}/{size}/{page}")
    public Result getFileByPage(@PathVariable("fileType") Integer fileType, @PathVariable("size") Integer size,
                                @PathVariable("page") Integer page) {
        Page<FileEntity> files = fileService.getFileByType(fileType, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<FileEntity>(files.getTotalElements(), files.getContent()));
    }

    @PostMapping("/file/upload")
    public Result updateFile(HttpServletRequest request, MultipartFile file,
                             @RequestParam("fileType") Integer fileType) throws Exception {
        String path = request.getSession().getServletContext().getRealPath("upload");
        Long fileId = fileService.uploadFile(file, path, fileType);
        // TODO 字符串拼接，资源消耗大，不优雅，用guava来优化一下
        String url = "/file/download?fileId=" + fileId;
        if (findFile(fileId)) {
            return new Result(true, StatusCode.OK, "文件上传成功", url);
        } else {
            return new Result(false, StatusCode.UPDATEERROR, "文件校验失败");
        }
    }

    private Boolean findFile(Long fileId) {
        FileEntity fileById = fileService.getFileById(fileId);
        return fileById != null;
    }

    @DeleteMapping("/file/delete/{fileId}")
    public Result deleteFile(@PathVariable("fileId") Long fileId) {
        if (fileService.deleteFile(fileId)) {
            return new Result(true, StatusCode.OK, "删除成功");
        } else {
            return new Result(false, StatusCode.ACCESSERROR, "无权删除文件");
        }
    }

    @RequestMapping("/file/download")
    public Result downloadFile(HttpServletResponse response, @RequestParam("fileId") Long fileId) {
        FileEntity fileEntity = fileService.getFileById(fileId);
        if (ObjectUtils.isEmpty(fileEntity)) {
            return new Result(false, StatusCode.RESOURCEERROR, "文件不存在");
        }
        String filePathName = fileEntity.getFileUrl();
        File file = new File(filePathName);
        if (!file.exists()) {
            return new Result(false, StatusCode.RESOURCEERROR, "文件不存在");
        }
        response.reset();
        response.setHeader("Content-Disposition", "attachment;fileName=" + filePathName);
        try {
            InputStream inStream = new FileInputStream(filePathName);
            OutputStream os = response.getOutputStream();

            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buff)) > 0) {
                os.write(buff, 0, len);
            }
            os.flush();
            os.close();

            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.RESOURCEERROR, "文件下载错误");
        }

        return new Result(true, StatusCode.OK, "文件下载成功");
    }
}