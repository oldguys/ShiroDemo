package com.example.hrh.module.sys.controllers;


import com.example.hrh.module.common.controllers.BaseController;
import com.example.hrh.module.common.exceptions.FormValidException;
import com.example.hrh.module.common.utils.HttpJsonUtils;
import com.example.hrh.module.common.utils.ReflectUtils;
import com.example.hrh.module.common.utils.ValidateUtils;

import com.example.hrh.module.sys.dao.entities.UserGroup;
import com.example.hrh.module.sys.dao.jpas.UserEntityMapper;
import com.example.hrh.module.sys.dao.jpas.UserGroupMapper;

import com.example.hrh.module.sys.dto.form.usergroup.UserGroupAddForm;

import com.example.hrh.module.sys.dto.form.usergroup.UserGroupUpdateForm;

import com.example.hrh.module.sys.dto.json.usergroup.UserGroupInfo;
import com.example.hrh.module.sys.dto.json.usergroup.UserGroupWithRoles;
import com.example.hrh.module.sys.service.UserGroupService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/29 0029 09:37
 */
@RestController
@RequestMapping("userGroup")
public class UserGroupController extends BaseController {

    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private UserGroupService userGroupService;


    @GetMapping("all")
    public Object all(Integer status) {
        return userGroupMapper.findAllByStatus(status);
    }

    /**
     * 修改用户组状态
     *
     * @param groupId
     * @param status
     * @return
     */
    @PostMapping("{groupId}/{status}")
    public Object updateStatus(@PathVariable("groupId") Long groupId, @PathVariable("status") Integer status) {

        int resultCode = userGroupMapper.updateStatus(groupId, status);
        return resultCode > 0 ? HttpJsonUtils.OK : HttpJsonUtils.ERROR;
    }


    /**
     * 新建用户组
     *
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("addition")
    @Transactional(rollbackOn = Exception.class)
    public Object addition(@Valid UserGroupAddForm form, BindingResult bindingResult) {

        ValidateUtils.validate(bindingResult);
        UserGroup userGroup = form.trainToEntity();

        userGroup.setCreateTime(new Date());
        userGroup.setStatus(1);
        int resultCode = userGroupMapper.save(userGroup);

        return resultCode > 0 ? HttpJsonUtils.buildSuccess("创建成功！", userGroup) : HttpJsonUtils.ERROR;
    }

    /**
     * 获取用户组的详细信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("{id}")
    public Object getUserGroup(@PathVariable("id") Long id) {
        return userGroupMapper.findOne(id);
    }

    /**
     * 编辑用户组
     *
     * @return
     */
    @PostMapping("modify")
    public Object modify(@Valid UserGroupUpdateForm form, BindingResult bindingResult) {

        ValidateUtils.validate(bindingResult);
        UserGroup temp = form.trainToEntity();
        UserGroup entity = userGroupMapper.findOne(form.getId());
        ReflectUtils.updateFieldByClass(temp, entity);

        int resultCode = userGroupMapper.update(entity);
        return resultCode > 0 ? HttpJsonUtils.buildSuccess("编辑成功！", entity) : HttpJsonUtils.ERROR;
    }

    @GetMapping("type")
    public Object getEntityType() {
        return userGroupService.getEntityType();
    }

    @GetMapping("{id}/role")
    public UserGroupWithRoles getUerGroupWithRole(@PathVariable Long id) {
        return userGroupService.getUserGroupWithRoles(id);
    }

    @PostMapping("/roles/modification")
    @Transactional(rollbackOn = Exception.class)
    public Object roleModify(Long id, String roleIdSet) {

        if (null == id) {
            throw new FormValidException("表单数据不完整！");
        }
        Set<Long> idSet = new HashSet<>();
        // 分割id
        separateStringToLongSet(roleIdSet, idSet);

        int resultCode = userGroupMapper.removeRoles(id);
        if (idSet.isEmpty()) {
            return resultCode > 0 ? HttpJsonUtils.buildSuccess("移除所有的用户组，没有关联角色列表！") : HttpJsonUtils.ERROR;
        }

        resultCode = userGroupMapper.associateRoles(id, idSet);
        return resultCode > 0 ? HttpJsonUtils.buildSuccess("成功关联所有角色！") : HttpJsonUtils.ERROR;
    }

    @GetMapping("{id}/users")
    public Object getUserGroupWithUsers(@PathVariable Long id) {
        return userGroupMapper.findUserGroupWithUsers(id);
    }

    /**
     *  关联单用户
     * @param groupId
     * @param userId
     * @return
     */
    @PostMapping("associate/{groupId}/{userId}")
    @Transactional(rollbackOn = Exception.class)
    public Object associateUser(@PathVariable Long groupId, @PathVariable String userId) {

        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(userId);
        int resultCode = userGroupMapper.associateUsers(groupId, userIdSet);
        UserGroup entity = userGroupMapper.findUserGroupWithUsers(groupId);

        return resultCode > 0 ? HttpJsonUtils.buildSuccess("更新成功！", entity) : HttpJsonUtils.ERROR;
    }

    /**
     *  关联单用户
     * @param groupId
     * @param userId
     * @return
     */
    @PostMapping("remove/associate/{groupId}/{userId}")
    @Transactional(rollbackOn = Exception.class)
    public Object removeAssociateUser(@PathVariable Long groupId, @PathVariable String userId) {

        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(userId);
        int resultCode = userGroupMapper.removeAssociateUsers(groupId, userIdSet);
        UserGroup entity = userGroupMapper.findUserGroupWithUsers(groupId);

        return resultCode > 0 ? HttpJsonUtils.buildSuccess("更新成功！", entity) : HttpJsonUtils.ERROR;
    }

    /**
     *  更新用户组关联用户列表
     * @param id
     * @param userIds
     * @return
     */
    @PostMapping("/users/modification")
    @Transactional(rollbackOn = Exception.class)
    public Object associateUsers(@PathVariable Long id, @PathVariable String userIds){
        if (null == id) {
            throw new FormValidException("表单数据不完整！");
        }
        Set<String> idSet = new HashSet<>();
        // 分割id
        separateStringToStringSet(userIds, idSet);

        int resultCode = userGroupMapper.removeUsers(id);
        if (idSet.isEmpty()) {
            return resultCode > 0 ? HttpJsonUtils.buildSuccess("移除所有的用户组，没有关联用户列表！") : HttpJsonUtils.ERROR;
        }
        // 更新关联
        resultCode = userGroupMapper.associateUsers(id, idSet);
        // 获取更新结果
        UserGroup entity = userGroupMapper.findUserGroupWithUsers(id);
        return resultCode > 0 ? HttpJsonUtils.buildSuccess("更新成功！", entity) : HttpJsonUtils.ERROR;
    }

    /**
     *  获取指定用户组的详细信息
     * @param id
     * @return
     */
    @GetMapping("{id}/all")
    public Object getUserGroupInfo(@PathVariable Long id){
        return userGroupService.getUserInfo(id);
    }

    @GetMapping("user/{userId}")
    public Object getUserWithUserGroup(@PathVariable String userId){
        return userGroupService.getUserWithUserGroup(userId);
    }


}
