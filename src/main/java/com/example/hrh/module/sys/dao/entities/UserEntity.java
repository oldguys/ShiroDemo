package com.example.hrh.module.sys.dao.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hrh.module.common.annotation.Entity;
import com.example.hrh.module.common.dao.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Column;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/15 0015 16:38
 */
@Data
@Entity(pre = "sys_")
@TableName(value = "sys_user_entity")
public class UserEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(unique = true)
    private String username;

    private String password;

    private String sourcePassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getSourcePassword() {
        return sourcePassword;
    }

    public void setSourcePassword(String sourcePassword) {
        this.sourcePassword = sourcePassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sourcePassword='" + sourcePassword + '\'' +
                "} " + super.toString();
    }
}
