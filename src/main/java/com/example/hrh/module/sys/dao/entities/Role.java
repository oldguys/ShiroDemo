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
 * @CreateTime: 2018-10-2018/10/15 0015 16:51
 */
@Data
@Entity(name = "sys_role")
@TableName(value = "sys_role" )
public class Role extends BaseEntity {

    @Column(length = 10)
    private String name;

    private String flag;

    public void setName(String name) {
        this.name = name;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
