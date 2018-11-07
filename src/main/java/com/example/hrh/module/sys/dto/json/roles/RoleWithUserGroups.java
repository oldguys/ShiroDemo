package com.example.hrh.module.sys.dto.json.roles;/**
 * Created by Administrator on 2018/11/3 0003.
 */

import com.example.hrh.module.sys.dao.entities.Role;
import com.example.hrh.module.sys.dao.entities.UserGroup;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/3 0003 09:22
 */
public class RoleWithUserGroups {

    private Role entity;

    private Map<String,List<UserGroup>> map = Collections.emptyMap();

    public Role getEntity() {
        return entity;
    }

    public void setEntity(Role entity) {
        this.entity = entity;
    }

    public Map<String, List<UserGroup>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<UserGroup>> map) {
        this.map = map;
    }
}
