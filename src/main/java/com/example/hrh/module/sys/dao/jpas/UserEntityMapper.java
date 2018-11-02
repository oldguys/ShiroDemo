package com.example.hrh.module.sys.dao.jpas;/**
 * Created by Administrator on 2018/10/17 0017.
 */

import com.example.hrh.module.common.dao.jpas.BaseEntityMapper;
import com.example.hrh.module.sys.dao.entities.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/17 0017 08:57
 */
@Repository
public interface UserEntityMapper extends BaseEntityMapper<UserEntity> {

    UserEntity findByUsername(String username);

    /**
     *  获取用户信息及关联用户组信息
     * @param userId
     * @return
     */
    UserEntity findUserWithUserGroups(String userId);


    /**
     *  获取指定用户组下的所有用户信息
     * @param groupId
     * @return
     */
    List<UserEntity> findByGroupId(Long groupId);

    /**
     *  模糊查询索引
     * @param text
     * @param size
     * @return
     */
    List<UserEntity> findByUsernameAndUserIdContaining(@Param("text") String text,@Param("size") Integer size);
}
