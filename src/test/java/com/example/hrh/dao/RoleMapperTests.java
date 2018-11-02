package com.example.hrh.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hrh.module.sys.dao.entities.Menu;
import com.example.hrh.module.sys.dao.entities.Role;
import com.example.hrh.module.sys.dao.jpas.RoleMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleMapperTests {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testFindByGroupId(){
        List<Role> roles = roleMapper.findByGroupId(1L);
        roles.forEach(System.out::println);
    }

    @Test
    public void testSave() {

        for (int i = 0; i < 5; i++) {
            Role role = new Role();
            role.setStatus(1);
            role.setName("测试" + i);
            role.setRoleFlag("role_" + i);
            role.setCreateTime(new Date());
            roleMapper.save(role);
        }
    }

    @Test
    public void testFindAllByStatus() {
        System.out.println("======================= 1");
        List<Role> roles = roleMapper.findAllByStatus(1);
        roles.forEach(System.out::println);
        System.out.println("======================= 0");
        roles = roleMapper.findAllByStatus(0);
        roles.forEach(System.out::println);
        System.out.println("======================= all");
        roles = roleMapper.findAllByStatus(null);
        roles.forEach(System.out::println);
    }

}
