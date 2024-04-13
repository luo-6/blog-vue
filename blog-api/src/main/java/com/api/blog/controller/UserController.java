package com.api.blog.controller;

import com.api.blog.service.SysUserService;
import com.api.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private SysUserService userService;
    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return userService.findUserByToken(token);
    }
}

