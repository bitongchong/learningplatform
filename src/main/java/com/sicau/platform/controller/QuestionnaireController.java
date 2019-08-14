package com.sicau.platform.controller;

import com.sicau.platform.entity.*;
import com.sicau.platform.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuestionnaireController {
    @Autowired
    QuestionnaireService questionnaireService;

    @PostMapping("/addQuestionnaire")
    public Result addQuestionnaire(@PathVariable("url") String url,
                                   @PathVariable("title") String title) {
        questionnaireService.addQuestionnaire(url, title);
        return new Result(true, StatusCode.OK, "问卷信息添加成功");
    }

    @GetMapping("/questionnaire/{size}/{page}")
    public Result getAllQuestionnairesByPage(@PathVariable("size") int size, @PathVariable("page") int page) {
        Page<Questionnaire> questionnairesByPage = questionnaireService.getAllQuestionnaireByPage(size, page);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<Questionnaire>(questionnairesByPage.getTotalElements(), questionnairesByPage.getContent()));

    }

    @PostMapping("/addQuestionnaireRecords")
    public Result addQuestionnaireRecords(@RequestBody QuestionnaireRecord questionnaireRecord) {
        questionnaireService.addQuertionnaireRecords(questionnaireRecord);
        return new Result(true, StatusCode.OK, "问卷结果添加成功");
    }

    @GetMapping("/questionnaireRecords/{size}/{page}")
    public Result getAllQuestionnaireRecord(@PathVariable("size") int size, @PathVariable("page") int page) {
        Page<QuestionnaireRecord> allQuestionnaireRecordsByPage = questionnaireService.getAllQuestionnaireRecordsByPage(size, page);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<QuestionnaireRecord>(allQuestionnaireRecordsByPage.getTotalElements(), allQuestionnaireRecordsByPage.getContent()));
    }

}
