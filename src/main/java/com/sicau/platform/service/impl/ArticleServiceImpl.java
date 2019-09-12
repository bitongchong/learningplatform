package com.sicau.platform.service.impl;

import com.sicau.platform.dao.AdminDao;
import com.sicau.platform.dao.ArticleDao;
import com.sicau.platform.entity.*;
import com.sicau.platform.enums.ArticleTypeEnum;
import com.sicau.platform.service.IArticleService;
import com.sicau.platform.util.IdGenerator;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author boot liu
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class ArticleServiceImpl implements IArticleService {
    private final ArticleDao articleDao;
    private final HostHolder hostHolder;
    private final AdminDao adminDao;
    private final WordFilterService wordFilterService;

    public ArticleServiceImpl(ArticleDao articleDao, HostHolder hostHolder, AdminDao adminDao, WordFilterService wordFilterService) {
        this.articleDao = articleDao;
        this.hostHolder = hostHolder;
        this.adminDao = adminDao;
        this.wordFilterService = wordFilterService;
    }

    @Override
    public Page<Article> getArticleByPage(Integer type, int size, int page) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        PageRequest of = PageRequest.of(page, size, sort);
        Specification<Article> specification = (Specification<Article>) (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("type").as(Integer.class), type);
        return articleDao.findAll(specification, of);
    }

    @Override
    public boolean addArticle(Article article) {
        User user = hostHolder.getUser();
        article.setContent(wordFilterService.filter(article.getContent()));
        article.setAuthor(user.getAccount());
        article.setArticleId(IdGenerator.nextId());
        article.setUpdateTime(new Date());
        article.setViewCount(0);
        articleDao.save(article);
        return true;
    }

    @Override
    public Article getArticleDetail(Long articleId) {
        Article article = articleDao.findByArticleId(articleId);
        synchronized (ArticleServiceImpl.class) {
            int viewCount = article.getViewCount();
            article.setViewCount(viewCount + 1);
        }
        return article;
    }

    @Override
    public boolean updateArticle(Article article) {
        String userAccount = hostHolder.getUser().getAccount();
        Long userId = hostHolder.getUser().getUserId();
        boolean isAdminId = adminDao.getByAdminId(userId) != null;
        boolean isArticleAuthor = userAccount.equals(article.getAuthor());
        boolean isArticleExist = ObjectUtils.isEmpty(article) && articleDao.findById(article.getArticleId()).isPresent();
        boolean isUpdatable = isAdminId || (isArticleAuthor && isArticleExist);
        if (isUpdatable) {
            article.setUpdateTime(new Date());
            articleDao.save(article);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteArticle(Long articleId) {
        String userAccount = hostHolder.getUser().getAccount();
        Long userId = hostHolder.getUser().getUserId();
        Admin byAdminId = adminDao.getByAdminId(userId);
        if (byAdminId != null || userAccount.equals(articleDao.findByArticleId(articleId).getAuthor())) {
            articleDao.deleteArticleByArticleId(articleId);
            return true;
        }
        return false;

    }

    @Override
    public Page<Article> search(String key, int page, int size) {
        page -= 1;
        PageRequest of = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "updateTime"));
        Specification<Article> specification = (Specification<Article>) (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("content").as(String.class), "%" + key + "%");
        return articleDao.findAll(specification, of);
    }

    @Override
    public IndexEntity getIndexInfo() {
        List<Article> acts = articleDao.find(4, ArticleTypeEnum.DYNAMIC.getCode());
        List<Article> infos = articleDao.find(3, ArticleTypeEnum.NOTICE.getCode());
        HashMap<String, List<Article>> map = new HashMap<>(8);
        map.put("notice", infos);
        map.put("dynamics", acts);
        IndexEntity entity = new IndexEntity();
        entity.setCode(StatusCode.OK);
        entity.setFlag(true);
        entity.setMessage("查询成功");
        entity.setData(map);
        return entity;
    }
}
