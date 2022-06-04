package com.cshouu.sbv.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cshouu.sbv.dao.mapper.ArticleMapper;
import com.cshouu.sbv.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {
    //期望在线程池中执行，不会影响原有主线程
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdated = new Article();
        articleUpdated.setViewCounts(viewCounts+1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        //为了在多线程环境下线程安全
        updateWrapper.eq(Article::getViewCounts,viewCounts);
        articleMapper.update(articleUpdated,updateWrapper);
    }
}
