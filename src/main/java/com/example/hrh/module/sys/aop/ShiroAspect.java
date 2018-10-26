package com.example.hrh.module.sys.aop;/**
 * Created by Administrator on 2018/10/24 0024.
 */

import com.example.hrh.module.common.utils.Log4jUtils;
import com.example.hrh.module.common.utils.ShiroUtils;
import com.example.hrh.module.sys.aop.annonation.PermControl;
import com.example.hrh.module.sys.configs.shiro.PermType;
import com.example.hrh.module.sys.exceptions.PermException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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

    @Pointcut("@annotation(com.example.hrh.module.sys.aop.annonation.PermControl)")
    public void pointCut() {
    }

    @Before("pointCut()")
    @RequiresAuthentication
    public void before(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        PermControl permControl = signature.getMethod().getAnnotation(PermControl.class);

        PermType[] permTypes = permControl.value();
        List<String> keySet = new ArrayList<>(permControl.value().length);
        for (PermType type : permTypes) {
            keySet.add(type.getFlag());
        }

        if (ShiroUtils.hasRoles(keySet, permControl.logical().equals(Logical.AND) ? true : false)) {
            Log4jUtils.getInstance(getClass()).info("具有权限:" + keySet);
        } else {
            throw new PermException("权限不足！");
        }
    }
}
