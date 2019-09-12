package com.sicau.platform.dao;

import com.sicau.platform.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author boot liu
 */
public interface ArticleDao extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    /**
     * 通过文章id获取文章
     *
     * @param articleId 文章id
     * @return 文章对应信息
     */
    Article findByArticleId(Long articleId);

    /**
     * 通过文章的类型code以及文章页数进行分页查询
     *
     * @param size 文章页数
     * @param type 文章的类型code
     * @return 分页查询结果
     */
    @Query(value = "SELECT * FROM article WHERE TYPE = ?2 ORDER BY updatetime DESC LIMIT 0,?1", nativeQuery = true)
    List<Article> find(@Param("size") Integer size, @Param("type") Integer type);

    /**
     * 通过文章id删除文章
     *
     * @param articleId 文章id
     * @return 删除的行数
     */
    int deleteArticleByArticleId(Long articleId);
}
