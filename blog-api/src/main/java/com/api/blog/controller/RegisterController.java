package com.api.blog.controller;

import com.api.blog.service.LoginService;
import com.api.blog.vo.Result;
import com.api.blog.vo.param.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
//@Transactional
public class RegisterController {

    @Autowired
    private LoginService loginService;
    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        //sso 单次点击，后期如果把登录注册功能 踢出去（单独服务，可以独立提供接口）
        return loginService.register(loginParam);
    }
}
