package com.example.hrh.module.sys.service;/**
 * Created by Administrator on 2018/10/26 0026.
 */

import com.example.hrh.module.common.utils.ShiroUtils;
import com.example.hrh.module.sys.dao.entities.Menu;
import com.example.hrh.module.sys.dao.jpas.MenuMapper;
import com.example.hrh.module.sys.dto.json.menu.MenuNode;
import com.example.hrh.module.sys.dto.json.menu.BootstrapTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 目录
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/26 0026 14:44
 */
@Service
public class MenuService {


    private static final long PARENT_MENU_CODE = 0;

    @Autowired
    private MenuMapper menuMapper;

    /**
     *  获取当前系统所有可用目录
     * @return
     */
    public List<MenuNode> getActiveMenuTree() {
        List<Menu> resources = menuMapper.findAllByStatus(1);

        List<MenuNode> menu = new ArrayList<>();

        List<Menu> remove = new ArrayList<>();

        //设置主目录
        for (Menu model : resources) {
            if (model.getParentId().equals(PARENT_MENU_CODE)) {
                menu.add(new MenuNode(model));
                remove.add(model);
            }
        }
        resources.removeAll(remove);
        remove.clear();

        //填充子目录
        appendNode(resources, remove, menu);

        //排序
        sortMenuNode(menu);
        return menu;
    }


    /**
     * 填充节点
     *
     * @param resources
     * @param remove
     * @param menu      树形节点
     */
    private void appendNode(List<Menu> resources, List<Menu> remove, List<MenuNode> menu) {
        for (MenuNode node : menu) {
            if (resources.isEmpty()) {
                break;
            }
            List<MenuNode> subs = new ArrayList<>();
            for (Menu temp : resources) {
                if (temp.getParentId().equals(node.getId())) {
                    subs.add(new MenuNode(temp));
                    remove.add(temp);
                }
            }
            if (!subs.isEmpty()) {
                node.setSubs(subs);
                resources.removeAll(remove);
                remove.clear();
                appendNode(resources, remove, subs);
            }
        }
    }


    /**
     * 排序Menu
     *
     * @param menu
     */
    private void sortMenuNode(List<MenuNode> menu) {
        Collections.sort(menu);
        for (MenuNode node : menu) {
            if (!node.getSubs().isEmpty()) {
                sortMenuNode(node.getSubs());
            }
        }
    }


    public List<Menu> getCurrentUserMenu() {

        String userId = (String) ShiroUtils.getCurrentUser();

        List<Menu> allMenu = menuMapper.findAllByUserId(userId);

        // 该用户没有权限
        if (allMenu.isEmpty()) {
            return null;
        }

        List<Menu> parents = new ArrayList<>();

        for (Menu menu : allMenu) {
            if (menu.getParentId().equals(PARENT_MENU_CODE)) {
                parents.add(menu);
            }
        }

        allMenu.removeAll(parents);

        // 拼接节点
        appendNode(allMenu, parents);

        //目录排序
        sortMenu(parents);

        return parents;
    }

    /**
     * ============================================================================
     */



    /**
     * 排序吗目录
     *
     * @param parents
     */
    private void sortMenu(List<Menu> parents) {

        Collections.sort(parents);

        for (Menu menu : parents) {
            if (!menu.getSubMenu().isEmpty()) {
                sortMenu(menu.getSubMenu());
            }
        }

    }

    /**
     * 拼接节点
     *
     * @param allMenu
     * @param parents
     */
    private void appendNode(List<Menu> allMenu, List<Menu> parents) {
        for (Menu parent : parents) {

            List<Menu> temp = new ArrayList<>();

            if (allMenu.isEmpty()) {
                break;
            }

            for (Menu menu : allMenu) {
                if (menu.getParentId().equals(parent.getId())) {
                    temp.add(menu);
                }
            }

            if (!temp.isEmpty()) {
                parent.setSubMenu(temp);
                allMenu.removeAll(temp);

                //递归填充子节点
                appendNode(allMenu, temp);
            }

        }
    }




    private void checkMenu(List<MenuNode> menu, List<Menu> roleMenu) {

        if (roleMenu.isEmpty()) {
            return;
        }

        for (Menu sysMenu : roleMenu) {
            for (MenuNode node : menu) {

                if (sysMenu.getId().equals(node.getId())) {
                    node.setCheck(true);
                }

                if (!node.getSubs().isEmpty()) {
                    checkMenu(node.getSubs(), roleMenu);
                }

                continue;
            }
        }

    }

    /**
     * 获取所有可用的目录树
     *
     * @return
     */
    public List<MenuNode> getMenuByRid(Long rid) {

        List<MenuNode> menu = getActiveMenuTree();

        List<Menu> roleMenu = menuMapper.findByRid(rid);
        //设置目录选中
        checkMenu(menu, roleMenu);

        return menu;
    }

    public List<BootstrapTreeNode> getTreeViewNode(Long rid) {

        List<MenuNode> menu = getMenuByRid(rid);

        List<BootstrapTreeNode> treeViewNode = new ArrayList<>();

        pullTreeViewNode(menu, treeViewNode);

        return treeViewNode;
    }

    /**
     * 填充树节点
     *
     * @param menu
     * @param treeViewNode
     */
    private void pullTreeViewNode(List<MenuNode> menu, List<BootstrapTreeNode> treeViewNode) {

        for (MenuNode node : menu) {

            BootstrapTreeNode viewNode = new BootstrapTreeNode(node);
            treeViewNode.add(viewNode);

            if (!node.getSubs().isEmpty()) {
                List<BootstrapTreeNode> temp = new ArrayList<>();
                viewNode.setNodes(temp);
                pullTreeViewNode(node.getSubs(), temp);
            }

        }
    }

    public List<BootstrapTreeNode> getTreeViewNode() {

        List<MenuNode> menu = getActiveMenuTree();

        List<BootstrapTreeNode> treeViewNode = new ArrayList<>();

        pullTreeViewNode(menu, treeViewNode);

        return treeViewNode;
    }
}
