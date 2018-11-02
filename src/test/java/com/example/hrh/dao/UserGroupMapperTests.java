package com.example.hrh.dao;

import com.example.hrh.module.sys.dao.entities.UserGroup;
import com.example.hrh.module.sys.dao.jpas.UserGroupMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserGroupMapperTests {

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Test
    public void testFindOneWithRoles(){
        UserGroup userGroup = userGroupMapper.findOneWithRoles(1L);
        System.out.println(userGroup);
    }

    @Test
    public void testFindAllByStatus() {
        System.out.println("======================= 1");
        List<UserGroup> userGroups = userGroupMapper.findAllByStatus(1);
        userGroups.forEach(System.out::println);
        System.out.println("======================= 0");
        userGroups = userGroupMapper.findAllByStatus(0);
        userGroups.forEach(System.out::println);
        System.out.println("======================= all");
        userGroups = userGroupMapper.findAllByStatus(null);
        userGroups.forEach(System.out::println);
    }

}
