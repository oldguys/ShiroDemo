package com.example.hrh.module.common.dao.jpas;/**
 * Created by Administrator on 2018/10/29 0029.
 */

import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/29 0029 10:19
 */
public interface BaseMapper<T, S> {

    int saveBatch(@Param("collections") Collection<T> collection);

    int save(T entity);

    int update(T entity);

    Set<S> findIds();

    /**
     *  获取实体
     * @param id
     * @return
     */
    T findOne(S id);

    List<T> findAll();
}
