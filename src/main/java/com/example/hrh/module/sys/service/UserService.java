package com.example.hrh.module.sys.service;/**
 * Created by Administrator on 2018/10/22 0022.
 */

import com.example.hrh.module.sys.dao.entities.UserEntity;
import com.example.hrh.module.sys.dao.jpas.UserEntityMapper;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/22 0022 10:32
 */
@Service
public class UserService {

    @Autowired
    private DefaultPasswordService passwordService;
    @Autowired
    private UserEntityMapper userEntityMapper;

    public void register(UserEntity userEntity){

    }
}
