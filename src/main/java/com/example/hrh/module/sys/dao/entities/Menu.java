package com.example.hrh.module.sys.dao.entities;/**
 * Created by Administrator on 2018/10/25 0025.
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hrh.module.common.annotation.Entity;
import com.example.hrh.module.common.dao.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/25 0025 21:36
 */
@Entity(pre = "sys_")
@TableName(value = "sys_menu" )
public class Menu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String url;

    private String name;

    private String icon;

    /**
     *  子节点列表
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Menu> subMenus = Collections.emptyList();

    /**
     *  父级节点
     */
    private Menu parentMenu;

    public List<Menu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                "} " + super.toString();
    }
}
