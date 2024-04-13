package com.api.blog.service;

import com.api.blog.dao.pojo.SysUser;
import com.api.blog.vo.Result;
import com.api.blog.vo.param.LoginParam;

public interface LoginService {
    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    Result logout(String token);
}
