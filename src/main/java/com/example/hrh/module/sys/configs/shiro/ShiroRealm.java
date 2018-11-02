package com.example.hrh.module.sys.configs.shiro;/**
 * Created by Administrator on 2018/10/17 0017.
 */

import com.example.hrh.module.sys.dao.entities.UserEntity;
import com.example.hrh.module.sys.dao.jpas.RoleMapper;
import com.example.hrh.module.sys.dao.jpas.UserEntityMapper;
import com.example.hrh.module.sys.dto.json.user.UserRoleFlag;
import com.example.hrh.module.sys.service.RoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/17 0017 11:29
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserEntityMapper userEntityMapper;
    @Autowired
    private RoleService roleService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String userId = (String) principals.getPrimaryPrincipal();
        UserRoleFlag userRoleFlag = roleService.getUserRoleFlags(userId);

        return new SimpleAuthorizationInfo(userRoleFlag.getRoleFlags());
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String principal = (String) token.getPrincipal();
        System.out.println("登录校验:" + principal);

        UserEntity entity = userEntityMapper.findByUsername(principal);

        if (null == entity) {
            throw new UnknownAccountException("未找到该用户！");
        }

        System.out.println("获取到用户进行校验....");
        return new SimpleAuthenticationInfo(principal, entity.getPassword(), getName());
    }
}
