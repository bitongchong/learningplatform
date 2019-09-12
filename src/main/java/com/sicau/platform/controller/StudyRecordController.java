package com.sicau.platform.controller;

import com.sicau.platform.entity.PageResult;
import com.sicau.platform.entity.Result;
import com.sicau.platform.entity.StatusCode;
import com.sicau.platform.entity.StudyRecord;
import com.sicau.platform.service.impl.StudyRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author liuyuehe
 * @description 学习记录相关Controller
 * @date 2019/8/14
 */
@RestController
public class StudyRecordController {
    private final StudyRecordService studyRecordService;

    public StudyRecordController(StudyRecordService studyRecordService) {
        this.studyRecordService = studyRecordService;
    }

    @PostMapping(value = "/studyFinish")
    public Result finishStudy(@Param("articleId") Long articleId) throws Exception {
        StudyRecord record = studyRecordService.findRecord(articleId);
        return studyRecordService.finishRecord(record);
    }

    @GetMapping(value = "/studyRecord/{size}/{page}")
    public Result getStudyRecord(@PathVariable("size") int size, @PathVariable("page") int page) {
        Page<StudyRecord> studyRecord = studyRecordService.getStudyRecord(size, page);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<>(studyRecord.getTotalElements(), studyRecord.getContent()));
    }
}
