package com.example.hrh.module.sys.service;/**
 * Created by Administrator on 2018/11/4 0004.
 */

import com.example.hrh.module.common.exceptions.FormValidException;
import com.example.hrh.module.common.utils.ReflectUtils;
import com.example.hrh.module.common.utils.ValidateUtils;
import com.example.hrh.module.sys.dao.entities.Menu;
import com.example.hrh.module.sys.dao.entities.TreeNode;
import com.example.hrh.module.sys.dao.jpas.MenuMapper;
import com.example.hrh.module.sys.dto.form.menu.MenuAddForm;
import com.example.hrh.module.sys.dto.form.menu.MenuUpdateForm;
import com.example.hrh.module.sys.dto.json.menu.BootstrapTreeNode;
import com.example.hrh.module.sys.dto.json.menu.MenuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/4 0004 22:58
 */
@Service
public class MenuService {

    @Autowired
    public MenuMapper menuMapper;
    @Autowired
    private TreeService treeService;

    /**
     * 通过角色ID获取目录
     *
     * @param roleId
     * @return
     */
    public List<Menu> getMenuTreeByRoleId(Long roleId) {

        List<Menu> menuList = menuMapper.findByRid(roleId);
        List<Menu> resultMenuTree = getMenuTree(menuList);

        return resultMenuTree;
    }

    /**
     * 通过用户ID获取 用户具有的目录树
     *
     * @param userId
     * @return
     */
    public List<Menu> getMenuTreeByUserId(String userId) {

        List<Menu> menuList = menuMapper.findByUserId(userId);
        List<Menu> resultMenuTree = getMenuTree(menuList);

        return resultMenuTree;
    }

    /**
     * 获取当前有效的所有目录的目录树
     *
     * @return
     */
    public List<Menu> getActiveMenuTree() {

        List<Menu> menuList = menuMapper.findAllByStatus(1);
        List<Menu> resultMenuTree = getMenuTree(menuList);

        return resultMenuTree;
    }

    /**
     * 获取MenuTree
     *
     * @param menuList 可见Menu
     * @return
     */
    private List<Menu> getMenuTree(List<Menu> menuList) {
        Map<Long, Menu> menuMap = new HashMap<>(menuList.size());
        menuList.forEach(obj -> {
            menuMap.put(obj.getId(), obj);
        });

        List<TreeNode> activeTree = treeService.getTree(Menu.class.getSimpleName(), 1);
        List<Menu> resultMenuTree = new ArrayList<>();

        // 配置填充子节点
        appendMenuNode(menuMap, activeTree, resultMenuTree);
        return resultMenuTree;
    }

    /**
     * 填充MenuTree
     *
     * @param menuMap
     * @param activeTree
     * @param resultMenuTree
     */
    private void appendMenuNode(Map<Long, Menu> menuMap, List<TreeNode> activeTree, List<Menu> resultMenuTree) {
        activeTree.forEach(obj -> {
            Menu entity = menuMap.get(obj.getEntityId());
            if (null == entity) {
                return;
            }
            resultMenuTree.add(entity);

            // 配置父级节点
            Menu parentNode = menuMap.get(obj.getParentEntityId());
            if (null != parentNode) {
                // 清除子节点关联，避免级联死循环
                Menu temp = new Menu();
                ReflectUtils.updateFieldByClass(parentNode, temp);
                entity.setParentMenu(temp);
            }

            if (!obj.getSubTreeNodes().isEmpty()) {
                List<Menu> treeMenu = new ArrayList<>();
                entity.setSubMenus(treeMenu);
                // 递归添加子节点
                appendMenuNode(menuMap, obj.getSubTreeNodes(), treeMenu);
            }
        });
    }

    @Transactional(rollbackOn = Exception.class)
    public Menu persist(MenuAddForm form) {
        Menu entity = form.trainToEntity();
        entity.setStatus(1);
        entity.setCreateTime(new Date());

        menuMapper.save(entity);

        Long entityId = entity.getId();
        if (null == entityId) {
            throw new FormValidException("持久化目录失败！");
        }

        Long parentId = form.getParentId() == null ? 0L : form.getParentId();

        TreeNode node = new TreeNode();
        node.setCreateTime(new Date());
        node.setEntityName(entity.getName());
        node.setStatus(1);
        node.setEntityKey(Menu.class.getSimpleName());
        node.setEntityId(entityId);
        node.setParentId(parentId);

        // 保存Node节点
        treeService.saveTreeNode(node, form.getAfterMenuId());

        if (null == node.getId()) {
            throw new FormValidException("持久化树节点失败！");
        }

        return entity;
    }

    @Transactional(rollbackOn = Exception.class)
    public Menu modify(MenuUpdateForm form) {

        Menu temp = form.trainToEntity();
        Menu entity = menuMapper.findOne(form.getId());
        ReflectUtils.updateFieldByClass(temp, entity);
        menuMapper.update(entity);
        // 更新树节点排序
        treeService.modifyTreeNode(form.getId(), form.getParentId(), form.getAfterMenuId(), entity.getName(), Menu.class.getSimpleName());

        return entity;
    }

    public List<BootstrapTreeNode> getBootstrapTreeByRoleId(Long rid) {

        List<Menu> list = menuMapper.findByRid(rid);
        Set<Long> ids = new HashSet<>(list.size());
        list.forEach(obj -> {
            ids.add(obj.getId());
        });

        return treeService.getBootstrapTree(treeService.getTree(Menu.class.getSimpleName(), 1), ids);
    }

}
