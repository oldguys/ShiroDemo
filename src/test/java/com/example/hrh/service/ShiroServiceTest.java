package com.example.hrh.service;/**
 * Created by Administrator on 2018/10/17 0017.
 */

import com.example.hrh.module.sys.dao.jpas.UserEntityMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/17 0017 15:31
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroServiceTest {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Test
    public void test(){
        System.out.println("test..................");
        SecurityUtils.getSubject().login(new UsernamePasswordToken("username","password"));

    }
}
