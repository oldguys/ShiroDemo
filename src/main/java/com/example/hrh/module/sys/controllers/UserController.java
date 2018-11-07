package com.example.hrh.module.sys.controllers;/**
 * Created by Administrator on 2018/10/22 0022.
 */


import com.example.hrh.module.sys.dao.entities.UserEntity;
import com.example.hrh.module.sys.dao.jpas.UserEntityMapper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/22 0022 10:35
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @GetMapping("all")
    public Object all(Integer status) {
        return userEntityMapper.findAllByStatus(status);
    }

    @GetMapping("group/{id}")
    public Object findByGroupId(@PathVariable("id") Long groupId) {
        return userEntityMapper.findByGroupId(groupId);
    }

    /**
     *  模糊搜索用户数据
     * @param text ID 或 用户姓名
     * @param size 默认 5
     * @return
     */
    @GetMapping("search")
    public Object findOne(String text, Integer size) {

        System.out.println("text:" + text);

        if (StringUtils.isEmpty(text)) {
            return Collections.emptyList();
        }
        size = size == null ? 5 : size;

        List<UserEntity> userEntities = userEntityMapper.findByUsernameAndUserIdContaining(text, size);

        return userEntities;
    }
}
