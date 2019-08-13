package com.sicau.platform.controller;

import com.sicau.platform.entity.*;
import com.sicau.platform.service.StudyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudyRecordController {
    @Autowired
    StudyRecordService studyRecordService;

    @PostMapping(value = "/studyFinish")
    /**
     * 这儿其实可以在获取文章详情页的时候，返回一个studyId，通过这个id在study_record表中查
     */
    public Result finishStudy(@Param("articleId") Long articleId) throws Exception {
        StudyRecord record = studyRecordService.findRecord(articleId);
        return studyRecordService.finishRecord(record);
    }

    @GetMapping(value = "/studyRecord/{size}/{page}")
    public Result getStudyRecord(@PathVariable("size") int size, @PathVariable("page") int page) {
        Page<StudyRecord> studyRecord = studyRecordService.getStudyRecord(size, page);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<StudyRecord>(studyRecord.getTotalElements(), studyRecord.getContent()));
    }
}
