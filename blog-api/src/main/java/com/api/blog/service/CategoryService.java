package com.api.blog.service;

import com.api.blog.vo.CategoryVo;

import java.util.List;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);
}
