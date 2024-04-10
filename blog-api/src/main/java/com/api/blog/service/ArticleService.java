package com.api.blog.service;

import com.api.blog.vo.ArticleVo;
import com.api.blog.vo.Result;
import com.api.blog.vo.param.PageParams;

import java.util.List;

public interface ArticleService{
    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);
}
