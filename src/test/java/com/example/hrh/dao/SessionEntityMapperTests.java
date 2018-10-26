package com.example.hrh.dao;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hrh.module.sys.dao.entities.SessionEntity;
import com.example.hrh.module.sys.dao.jpas.SessionEntityMapper;
import com.example.hrh.module.sys.dao.jpas.UserEntityMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionEntityMapperTests {

    @Autowired
    private SessionEntityMapper sessionEntityMapper;

    @Test
    public void testSelectList(){

        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setUsername("测试");
        Wrapper wrapper = new QueryWrapper(sessionEntity);
        List<SessionEntity> sessionEntityList = sessionEntityMapper.selectList(wrapper);
        sessionEntityList.forEach(obj ->{
            System.out.println(obj);
        });

    }
}
