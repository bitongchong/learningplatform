package com.sicau.platform.controller;

import com.sicau.platform.entity.*;
import com.sicau.platform.service.impl.ArticleServiceImpl;
import com.sicau.platform.service.impl.StudyRecordService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyuehe
 * @description 文章相关控制器
 * @date 2019/9/12
 */
@RestController
public class ArticleController {
    private final ArticleServiceImpl articleServiceImpl;
    private final StudyRecordService studyRecordService;

    public ArticleController(ArticleServiceImpl articleServiceImpl, StudyRecordService studyRecordService) {
        this.articleServiceImpl = articleServiceImpl;
        this.studyRecordService = studyRecordService;
    }

    @GetMapping("/article/index")
    public IndexEntity getIndexArticle() {
        return articleServiceImpl.getIndexInfo();
    }

    @GetMapping("/article/{type}/{size}/{page}")
    public Result getArticleByPage(@PathVariable("type") int type, @PathVariable("size") int size, @PathVariable("page") int page) {
        Page<Article> articleByPage = articleServiceImpl.getArticleByPage(type, size, page);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<>(articleByPage.getTotalElements(), articleByPage.getContent()));
    }

    @PostMapping("/addArticle")
    public Result addArticle(@RequestBody Article article) {
        boolean b = articleServiceImpl.addArticle(article);
        return b ? new Result(true, StatusCode.OK, "文章添加成功")
                : new Result(false, StatusCode.INTERNALSERVERERROR, null);
    }

    @GetMapping("/article/{articleId}")
    public Result getArticleDetail(@PathVariable("articleId") Long articleId) {
        Article article = articleServiceImpl.getArticleDetail(articleId);
        if (article == null) {
            return new Result(false, StatusCode.ARTICLEERROR, "文章不存在");
        } else {
            studyRecordService.initRecord(article);
            return new Result(true, StatusCode.OK, "查询成功", article);
        }
    }

    @PostMapping("/updateArticle")
    public Result updateArticle(@RequestBody Article article) {
        if (articleServiceImpl.updateArticle(article)) {
            return new Result(true, StatusCode.OK, "修改成功");
        } else {
            return new Result(false, StatusCode.ACCESSERROR, "无权限修改本文章");
        }
    }

    @PostMapping("/deleteArticle")
    public Result deleteArticle(Long articleId) {
        if (articleServiceImpl.deleteArticle(articleId)) {
            return new Result(true, StatusCode.OK, "删除成功");
        } else {
            return new Result(false, StatusCode.ARTICLEERROR, "无权限删除本文章");
        }
    }

    /**
     * 暂时没有做分页，原因是没有整合搜索框架，做了也白做
     *
     * @param key 搜索的key
     * @return 分页搜索
     */
    @PostMapping("/searchArticle")
    public Result search(String key, int page, int size) {
        Page<Article> result = articleServiceImpl.search(key, page, size);
        if (result.getTotalElements() == 0) {
            return new Result(false, StatusCode.ARTICLEERROR, "查询无结果",
                    new PageResult<>(0, null));
        } else {
            return new Result(true, StatusCode.OK, "查询成功",
                    new PageResult<>(result.getTotalElements(), result.getContent()));
        }
    }
}
