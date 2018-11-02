package com.example.hrh.module.sys.dao.jpas;/**
 * Created by Administrator on 2018/10/17 0017.
 */


import com.example.hrh.module.common.dao.jpas.BaseEntityMapper;
import com.example.hrh.module.sys.dao.entities.Menu;

import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/17 0017 08:57
 */
@Repository
public interface MenuMapper extends BaseEntityMapper<Menu> {

    /**
     *  通过用户ID 获取目录
     * @param userId
     * @return
     */
    List<Menu> findByUserId(String userId);

    /**
     *  通过角色ID 获取角色目录
     * @param rid
     * @return
     */
    List<Menu> findByRid(Long rid);
}
