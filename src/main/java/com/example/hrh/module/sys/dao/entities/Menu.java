package com.example.hrh.module.sys.dao.entities;/**
 * Created by Administrator on 2018/10/25 0025.
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hrh.module.common.annotation.Entity;
import com.example.hrh.module.common.dao.entities.BaseEntity;

import java.util.Collections;
import java.util.List;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/25 0025 21:36
 */
@Entity(pre = "sys_")
@TableName(value = "sys_menu" )
public class Menu extends BaseEntity implements Comparable<Menu>{
    private static final long serialVersionUID = 1L;

    private String url;

    private String name;

    private Integer order;

    private Long parentId = 0L;

    private String icon;

    /**
     * 菜单
     */
    private List<Menu> subMenu = Collections.emptyList();

    @Override
    public int compareTo(Menu o) {

        return this.order - o.order;
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Menu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<Menu> subMenu) {
        this.subMenu = subMenu;
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
                ", order=" + order +
                ", parentId=" + parentId +
                ", icon='" + icon + '\'' +
                ", subMenu=" + subMenu +
                "} " + super.toString();
    }
}
