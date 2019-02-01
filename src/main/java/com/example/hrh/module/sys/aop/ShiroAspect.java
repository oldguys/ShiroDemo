package com.example.hrh.module.sys.aop;/**
 * Created by Administrator on 2018/10/24 0024.
 */

import com.example.hrh.module.common.utils.Log4jUtils;
import com.example.hrh.module.common.utils.ShiroUtils;
import com.example.hrh.module.sys.aop.annonation.PermControl;
import com.example.hrh.module.sys.configs.shiro.PermType;
import com.example.hrh.module.sys.exceptions.PermException;
import com.example.hrh.module.sys.service.RoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/24 0024 20:59
 */
@Aspect
@Component
public class ShiroAspect {

    /**
     *  角色控制服务
     */
    @Autowired
    private RoleService roleService;

    @Pointcut("@annotation(com.example.hrh.module.sys.aop.annonation.PermControl)")
    public void pointCut() {
    }

    /**
     *  前置条件，已经登录
     * @param joinPoint
     */
    @Before("pointCut()")
    @RequiresAuthentication
    public void before(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        PermControl permControl = signature.getMethod().getAnnotation(PermControl.class);

        boolean jdbcFlag = false;

        PermType[] permTypes = permControl.value();
        List<String> keySet = new ArrayList<>(permControl.value().length);
        for (PermType type : permTypes) {
            keySet.add(type.getFlag());

            // jdbc权限控制
            if(type.equals(PermType.JDBC_FLAG)){
                jdbcFlag = true;
            }
        }

        /**
         *  进行多种复杂情况的权限控制。
         */
        if(jdbcFlag){
            // TODO JDBC 权限控制
            return;
        }

        if (ShiroUtils.hasRoles(keySet, permControl.logical().equals(Logical.AND) ? true : false)) {
            Log4jUtils.getInstance(getClass()).info("具有权限:" + keySet);
        } else {
            throw new PermException("权限不足！");
        }
    }
}
