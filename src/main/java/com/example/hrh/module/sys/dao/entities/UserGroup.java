package com.example.hrh.module.sys.dao.entities;/**
 * Created by Administrator on 2018/10/15 0015.
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hrh.module.common.annotation.Entity;
import com.example.hrh.module.common.dao.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import java.util.Collections;
import java.util.List;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/15 0015 16:52
 */
@Entity(pre = "sys_")
@TableName(value = "sys_user_group" )
public class UserGroup extends BaseEntity {

    private String groupName;

    private Integer type;

    @Column(columnDefinition = "TEXT")
    private String description;

    private List<Role> roles = Collections.emptyList();

    private List<UserEntity> users = Collections.emptyList();

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "groupName='" + groupName + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", roles=" + roles +
                "} " + super.toString();
    }
}
