package com.example.hrh.module.sys.dao.jpas;/**
 * Created by Administrator on 2018/10/17 0017.
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface UserEntityMapper extends BaseMapper<UserEntity>{


    List<UserEntity> findAll();

    UserEntity findByUsername(String username);

    /**
     *  通过用户ID获取指定用户信息
     * @param userId
     * @return
     */
    UserEntity findByUserId(String userId);

    /**
     *  获取 List<UserEntity> 列表
     *  status :
     *      1 - 有效
     *      0 - 无效
     *      null -> 所有
     * @param status
     * @return
     */
    List<UserEntity> findAllByStatus(@Param("status") Integer status);

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
