package com.example.hrh.module.sys.dao.jpas;/**
 * Created by Administrator on 2018/10/17 0017.
 */


import com.example.hrh.module.common.dao.jpas.BaseEntityMapper;
import com.example.hrh.module.sys.dao.entities.Menu;

import com.example.hrh.module.sys.dto.json.menu.MenuInfoItem;
import org.apache.ibatis.annotations.Param;
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
     * 通过用户ID 获取目录
     *
     * @param userId
     * @return
     */
    List<Menu> findByUserId(String userId);

    /**
     * 通过角色ID 获取角色目录
     *
     * @param rid
     * @return
     */
    List<Menu> findByRid(Long rid);

    /**
     *  通过状态获取 目录集合信息
     *
     * @param parentId
     * @param status
     * @return
     */
    List<MenuInfoItem> findWithInfoByStatus(@Param("parentId") Long parentId, @Param("status") Integer status);

    /**
     *  获取详细信息
     * @param id
     * @return
     */
    Menu findInfo(Long id);

    /**
     *  获取父级节点
     * @param id
     * @return
     */
    Menu findByParentId(Long id);

    /**
     *  获取实体以及相关联的子目录
     * @param id
     * @return
     */
    MenuInfoItem findOneWithSubMenus(Long id);
}
