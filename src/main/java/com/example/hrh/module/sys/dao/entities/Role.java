package com.example.hrh.module.sys.dao.entities;/**
 * Created by Administrator on 2018/10/15 0015.
 */

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hrh.module.common.annotation.Entity;
import com.example.hrh.module.common.dao.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;

import java.util.Collections;
import java.util.List;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/15 0015 16:51
 */
@Entity(name = "sys_role")
@TableName(value = "sys_role")
public class Role extends BaseEntity {

    @Column(length = 10)
    private String name;

    @Column(unique = true, nullable = false)
    private String roleFlag;

    private String description;

    private Integer type;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UserGroup> groups = Collections.emptyList();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Menu> menuList = Collections.emptyList();

    public String getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(String roleFlag) {
        this.roleFlag = roleFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
