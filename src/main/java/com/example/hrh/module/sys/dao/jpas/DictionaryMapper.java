package com.example.hrh.module.sys.dao.jpas;/**
 * Created by Administrator on 2018/10/30 0030.
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hrh.module.sys.dao.entities.Dictionary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/30 0030 11:05
 */
@Repository
public interface DictionaryMapper extends BaseMapper<Dictionary> {

    /**
     *  获取 List<T> 列表
     *  status :
     *      1 - 有效
     *      0 - 无效
     *      null -> 所有
     * @param status
     * @return
     */
    List<Dictionary> findAllByStatus(Integer status);

    /**
     * 	修改 T 状态
     * @param id
     * @param status
     * @return
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     *  获取指定类别下集合
     * @param type
     * @return
     */
    List<Dictionary> findByType(String type);
}
