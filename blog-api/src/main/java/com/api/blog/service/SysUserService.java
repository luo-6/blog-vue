package com.api.blog.service;

import com.api.blog.dao.pojo.SysUser;
import com.api.blog.vo.Result;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account,String password);

    Result findUserByToken(String token);

    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);
}
