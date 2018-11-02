package com.example.hrh.module.sys.service;


import com.example.hrh.module.sys.dao.entities.*;
import com.example.hrh.module.sys.dao.jpas.DictionaryMapper;
import com.example.hrh.module.sys.dao.jpas.RoleMapper;
import com.example.hrh.module.sys.dao.jpas.UserEntityMapper;
import com.example.hrh.module.sys.dao.jpas.UserGroupMapper;
import com.example.hrh.module.sys.dto.json.roles.RoleInfo;
import com.example.hrh.module.sys.dto.json.user.UserWithUserGroup;
import com.example.hrh.module.sys.dto.json.usergroup.UserGroupInfo;
import com.example.hrh.module.sys.dto.json.usergroup.UserGroupItem;
import com.example.hrh.module.sys.dto.json.usergroup.UserGroupWithRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/26 0026 14:44
 */
@Service
public class UserGroupService {

    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserEntityMapper userEntityMapper;
    @Autowired
    private MenuService menuService;

    /**
     * 用户组类别
     */
    public static final Map<String, String> ENTITY_TYPE = new HashMap<>();


    public Map<String, String> getEntityType() {
        if (ENTITY_TYPE.isEmpty()) {
            synchronized (getClass()) {
                if (ENTITY_TYPE.isEmpty()) {
                    List<com.example.hrh.module.sys.dao.entities.Dictionary> dictionaryList = dictionaryMapper.findByType(com.example.hrh.module.sys.dao.entities.Dictionary.DictionaryType.USER_GROUP_TYPE.getName());
                    dictionaryList.forEach(obj -> {
                        ENTITY_TYPE.put(obj.getFiledValue(), obj.getFiledName());
                    });
                }
            }
        }
        return ENTITY_TYPE;
    }

    /**
     * 获取用户组及角色的界面显示组件
     *
     * @param id
     * @return
     */
    public UserGroupWithRoles getUserGroupWithRoles(Long id) {

        UserGroup entity = userGroupMapper.findOneWithRoles(id);
        List<Role> roles = roleMapper.findAllByStatus(1);

        Set<Long> roleIdSet = new HashSet<>(entity.getRoles().size());
        entity.getRoles().forEach(obj -> {
            roleIdSet.add(obj.getId());
        });
        Map<String, List<RoleInfo>> roleMap = new HashMap<>(entity.getRoles().size());

        roles.forEach(obj -> {
            if (null == obj.getType()) {
                return;
            }
            List<RoleInfo> temp = roleMap.get(String.valueOf(obj.getType()));
            if (null == temp) {
                temp = new ArrayList<>();
                roleMap.put(String.valueOf(obj.getType()), temp);
            }

            RoleInfo info = new RoleInfo();
            info.setId(obj.getId());
            info.setRoleFlag(obj.getRoleFlag());
            info.setName(obj.getName());
            info.setType(obj.getType());
            if (roleIdSet.contains(obj.getId())) {
                info.setCheck(true);
            }
            temp.add(info);
        });
        //替换value -> name

        Map<String, List<RoleInfo>> tempMap = new HashMap<>(roleMap.size());
        Map<String, String> typeNameMap = roleService.getEntityType();
        roleMap.forEach((key, value) -> {
            tempMap.put(typeNameMap.get(key), value);
        });

        UserGroupWithRoles userGroupWithRoles = new UserGroupWithRoles();
        userGroupWithRoles.setUserGroup(entity);
        userGroupWithRoles.setRoleMap(tempMap);

        return userGroupWithRoles;
    }

    /**
     * 获取用户详细信息
     *
     * @param id
     * @return
     */
    public Object getUserInfo(Long id) {

        UserGroup entity = userGroupMapper.findOneWithRoles(id);
        if (null == entity) {
            return null;
        }

        Map<String, String> typeNameMap = roleService.getEntityType();
        Map<String, List<Role>> roleMap = new HashMap<>(typeNameMap.size());

        entity.getRoles().forEach(obj -> {

            String typeName = typeNameMap.get(String.valueOf(obj.getType()));
            List<Role> list;
            if (typeName != null) {
                list = roleMap.get(typeName);
                if (null == list) {
                    list = new ArrayList<>();
                    roleMap.put(typeName, list);
                }
                list.add(obj);
            }
        });

        UserGroupInfo info = new UserGroupInfo();
        info.setUserGroup(entity);
        info.setRoleMap(roleMap);
        info.setCount(userGroupMapper.countUserByGroupId(id));

        return info;
    }

    public UserWithUserGroup getUserWithUserGroup(String userId) {

        UserEntity userEntity = userEntityMapper.findUserWithUserGroups(userId);
        if (null == userEntity) {
            return null;
        }
        Map<Long, UserGroupItem> listMap = new HashMap<>(userEntity.getUserGroups().size());
        userEntity.getUserGroups().forEach(obj -> {

            UserGroupItem item = new UserGroupItem();
            item.setGroupId(obj.getId());
            item.setGroupName(obj.getGroupName());
            item.setCheck(true);

            listMap.put(obj.getId(), item);
        });

        Map<String, String> typeMap = getEntityType();
        Map<String, List<UserGroupItem>> userGroupMaps = new HashMap<>(typeMap.size());

        List<UserGroup> list = userGroupMapper.findAllByStatus(1);
        list.forEach(obj -> {

            if (null == obj.getType()) {
                return;
            }

            // 注意 Integer => String
            String typeName = typeMap.get(String.valueOf(obj.getType()));

            List<UserGroupItem> entityList = userGroupMaps.get(typeName);

            if (null == entityList) {
                entityList = new ArrayList<>();
                userGroupMaps.put(typeName, entityList);
            }

            UserGroupItem item = listMap.get(obj.getId());
            if (null == item) {
                item = new UserGroupItem();
                item.setGroupName(obj.getGroupName());
                item.setGroupId(obj.getId());
            }

            entityList.add(item);
        });

        UserWithUserGroup userWithUserGroup = new UserWithUserGroup();
        userWithUserGroup.setUser(userEntity);
        userWithUserGroup.setGroupMaps(userGroupMaps);

        // 关联目录
        userWithUserGroup.setMenuList(menuService.getMenusByUserId(userId));

        return userWithUserGroup;
    }
}
