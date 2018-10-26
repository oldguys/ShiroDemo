package com.example.hrh.module.sys.dao.jpas;/**
 * Created by Administrator on 2018/10/19 0019.
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hrh.module.sys.dao.entities.SessionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/19 0019 09:46
 */
@Repository
public interface SessionEntityMapper extends BaseMapper<SessionEntity> {

    /**
     *  获取关联的所有对象值
     * @param username
     * @return
     */
    List<SessionEntity> findActiveSessionByUsername(String username);

    /**
     *  批量更新状态
     * @param idCollection
     * @param status
     * @return
     */
    int updateStatusBatch(@Param("idCollection") Collection<Long> idCollection ,@Param("status") Integer status);
}
