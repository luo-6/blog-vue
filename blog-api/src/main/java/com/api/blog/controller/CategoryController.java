package com.api.blog.controller;

import com.api.blog.service.CategoryService;
import com.api.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public Result categorys(){
        return categoryService.findAll();
    }
    @GetMapping("/detail")
    public Result categorysDetails(){
        return categoryService.findAllDetail();
    }
    @GetMapping("/detail/{id}")
    public Result categoryDetailById(@PathVariable("id") Long id){
        return categoryService.categoryDetailById(id);
    }
}
