package com.example.hrh.module.sys.service;


import com.example.hrh.module.sys.dao.entities.Dictionary;
import com.example.hrh.module.sys.dao.entities.Role;
import com.example.hrh.module.sys.dao.entities.UserGroup;
import com.example.hrh.module.sys.dao.jpas.DictionaryMapper;
import com.example.hrh.module.sys.dao.jpas.RoleMapper;
import com.example.hrh.module.sys.dto.json.roles.RoleWithMenus;
import com.example.hrh.module.sys.dto.json.roles.RoleWithUserGroups;
import com.example.hrh.module.sys.dto.json.user.UserRoleFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description: 角色
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/26 0026 14:44
 */
@Service
public class RoleService {

    /**
     * 角色类别
     */
    public static final Map<String, String> ENTITY_TYPE = new HashMap<>();
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserGroupService userGroupService;


    public Map<String, String> getEntityType() {
        if (ENTITY_TYPE.isEmpty()) {
            synchronized (getClass()) {
                if (ENTITY_TYPE.isEmpty()) {
                    List<Dictionary> dictionaryList = dictionaryMapper.findByType(Dictionary.DictionaryType.ROLE_TYPE.getName());
                    dictionaryList.forEach(obj -> {
                        ENTITY_TYPE.put(obj.getFiledValue(), obj.getFiledName());
                    });
                }
            }
        }
        return ENTITY_TYPE;
    }

    /**
     * 获取角色标示
     *
     * @param userId
     * @return
     */
    public UserRoleFlag getUserRoleFlags(String userId) {
        List<Role> flags = roleMapper.findByUserId(userId);

        UserRoleFlag userRoleFlags = new UserRoleFlag();
        userRoleFlags.setId(userId);

        return userRoleFlags;
    }

    /**
     * 获取角色以及该角色维护的目录
     *
     * @param id
     * @return
     */
    public RoleWithMenus getRoleWithMenu(Long id) {

        Role role = roleMapper.findOneWithMenus(id);

        RoleWithMenus roleWithMenus = new RoleWithMenus();
        roleWithMenus.setRole(role);
        roleWithMenus.setMenu(menuService.getBootstrapTreeByRoleId(id));

        return roleWithMenus;
    }


    public Object findOneWithUserGroup(Long id) {

        Role entity = roleMapper.findOneWithUserGroups(id);

        if (null == entity) {
            return null;
        }

        Map<String, String> typeMap = userGroupService.getEntityType();
        Map<String, List<UserGroup>> userGroupMap = new HashMap<>(typeMap.size());
        entity.getGroups().forEach(obj -> {

            String typeName = typeMap.get(String.valueOf(obj.getType()));

            if (null == typeName) {
                return;
            }

            List<UserGroup> list = userGroupMap.get(typeName);
            if (null == list) {
                list = new ArrayList<>();
                userGroupMap.put(typeName, list);
            }

            list.add(obj);
        });

        RoleWithUserGroups roleWithUserGroups = new RoleWithUserGroups();
        roleWithUserGroups.setEntity(entity);
        roleWithUserGroups.setMap(userGroupMap);

        return roleWithUserGroups;
    }

    public Map<String, List<Role>> getRoleMapByUserId(String userId) {

        List<Role> resource = roleMapper.findByUserId(userId);
        Map<String, String> entityTypeMap = getEntityType();
        Map<String, List<Role>> roleMap = new HashMap<>(entityTypeMap.size());
        resource.forEach(obj -> {

            String typeName = entityTypeMap.get(String.valueOf(obj.getType()));
            List<Role> list = roleMap.get(typeName);
            if (null == list) {
                list = new ArrayList<>();
                roleMap.put(typeName, list);
            }
            list.add(obj);
        });

        return roleMap;
    }
}
