package com.api.blog.controller;

import com.api.blog.cache.Cache;
import com.api.blog.common.aop.LogAnnotation;
import com.api.blog.service.ArticleService;
import com.api.blog.service.impl.ArticleServiceImpl;
import com.api.blog.vo.ArticleVo;
import com.api.blog.vo.Result;
import com.api.blog.vo.param.ArticleParam;
import com.api.blog.vo.param.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

//进行json数据交互
@RestController
@RequestMapping("/articles")
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    //    此接口用来记录日志
    @LogAnnotation(module="文章",operator="文章获取列表")
    @Cache(expire = 5 * 60 * 1000,name = "listArticle   ")
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    /**
     * 首页最热文章
     * @return
     */
    @PostMapping("/hot")
    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }
    /**
     * 首页最新文章
     * @return
     */
    @PostMapping("/new")
    @Cache(expire = 5 * 60 * 1000,name = "news_article")
    public Result newArticles() {
        int limit = 5;
        return articleService.newArticles(limit);
    }
    @PostMapping("/listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }
    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id")Long articleId){
        return articleService.findArticleById(articleId);
    }
    @PostMapping("/publish")
    public Result pulish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
