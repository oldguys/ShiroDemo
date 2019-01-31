package com.example.hrh.module.sys.dao.jpas;/**
 * Created by Administrator on 2018/11/10 0010.
 */

import com.example.hrh.module.common.dao.jpas.BaseEntityMapper;
import com.example.hrh.module.sys.dao.entities.ResourcePerm;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/10 0010 10:02
 */
@Repository
public interface ResourcePermMapper extends BaseEntityMapper<ResourcePerm>{

    /**
     *  获取 资源列表
     * @param entityKey
     * @return
     */
    List<ResourcePerm> findByEntityKey(String entityKey);

    /**
     *  获取资源对象
     * @param businessKey
     * @return
     */
    ResourcePerm findByBusinessKey(String businessKey);
}
