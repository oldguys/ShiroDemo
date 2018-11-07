package com.example.hrh.module.sys.controllers;

import com.example.hrh.module.common.controllers.BaseController;
import com.example.hrh.module.common.exceptions.FormValidException;
import com.example.hrh.module.common.utils.HttpJsonUtils;
import com.example.hrh.module.common.utils.ReflectUtils;
import com.example.hrh.module.common.utils.ValidateUtils;
import com.example.hrh.module.sys.dao.entities.*;
import com.example.hrh.module.sys.dao.jpas.RoleMapper;
import com.example.hrh.module.sys.dto.form.roles.RoleAddForm;
import com.example.hrh.module.sys.dto.form.roles.RoleUpdateForm;

import com.example.hrh.module.sys.service.RoleService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/29 0029 09:35
 */
@RestController
@RequestMapping("role")
public class RoleController extends BaseController{

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMapper roleMapper;


    @GetMapping("all")
    public Object all(Integer status) {
        return roleMapper.findAllByStatus(status);
    }

    /**
     * TODO
     *
     * @param userId
     * @return
     */
    @GetMapping("{userId}/roles")
    public Object getUserRoleFlag(@PathVariable("userId") String userId) {
        return roleService.getUserRoleFlags(userId);
    }

    /**
     * 新建角色
     *
     * @return
     */
    @PostMapping("addition")
    public Object addition(@Valid RoleAddForm form, BindingResult bindingResult) {

        ValidateUtils.validate(bindingResult);
        Role entity = form.trainToEntity();

        if (null != roleMapper.findByFlag(entity.getRoleFlag())) {
            throw new FormValidException("该角色标识已存在");
        }

        entity.setCreateTime(new Date());
        entity.setStatus(1);

        int resultCode = roleMapper.save(entity);
        return resultCode > 0 ? HttpJsonUtils.buildSuccess("新建成功！", entity) : HttpJsonUtils.ERROR;
    }

    @GetMapping("flag/{flag}")
    public Object findByFlag(@PathVariable("flag") String flag) {
        return roleMapper.findByFlag(flag);
    }

    /**
     * 修改角色状态
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("{id}/{status}")
    public Object updateStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status) {

        int resultCode = roleMapper.updateStatus(id, status);
        return resultCode > 0 ? HttpJsonUtils.OK : HttpJsonUtils.ERROR;
    }

    /**
     * 编辑角色
     *
     * @return
     */
    @PostMapping("modify")
    public Object modify(@Valid RoleUpdateForm form, BindingResult bindingResult) {

        ValidateUtils.validate(bindingResult);
        Role temp = form.trainToEntity();
        Role entity = roleMapper.findOne(form.getId());

        if (null != roleMapper.findByFlag(temp.getRoleFlag()) && !temp.getRoleFlag().equals(entity.getRoleFlag())) {
            throw new FormValidException("该角色标识已存在");
        }

        ReflectUtils.updateFieldByClass(temp, entity);

        int resultCode = roleMapper.update(entity);
        return resultCode > 0 ? HttpJsonUtils.buildSuccess("修改成功！", entity) : HttpJsonUtils.ERROR;
    }

    @GetMapping("type")
    public Object getEntityType() {
        return roleService.getEntityType();
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Object getOne(@PathVariable("id") Long id) {
        return roleMapper.findOne(id);
    }

    @GetMapping("{id}/menus")
    public Object getRoleWithMenu(@PathVariable("id") Long id) {
        return roleService.getRoleWithMenu(id);
    }

    @PostMapping("/menus/modification")
    public Object menuModify(Long id , String menuIdSet){
        if (null == id) {
            throw new FormValidException("表单数据不完整！");
        }
        Set<Long> idSet = new HashSet<>();
        // 分割id
        separateStringToLongSet(menuIdSet,idSet);

        int resultCode = roleMapper.removeMenus(id);
        if (idSet.isEmpty()) {
            return resultCode > 0 ? HttpJsonUtils.buildSuccess("移除所有的目录，没有新关联目录！") : HttpJsonUtils.ERROR;
        }

        resultCode = roleMapper.associateMenus(id, idSet);
        return resultCode > 0 ? HttpJsonUtils.buildSuccess("成功关联所有目录！") : HttpJsonUtils.ERROR;
    }

    @GetMapping("userGroup/{id}")
    public Object getRoleWithUserGroup(@PathVariable Long id){
        return roleService.findOneWithUserGroup(id);
    }
}
