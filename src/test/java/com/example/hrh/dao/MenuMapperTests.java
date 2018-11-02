package com.example.hrh.dao;

import com.example.hrh.module.sys.dao.entities.Menu;
import com.example.hrh.module.sys.dao.jpas.MenuMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuMapperTests {

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void testSave(){

        for (int i = 0 ; i< 20 ; i++){
            Menu menu = new Menu();
            menu.setStatus(1);
            menu.setName("测试"+i);
            menu.setOrder(i);
            menu.setCreateTime(new Date());
            menuMapper.save(menu);
        }
    }

    @Test
    public void testFindAllByStatus() {
        System.out.println("======================= 1");
        List<Menu> menus = menuMapper.findAllByStatus(1);
        menus.forEach(System.out::println);
        System.out.println("======================= 0");
        menus = menuMapper.findAllByStatus(0);
        menus.forEach(System.out::println);
        System.out.println("======================= all");
        menus = menuMapper.findAllByStatus(null);
        menus.forEach(System.out::println);
    }

}
