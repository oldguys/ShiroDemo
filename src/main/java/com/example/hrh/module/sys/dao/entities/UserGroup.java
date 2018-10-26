package com.example.hrh.module.sys.dao.entities;/**
 * Created by Administrator on 2018/10/15 0015.
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hrh.module.common.annotation.Entity;
import com.example.hrh.module.common.dao.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Column;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/15 0015 16:52
 */
@Data
@Entity(pre = "sys_")
@TableName(value = "sys_user_group" )
public class UserGroup extends BaseEntity {

    private String groupName;

    @Column(unique = true, nullable = false)
    private String flag;

    @Column(columnDefinition = "TEXT")
    private String description;

}
