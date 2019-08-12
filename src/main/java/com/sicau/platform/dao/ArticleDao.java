package com.sicau.platform.dao;

import com.sicau.platform.entity.Article;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleDao extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    public Article findByArticleId(Long articleId);
    public Article findByArticleIdAndAuthor(Long articleId, String author);
    @Query(value = "SELECT * FROM article WHERE TYPE = ?2 ORDER BY updatetime DESC LIMIT 0,?1", nativeQuery = true)
    public List<Article> find(Integer size, Integer type);
//    @Delete(value = "DELETE FROM article where article_id = ?")
    public int deleteArticleByArticleId(Long articleId);
}
