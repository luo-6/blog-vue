package com.api.blog.service;

import com.api.blog.dao.pojo.SysUser;
import com.api.blog.vo.Result;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account,String password);

    Result findUserByToken(String token);
}
