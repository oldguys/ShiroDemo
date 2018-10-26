package com.example.hrh.module.sys.configs.shiro;/**
 * Created by Administrator on 2018/10/17 0017.
 */

import com.example.hrh.module.sys.dao.entities.UserEntity;
import com.example.hrh.module.sys.dao.jpas.UserEntityMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/17 0017 11:29
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        System.out.println("权限校验:" + principals.getPrimaryPrincipal());

        Set<String> roles = new HashSet<>();
        roles.add("admin");

        return new SimpleAuthorizationInfo(roles);
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
