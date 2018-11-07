package com.example.hrh.module.sys.dto.json.usergroup;


import com.example.hrh.module.sys.dao.entities.UserGroup;
import com.example.hrh.module.sys.dto.json.roles.RoleItem;

import java.util.List;
import java.util.Map;

/**
 * @Description: 目录
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/26 0026 14:44
 */
public class UserGroupWithRoles {

    private UserGroup userGroup;

    private Map<String,List<RoleItem>> roleMap;

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public Map<String, List<RoleItem>> getRoleMap() {
        return roleMap;
    }

    public void setRoleMap(Map<String, List<RoleItem>> roleMap) {
        this.roleMap = roleMap;
    }
}
