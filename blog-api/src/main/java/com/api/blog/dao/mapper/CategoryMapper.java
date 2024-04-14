package com.api.blog.dao.mapper;

import com.api.blog.dao.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Locale;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
