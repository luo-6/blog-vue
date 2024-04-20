package com.api.blog.service;

import com.api.blog.dao.pojo.Tag;
import com.api.blog.vo.Result;
import com.api.blog.vo.TagVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    /**
     * 查询所有文章标签
     * @return
     */
    Result findAll();

    Result findAllDetail();

    Result findAllDetailById(Long id);
}
