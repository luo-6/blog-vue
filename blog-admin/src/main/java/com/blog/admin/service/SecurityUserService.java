package com.blog.admin.service;

import com.blog.admin.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SecurityUserService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //当登陆的时候，会把username传递到这里
        //通过username查询用户表，如果admin存在 将密码告诉spring,如果不存在，返回null
        Admin admin = this.adminService.findAdminByUsername(username);
        if (admin == null){
            //登陆失败
            return null;
        }

        UserDetails userDetails = new User(username, admin.getPassword(),new ArrayList<>());
        return userDetails;
    }
}
