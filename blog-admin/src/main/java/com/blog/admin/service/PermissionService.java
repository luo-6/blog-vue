package com.blog.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.admin.mapper.PermissionMapper;
import com.blog.admin.model.params.PageParam;
import com.blog.admin.pojo.PageResult;
import com.blog.admin.pojo.Permission;
import com.blog.admin.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    public Result listPermission(PageParam pageParam) {
        /**
         * 要的数据，管理台，表的所有字段
         * 分页查询
         * @param pageParam
         * @return
         */
        Page<Permission> permissionPage = new Page<>(pageParam.getCurrentPage(),  pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNoneBlank(pageParam.getQueryString())){
            queryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
        permissionMapper.selectPage(permissionPage,queryWrapper);
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }
    public Result add(Permission permission) {
        this.permissionMapper.insert(permission);
        return Result.success(null);
    }

    public Result update(Permission permission) {
        this.permissionMapper.updateById(permission);
        return Result.success(null);
    }

    public Result delete(Long id) {
        this.permissionMapper.deleteById(id);
        return Result.success(null);
    }
}
