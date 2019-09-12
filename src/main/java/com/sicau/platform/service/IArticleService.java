package com.sicau.platform.service;

import com.sicau.platform.entity.Article;
import com.sicau.platform.entity.IndexEntity;
import org.springframework.data.domain.Page;

/**
 * @author liuyuehe
 * @description
 * @date 2019/9/12
 */
public interface IArticleService {
    /**
     * 分页获取文章
     * @param type 文章类型
     * @param size 一页多少篇文章
     * @param page  页数
     * @return 文章list
     */
    Page<Article> getArticleByPage(Integer type, int size, int page);

    /**
     * -
     * @param article -
     * @return -
     */
    boolean addArticle(Article article);

    /**
     * 获取文章详情
     * @param articleId -
     * @return -
     */
    Article getArticleDetail(Long articleId);

    /**
     * 修改文章
     * @param article -
     * @return -
     */
    boolean updateArticle(Article article);

    /**
     * 删除文章
     * @param articleId -
     * @return -
     */
    boolean deleteArticle(Long articleId);

    /**
     * 文章搜索
     * @param key 关键词
     * @param page -
     * @param size -
     * @return -
     */
    Page<Article> search(String key, int page, int size);

    /**
     * 获取首页文章信息
     * @return -
     */
    IndexEntity getIndexInfo();
}
