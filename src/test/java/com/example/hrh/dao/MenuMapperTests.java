package com.example.hrh.dao;


import com.example.hrh.module.sys.dao.entities.Menu;
import com.example.hrh.module.sys.dao.jpas.MenuMapper;
import com.example.hrh.module.sys.dto.json.menu.MenuInfoItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuMapperTests {

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void testFindOne(){
        System.out.println(menuMapper.findOne(3L));
    }

    @Test
    public void testFinOneWithSubMenus() {
        MenuInfoItem entity = menuMapper.findOneWithSubMenus(8L);
        System.out.println(entity);
        if (!entity.getSubMenus().isEmpty()) {
            System.out.println("子目录.........");
            entity.getSubMenus().forEach(System.out::println);
        }
    }

    @Test
    public void testFindInfo() {
        Menu entity = menuMapper.findInfo(8L);
        System.out.println();
        System.out.println(entity);
        if (null != entity.getParentMenu()) {
            System.out.println("父级目录");
            System.out.println(entity.getParentMenu());
            if (!entity.getSubMenus().isEmpty()) {
                System.out.println("子目录");
                entity.getSubMenus().forEach(System.out::println);
            }
        }
        System.out.println();
    }

    @Test
    public void testFindWithInfoByStatus() {
        List<MenuInfoItem> list = menuMapper.findWithInfoByStatus(null, 1);
        list.forEach(System.out::println);
    }

}
