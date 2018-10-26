package com.example.hrh.dao;

import com.example.hrh.module.sys.dao.entities.UserEntity;
import com.example.hrh.module.sys.dao.jpas.UserEntityMapper;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEntityMapperTests {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private DefaultPasswordService passwordService;

    @Test
    public void testFindAll() {
        List<UserEntity> userEntityList = userEntityMapper.findAll();

        for (UserEntity entity : userEntityList) {
            System.out.println(entity);
        }
    }

    @Test
    public void testFindByUsername() {
        UserEntity userEntity = userEntityMapper.findByUsername("测试");
        System.out.println(userEntity);
    }

    @Test
    public void testSelectById() {
        UserEntity userEntity = userEntityMapper.selectById(1);
        System.out.println(userEntity);
    }

    @Test
    public void testSave() {

        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();

        String password = "123456";

        UserEntity entity = new UserEntity();
        entity.setStatus(1);
        entity.setCreateTime(new Date());
        entity.setUsername("测试1");
        entity.setSourcePassword(password);
        String encodePsw = passwordService.encryptPassword(password);

        System.out.println("加密后:" + encodePsw);
        entity.setPassword(encodePsw);


        userEntityMapper.insert(entity);
    }

}
