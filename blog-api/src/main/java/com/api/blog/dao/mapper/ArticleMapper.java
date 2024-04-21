package com.api.blog.dao.mapper;

import com.api.blog.dao.dos.Archives;
import com.api.blog.dao.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<Archives> listArchives();
    IPage<Article> listArticle(
            Page<Article> page,
            Long categoryId,
            Long tagId,
            String year,
            String month);
}
