package com.example.hrh.module.sys.dao.jpas;/**
 * Created by Administrator on 2018/11/4 0004.
 */

import com.example.hrh.module.common.dao.jpas.BaseEntityMapper;
import com.example.hrh.module.sys.dao.entities.TreeNode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/4 0004 12:06
 */
@Repository
public interface TreeNodeMapper extends BaseEntityMapper<TreeNode> {

    /**
     * 获取树初始目录
     * @param entityKey 目录类型
     * @param status 状态
     * @return
     */
    List<TreeNode> findByTypeAndStatus(@Param("entityKey") String entityKey,@Param("status")  Integer status);

    /**
     *  获取父级目录节点
     * @param parentId
     * @param entityKey
     * @return
     */
    TreeNode findParentNode(@Param("entityId") Long parentId,@Param("entityKey")  String entityKey);

    /**
     *  通过父级获取目录
     * @param parentId
     * @param entityKey
     * @return
     */
    List<TreeNode> findByParentEntityId(@Param("parentEntityId") Long parentId,@Param("entityKey")  String entityKey);

    /**
     *  更新排序列表
     * @param sortUpdateList
     * @return
     */
    int updateSortList(@Param("entityList") List<TreeNode> sortUpdateList);

    /**
     *  通过 关联对象ID获取树节点
     * @param entityId
     * @param entityKey
     * @return
     */
    TreeNode findByEntityId(@Param("entityId") Long entityId,@Param("entityKey")  String entityKey);

    /**
     *  更新树节点状态
     * @param entityKey
     * @param entityId
     * @param status
     * @return
     */
    int updateStatusByType(@Param("entityKey") String entityKey,@Param("entityId")  Long entityId,@Param("status")  Integer status);
}
