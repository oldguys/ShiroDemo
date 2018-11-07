package com.example.hrh.module.sys.dao.jpas;/**
 * Created by Administrator on 2018/10/17 0017.
 */

import com.example.hrh.module.common.dao.jpas.BaseEntityMapper;
import com.example.hrh.module.sys.dao.entities.Role;
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
public interface RoleMapper extends BaseEntityMapper<Role> {

    /**
     *  通过角色标识获取角色名称
     * @param flag
     * @return
     */
    Role findByFlag(String flag);

    /**
     *  通过用户组ID获取
     *      status:true
     * @param groupId
     * @return
     */
    List<Role> findByGroupId(Long groupId);

    /**
     *  获取角色及其关联的目录信息
     * @param id
     * @return
     */
    Role findOneWithMenus(Long id);

    /**
     * 通过用户ID 获取角色标示
     * @param userId
     * @return
     */
    List<Role> findByUserId(String userId);


    /**
     *  移除目录控制
     * @param id
     * @return
     */
    int removeMenus(Long id);

    /**
     *  更新目录关联
     * @param id
     * @param idSet
     * @return
     */
    int associateMenus(@Param("id") Long id,@Param("idSet")  Set<Long> idSet);

    /**
     *  级联获取用户组
     * @param id 角色ID
     * @return
     */
    Role findOneWithUserGroups(Long id);
}
