package com.sicau.platform.controller;

import com.sicau.platform.entity.*;
import com.sicau.platform.service.impl.QuestionnaireService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyuehe
 * @description 问卷相关Controller
 * @date 2019/8/14
 */
@RestController
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @PostMapping("/addQuestionnaire")
    public Result addQuestionnaire(String url,
                                   String title) {
        questionnaireService.addQuestionnaire(url, title);
        return new Result(true, StatusCode.OK, "问卷信息添加成功");
    }

    @GetMapping("/questionnaire/{size}/{page}")
    public Result getAllQuestionnairesByPage(@PathVariable("size") int size, @PathVariable("page") int page) {
        Page<Questionnaire> questionnairesByPage = questionnaireService.getAllQuestionnaireByPage(size, page);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<>(questionnairesByPage.getTotalElements(), questionnairesByPage.getContent()));
    }

    @PostMapping("/addQuestionnaireRecords")
    public Result addQuestionnaireRecords(String resultImgUrl, Long questionnaireId) {
        questionnaireService.addQuestionnaireRecords(resultImgUrl, questionnaireId);
        return new Result(true, StatusCode.OK, "问卷结果添加成功");
    }

    @GetMapping("/questionnaireRecords/{size}/{page}")
    public Result getAllQuestionnaireRecord(@PathVariable("size") int size, @PathVariable("page") int page) {
        Page<QuestionnaireRecord> allQuestionnaireRecordsByPage = questionnaireService.getAllQuestionnaireRecordsByPage(size, page);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<>(allQuestionnaireRecordsByPage.getTotalElements(), allQuestionnaireRecordsByPage.getContent()));
    }

    @GetMapping("/passTheRecord")
    public Result passTheRecord(Long questionnaireRecordId) {
        return questionnaireService.changeTheStatus(questionnaireRecordId) ? new Result(true, StatusCode.OK, "审核成功")
                : new Result(false, StatusCode.INTERNALSERVERERROR, "审核失败，请联系管理员");
    }
}
