package com.example.hrh.module.sys.dto.json.menu;/**
 * Created by Administrator on 2018/11/6 0006.
 */

import com.example.hrh.module.sys.dao.entities.Menu;

import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/6 0006 15:33
 */
public class MenuInfo {

    /**
     *  实体
     */
    private Menu entity;

    /**
     *  排序集合
     */
    private List<Menu> menuList;

    /**
     *  父级目录
     */
    private Menu parentMenu;

    public Menu getEntity() {
        return entity;
    }

    public void setEntity(Menu entity) {
        this.entity = entity;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }
}
