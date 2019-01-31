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

    /**
     *  批量持久化
     * @param collection
     * @return
     */
    int saveBatch(@Param("collections") Collection<T> collection);

    /**
     *  持久化
     * @param entity
     * @return
     */
    int save(T entity);

    /**
     *  更新
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     *  获取所有集合
     * @return
     */
    Set<S> findIds();

    /**
     *  获取实体
     * @param id
     * @return
     */
    T findOne(S id);

    /**
     *  获取所有的元素
     * @return
     */
    List<T> findAll();
}
