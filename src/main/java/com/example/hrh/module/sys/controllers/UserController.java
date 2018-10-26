package com.example.hrh.module.sys.controllers;/**
 * Created by Administrator on 2018/10/22 0022.
 */

import com.example.hrh.module.sys.dao.entities.UserEntity;
import com.example.hrh.module.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/22 0022 10:35
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public void login(@Valid UserEntity userEntity, BindingResult bindingResult){

    }

    @PostMapping("register")
    public void register(@Valid UserEntity userEntity, BindingResult bindingResult){




        return  ;
    }
}
