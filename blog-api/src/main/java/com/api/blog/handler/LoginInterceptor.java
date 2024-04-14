package com.api.blog.handler;

import com.alibaba.fastjson.JSON;
import com.api.blog.dao.pojo.SysUser;
import com.api.blog.service.LoginService;
import com.api.blog.utils.UserThreadLocal;
import com.api.blog.vo.ErrorCode;
import com.api.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //再执行Controller方法之前执行
        /**
         * 1.需要判断请求路径是否是 handler method(controller方法)
         * 2.判断token是否为空，如果为空 ，未登录
         * 3.如果不为空，进行登陆验证
         * 4.认证成功，放行
         */
        if (!(handler instanceof HandlerMethod)){
            //handler可能是资源，访问的是spring boot,访问静态资源，默认去classpath下的static目录去查询
            return true;
        }
        String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");
        if (StringUtils.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(JSON.toJSONString(result));
            return false;
        }
        //登陆验证成功
        //直接在controller中获取用户信息信息
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果用完之后不删除，会有内存泄露的风险
        UserThreadLocal.remove();
    }
}
