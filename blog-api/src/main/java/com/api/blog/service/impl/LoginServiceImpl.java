package com.api.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.api.blog.dao.pojo.SysUser;
import com.api.blog.service.LoginService;
import com.api.blog.service.SysUserService;
import com.api.blog.utils.JWTUtils;
import com.api.blog.vo.ErrorCode;
import com.api.blog.vo.Result;
import com.api.blog.vo.param.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.provider.MD5;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserService userService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    private static final String jwtToken = "123456Mszlu!@###$$";
    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1.检查参数是否合法
         * 2.根据用户名和密码去用户表查询是否存在
         * 3.如果不存在，返回登陆失败
         * 4.如果存在，返回jwt，生成令牌给前端,
         * 5.toke放入redis,redis token:user信息 设置过期时间(登录认证先认定字符串，再认证redis是否存在)
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password + jwtToken);
        SysUser sysUser = userService.findUser(account,password);
        if (sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token))
        {
            return null;
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null)
            return null;
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    /**
     * 注册
     * @param loginParam
     * @return
     */
    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1.判断参数是否合法
         * 2.判断账号是否存在，存在，返回被注册
         *3.如果账户不存在，注册账户
         * 4.生成token
         * 5.生成redis
         * 6.注意加上事务，一旦中间出现任何问题，注册的用户需要回滚
         *
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = userService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),"账户已被注册");
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+jwtToken));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.userService.save(sysUser);
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);

    }
}
