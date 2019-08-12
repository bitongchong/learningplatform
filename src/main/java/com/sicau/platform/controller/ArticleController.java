package com.sicau.platform.controller;

import com.sicau.platform.entity.*;
import com.sicau.platform.service.ArticleService;
import com.sicau.platform.service.StudyRecordService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    StudyRecordService studyRecordService;

    @GetMapping("/article/index")
    public IndexEntity getIndexArticle(){
        IndexEntity indexInfo = articleService.getIndexInfo();
        return indexInfo;
    }

    @GetMapping("/article/{type}/{size}/{page}")
    public Result getArticleByPage(@PathVariable("type") int type, @PathVariable("size") int size, @PathVariable("page") int page) {
        Page<Article> articleByPage = articleService.getArticleByPage(type, size, page);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<Article>(articleByPage.getTotalElements(), articleByPage.getContent()));
    }

    @PostMapping("/addArticle")
    public Result addArticle(@RequestBody Article article) {
        articleService.addArticle(article);
        return new Result(true, StatusCode.OK, "文章添加成功");
    }

    @GetMapping("/article/{articleId}")
    public Result getArticleDetail(@PathVariable("articleId") Long articleId) {
        Article article = articleService.getArticleDetail(articleId);
        if (article == null) {
            return new Result(false, StatusCode.ARTICLEERROR, "文章不存在");
        } else {
            studyRecordService.initRecord(article);
            return new Result(true, StatusCode.OK, "查询成功", article);
        }
    }

    @PostMapping("/updateArticle")
    public Result updateArticle(@RequestBody Article article) {
        if (articleService.updateArticle(article)) {
            return new Result(true, StatusCode.OK, "修改成功");
        } else {
            return new Result(false, StatusCode.ACCESSERROR, "无权限修改本文章");
        }
    }

    @PostMapping("/deleteArticle")
    public Result deleteArticle(Long articleId){
        if (articleService.deleteArticle(articleId)){
            return new Result(true, StatusCode.OK, "删除成功");
        }else {
            return new Result(false, StatusCode.ARTICLEERROR, "无权限删除本文章");
        }
    }

    /**
     * 暂时没有做分页，原因是没有整合搜索框架，做了也白做
     * @param key
     * @return
     */
    @PostMapping("/searchArticle")
    public Result search(String key, int page, int size){
        Page<Article> result = articleService.search(key, page, size);
        if (result.getTotalElements() == 0){
            return new Result(false, StatusCode.ARTICLEERROR, "查询无结果",
                    new PageResult<>(0, null));
        }else{
            return new Result(true, StatusCode.OK, "查询成功",
                    new PageResult<Article>(result.getTotalElements(), result.getContent()));
        }
    }
}
