package com.example.hrh.module.sys.controllers;/**
 * Created by Administrator on 2018/10/29 0029.
 */

import com.example.hrh.module.sys.dao.jpas.MenuMapper;
import com.example.hrh.module.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/29 0029 09:37
 */
@RestController
@RequestMapping("menu")
public class MenuController {


    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuMapper menuMapper;

    @GetMapping("current")
    public Object getCurrentUserMenu(){
        return menuService.getCurrentUserMenu();
    }

    @GetMapping("tree")
    public Object getMenuTree(){
        return menuService.getActiveMenuTree();
    }

    @GetMapping("all")
    public Object all(Integer status){
        return menuMapper.findAllByStatus(status);
    }

    @GetMapping("/tree/{rid}")
    public Object getMenuByRid(@PathVariable("rid") Long rid){
        return menuService.getMenuByRid(rid);
    }

    @GetMapping("bootstrap/tree/{rid}")
    public Object getTreeViewNode(@PathVariable("rid") Long rid){
        return menuService.getTreeViewNode(rid);
    }
}
