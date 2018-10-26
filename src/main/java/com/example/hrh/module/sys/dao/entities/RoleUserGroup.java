package com.example.hrh.module.sys.dao.entities;/**
 * Created by Administrator on 2018/10/25 0025.
 */


import com.example.hrh.module.common.annotation.AssociateEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/25 0025 21:37
 */
@AssociateEntity(pre = "sys_")
public class RoleUserGroup {

    @Id
    @GeneratedValue
    private Long id;

    private Long roleId;

    private Long userGroupId;
}
