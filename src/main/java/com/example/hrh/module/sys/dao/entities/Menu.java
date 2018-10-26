package com.example.hrh.module.sys.dao.entities;/**
 * Created by Administrator on 2018/10/25 0025.
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hrh.module.common.annotation.Entity;
import com.example.hrh.module.common.dao.entities.BaseEntity;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/25 0025 21:36
 */
@Entity(pre = "sys_")
@TableName(value = "sys_menu" )
public class Menu extends BaseEntity {
}
