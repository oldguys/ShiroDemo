package com.example.hrh.module.sys.dao.jpas;/**
 * Created by Administrator on 2018/10/17 0017.
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hrh.module.sys.dao.entities.UserEntity;
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
}
