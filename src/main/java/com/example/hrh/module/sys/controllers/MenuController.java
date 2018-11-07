package com.example.hrh.module.sys.controllers;/**
 * Created by Administrator on 2018/10/29 0029.
 */


import com.example.hrh.module.common.utils.HttpJsonUtils;
import com.example.hrh.module.common.utils.ValidateUtils;
import com.example.hrh.module.sys.dao.entities.Menu;
import com.example.hrh.module.sys.dao.jpas.MenuMapper;

import com.example.hrh.module.sys.dao.jpas.TreeNodeMapper;
import com.example.hrh.module.sys.dto.form.menu.MenuAddForm;
import com.example.hrh.module.sys.dto.form.menu.MenuUpdateForm;
import com.example.hrh.module.sys.dto.json.menu.BootstrapTreeNode;
import com.example.hrh.module.sys.dto.json.menu.MenuInfoItem;
import com.example.hrh.module.sys.service.MenuService;

import com.example.hrh.module.sys.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;
import java.util.List;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/29 0029 09:37
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    private static final String MANAGE_TREE = "display";
    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private TreeService treeService;
    @Autowired
    private TreeNodeMapper treeNodeMapper;

    @GetMapping("all")
    public Object all(Long parentId, Integer status) {

        if(null != parentId){
            MenuInfoItem entity = menuMapper.findOneWithSubMenus(parentId);
            if (null != entity) {
                return entity;
            }
        }
        return menuMapper.findWithInfoByStatus(parentId, status);
    }

    /**
     * 获取用户具有的目录树
     * @param userId
     * @return
     */
    @GetMapping("{userId}/tree")
    public Object getMenuTreeByUserId(@PathVariable("userId") String userId) {
        return menuService.getMenuTreeByUserId(userId);
    }

    /**
     * 当前有效的所有目录树
     *
     * @return
     */
    @GetMapping("active/tree")
    public Object getActiveMenuTree() {
        return menuService.getActiveMenuTree();
    }

    @PostMapping("{menuId}/{status}")
    @Transactional(rollbackOn = Exception.class)
    public Object updateStatus(@PathVariable("menuId") Long menuId, @PathVariable("status") Integer status) {

        int resultCode = menuMapper.updateStatus(menuId, status);
        if (resultCode > 0) {
            resultCode = treeNodeMapper.updateStatusByType(Menu.class.getSimpleName(), menuId, status);
        }
        return resultCode > 0 ? HttpJsonUtils.OK : HttpJsonUtils.ERROR;
    }


    @GetMapping("bootstrap/tree")
    public Object getBootstrapTree(Long roleId, String display) {

        if (StringUtils.isEmpty(roleId)) {
            List<BootstrapTreeNode> list = new ArrayList<>();

            if (!StringUtils.isEmpty(display) && MANAGE_TREE.equals(display.trim())) {
                BootstrapTreeNode node = new BootstrapTreeNode();
                node.setId(0L);
                node.setText("根目录");
                list.add(node);
                list.addAll(treeService.getBootstrapTree(treeService.getTree(Menu.class.getSimpleName(), 1), null));
//                // 设置图标
//                TreeService.setIcon(list, "glyphicon glyphicon-leaf");
            } else {
                list = treeService.getBootstrapTree(treeService.getTree(Menu.class.getSimpleName(), 1), null);
            }
            return list;
        }

        List<Menu> menuList = menuMapper.findByRid(roleId);
        Set<Long> idSet = new HashSet<>(menuList.size());
        menuList.forEach(obj -> {
            idSet.add(obj.getId());
        });
        return treeService.getBootstrapTree(treeService.getTree(Menu.class.getSimpleName(), 1), idSet);
    }

    @PostMapping("addition")
    public Object addition(@Valid MenuAddForm form, BindingResult bindingResult) {

        ValidateUtils.validate(bindingResult);
        Menu entity = menuService.persist(form);

        return entity != null ? HttpJsonUtils.buildSuccess("新建成功！", entity) : HttpJsonUtils.ERROR;
    }

    @PostMapping("modify")
    public Object modify(@Valid MenuUpdateForm form, BindingResult bindingResult) {

        ValidateUtils.validate(bindingResult);
        Menu entity = menuService.modify(form);

        return entity != null ? HttpJsonUtils.buildSuccess("编辑成功！", entity) : HttpJsonUtils.ERROR;
    }

    @GetMapping("{id}/info")
    public Object getInfo(@PathVariable("id") Long id) {
        return menuMapper.findOne(id);
    }

    @GetMapping("{id}/subMenu")
    public Object getOneWithSubMenus(@PathVariable("id") Long id) {
        return menuMapper.findOneWithSubMenus(id);
    }


}
