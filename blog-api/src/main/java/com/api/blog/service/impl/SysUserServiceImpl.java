package com.api.blog.service.impl;

import com.api.blog.dao.mapper.SysUserMapper;
import com.api.blog.dao.pojo.SysUser;
import com.api.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("星博客");
        }
        return sysUserMapper.selectById(id);
    }
}
