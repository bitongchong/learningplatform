package com.sicau.platform.service;

import com.sicau.platform.dao.AdminDao;
import com.sicau.platform.dao.ArticleDao;
import com.sicau.platform.entity.*;
import com.sicau.platform.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author boot liu
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class ArticleService {
    @Autowired
    ArticleDao articleDao;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    IdGenerator idGenerator;
    @Autowired
    AdminDao adminDao;
    @Autowired
    WordFilterService wordFilterService;

    class ArticleType{
        static final int NOTICE = 0;
        static final int DYNAMIC = 1;
        static final int IMPORTANT = 2;
        static final int HOT = 3;
        static final int VIDEO = 4;
    }

    public Page<Article> getArticleByPage(Integer type, int size, int page) {
        page -= 1;
        Sort sort = new Sort(Sort.Direction.DESC, "updatetime");
        PageRequest of = PageRequest.of(page, size, sort);
        Specification<Article> specification = new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("type").as(Integer.class), type);
            }
        };
        return articleDao.findAll(specification, of);
    }

    public boolean addArticle(Article article) {
        User user = hostHolder.getUser();
        article.setContent(wordFilterService.filter(article.getContent()));
        article.setAuthor(user.getAccount());
        article.setArticleId(IdGenerator.nextId());
        article.setUpdatetime(new Date());
        article.setViewCount(0);
        articleDao.save(article);
        return true;
    }

    public Article getArticleDetail(Long articleId) {
        Article article = articleDao.findByArticleId(articleId);
        synchronized (ArticleService.class) {
            int viewCount = article.getViewCount();
            article.setViewCount(viewCount + 1);
        }
        return article;
    }

    public boolean updateArticle(Article article) {
        // 这儿添加一个 && adminDao.findBy(hostHolder.getUser().getAccount())为空
        String userAccount = hostHolder.getUser().getAccount();
        Long userId = hostHolder.getUser().getUserid();
        Boolean isAdminId = adminDao.getByAdminId(userId) != null;
        Boolean isArticleAuthor =  userAccount.equals(article.getAuthor());
        Boolean isArticleExist = article != null && articleDao.findById(article.getArticleId()).isPresent();
        Boolean isUpdatable = isAdminId || (isArticleAuthor && isArticleExist);
        if (isUpdatable) {
            article.setUpdatetime(new Date());
            articleDao.save(article);
            return true;
        }
        return false;
    }

    public boolean deleteArticle(Long articleId) {
        String userAccount = hostHolder.getUser().getAccount();
        Long userId = hostHolder.getUser().getUserid();
        Admin byAdminId = adminDao.getByAdminId(userId);
        if (byAdminId != null || userAccount.equals(articleDao.findByArticleId(articleId).getAuthor())) {
            articleDao.deleteArticleByArticleId(articleId);
            return true;
        }
        return false;

    }

    public Page<Article> search(String key, int page, int size) {
        page -= 1;
        PageRequest of = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "updatetime"));
        Specification<Article> specification = new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("content").as(String.class), "%" + key + "%");
            }
        };
        return articleDao.findAll(specification, of);
    }

    public IndexEntity getIndexInfo(){
        /* todo:这儿有魔法值，不符合代码规范，建议全局配置或传参 */
        List<Article> acts = articleDao.find(4, ArticleType.DYNAMIC);
        List<Article> infos = articleDao.find(3, ArticleType.NOTICE);

        HashMap map = new HashMap(8);
        map.put("notice",infos);
        map.put("dynamics",acts);
        IndexEntity entity = new IndexEntity();
        entity.setCode(StatusCode.OK);
        entity.setFlag(true);
        entity.setMessage("查询成功");
        entity.setData(map);
        return entity;
    }
}
