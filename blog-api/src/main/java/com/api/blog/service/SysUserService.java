package com.api.blog.service;

import com.api.blog.dao.pojo.SysUser;

public interface SysUserService {
    SysUser findUserById(Long id);
}
