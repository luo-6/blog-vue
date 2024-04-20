package com.api.blog.service;

import com.api.blog.vo.CategoryVo;
import com.api.blog.vo.Result;

import java.util.List;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();
}
