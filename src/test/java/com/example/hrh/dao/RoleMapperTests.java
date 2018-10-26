package com.example.hrh.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hrh.module.sys.dao.entities.Role;
import com.example.hrh.module.sys.dao.entities.UserEntity;
import com.example.hrh.module.sys.dao.jpas.RoleMapper;
import com.example.hrh.module.sys.dao.jpas.UserEntityMapper;
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
    public void testSelectList(){
        Role index = new Role();
        index.setName("测试");

        List<Role> roles = roleMapper.selectList(new QueryWrapper(index));
        for(Role temp : roles){
            System.out.println(temp);
        }
    }

    @Test
    public void testSelectById() {
        Role role = roleMapper.selectById(1);
        System.out.println(role);
    }

    @Test
    public void testInsert() {

        Role entity = new Role();
        entity.setFlag("111");
        entity.setName("测试角色2");
        entity.setCreateTime(new Date());
        entity.setStatus(1);

        roleMapper.insert(entity);
    }

}
