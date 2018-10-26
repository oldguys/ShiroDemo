package com.example.hrh.module.sys.exceptions;/**
 * Created by Administrator on 2018/10/25 0025.
 */


import org.apache.shiro.authz.UnauthorizedException;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/25 0025 20:02
 */
public class PermException extends UnauthorizedException {

    public PermException(String message){
        super(message);
    }

}
