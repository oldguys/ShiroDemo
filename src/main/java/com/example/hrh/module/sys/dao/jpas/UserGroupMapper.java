package com.example.hrh.module.sys.dao.jpas;/**
 * Created by Administrator on 2018/10/17 0017.
 */


import com.example.hrh.module.common.dao.jpas.BaseEntityMapper;

import com.example.hrh.module.sys.dao.entities.UserEntity;
import com.example.hrh.module.sys.dao.entities.UserGroup;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/17 0017 08:57
 */
@Repository
public interface UserGroupMapper extends BaseEntityMapper<UserGroup> {

    /**
     * 获取用户组信息
     *  级联 roles
     * @param id
     * @return
     */
    UserGroup findOneWithRoles(Long id);

    /**
     * 关联用户组与角色
     * @param id 用户组ID
     * @param idSet jueseID
     * @return
     */
    int associateRoles(@Param("id") Long id, @Param("idSet") Set<Long> idSet);

    /**
     * 移除用户组关联的所有角色
     *
     * @param id 用户组ID
     * @return
     */
    int removeRoles(Long id);

    /**
     * 获取用户组信息
     *  级联 users
     * @param id
     * @return
     */
    UserGroup findUserGroupWithUsers(Long id);

    /**
     *  移除用户组关联的用户
     * @param id 用户组ID
     * @return
     */
    int removeUsers(Long id);

    /**
     * 关联用户与用户组
     * @param groupId
     * @param userIds
     * @return
     */
    int associateUsers(@Param("groupId") Long groupId, @Param("userIds") Set<String> userIds);

    /**
     *  移除与指定用户组关联的用户
     * @param id 用户组ID
     * @param idSet 用户ID
     * @return
     */
    int removeAssociateUsers(@Param("id") Long id,@Param("idSet")  Set<String> idSet);

    /**
     *  获取具有的用户数合集
     * @param id 用户组ID
     * @return
     */
    Integer countUserByGroupId(Long id);

    /**
     *  通过用户ID获取关联的用户组
     * @param id 用户ID
     * @return
     */
    List<UserGroup> findByUserId(Long id);
}
