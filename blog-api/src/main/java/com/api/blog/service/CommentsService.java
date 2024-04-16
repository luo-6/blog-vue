package com.api.blog.service;

import com.api.blog.vo.Result;
import com.api.blog.vo.param.CommentParam;

public interface CommentsService {
    /**
     * 根据文章id查询所有的列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}
