package com.cshouu.sbv.controller;

import com.cshouu.sbv.common.LogAnnotation;
import com.cshouu.sbv.common.cache.Cache;
import com.cshouu.sbv.service.ArticleService;
import com.cshouu.sbv.vo.Result;
import com.cshouu.sbv.vo.params.ArticleParam;
import com.cshouu.sbv.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//json数据交互
@RestController
@RequestMapping("api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    @LogAnnotation(module="文章",operator="获取文章列表")
    @Cache(expire = 5*60*1000,name = "list_article")
    public Result listArticles(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }

    @PostMapping("hot")
    @Cache(expire = 5*60*1000,name = "hot_article")
    public Result hotArticles(){
        int limit = 5;
        return articleService.hotArticles(limit);
    }

    @PostMapping("new")
    public Result newArticles(){
        int limit=5;
        return articleService.newArticles(limit);
    }

    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
