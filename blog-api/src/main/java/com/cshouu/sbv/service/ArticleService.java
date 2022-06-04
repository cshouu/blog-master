package com.cshouu.sbv.service;

import com.cshouu.sbv.vo.Result;
import com.cshouu.sbv.vo.params.ArticleParam;
import com.cshouu.sbv.vo.params.PageParams;

public interface ArticleService {

    Result listArticle(PageParams pageParams);

    Result hotArticles(int limit);

    Result newArticles(int limit);

    Result listArchives();

    Result findArticleById(Long articleId);

    Result publish(ArticleParam articleParam);
}
