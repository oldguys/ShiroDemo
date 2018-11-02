package com.example.hrh.module.sys.dto.json.usergroup;/**
 * Created by Administrator on 2018/11/1 0001.
 */

import com.example.hrh.module.sys.dao.entities.Role;
import com.example.hrh.module.sys.dao.entities.UserGroup;
import com.example.hrh.module.sys.dto.json.roles.RoleInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/1 0001 14:15
 */
public class UserGroupInfo{

    private UserGroup userGroup;

    private Map<String,List<Role>> roleMap;

    private Integer count;

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public Map<String, List<Role>> getRoleMap() {
        return roleMap;
    }

    public void setRoleMap(Map<String, List<Role>> roleMap) {
        this.roleMap = roleMap;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
