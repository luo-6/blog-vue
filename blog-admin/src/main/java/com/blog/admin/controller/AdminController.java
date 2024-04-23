package com.blog.admin.controller;

import com.blog.admin.model.params.PageParam;
import com.blog.admin.pojo.Permission;
import com.blog.admin.service.PermissionService;
import com.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private PermissionService permissionService;
    @PostMapping("/permission/permissionList")
    public Result listPermission(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);
    }
    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
    @GetMapping("/logout")
    public Result loginOut(@PathVariable("id") Long id){
        return null;
    }
}
