package com.api.blog.handler;

import com.api.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.image.RescaleOp;

@ControllerAdvice
//对ctoller注解进行拦截的方法进行拦截
public class AllExceptionHandler {
    //进行异常处理,处理Exception的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    //返回json数据，不加则返回页面
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"系统异常");
    }

}
