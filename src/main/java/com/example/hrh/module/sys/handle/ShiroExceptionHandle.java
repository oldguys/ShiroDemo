package com.example.hrh.module.sys.handle;/**
 * Created by Administrator on 2018/10/23 0023.
 */

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/23 0023 17:35
 */
@RestControllerAdvice
public class ShiroExceptionHandle {

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Object unauthenticatedException(UnauthenticatedException exception) {

        System.out.println("message:" + exception.getMessage());
        System.out.println("权限异常:没有认证");
        return "没有权限。。。。。";
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Object unauthorizedException(UnauthorizedException exception) {

        System.out.println("message:" + exception.getMessage());
        System.out.println("权限异常:没有授权登录");
        return "没有授权登录。。。。。";
    }


}
