package com.example.hrh.module.sys.controllers;/**
 * Created by Administrator on 2018/10/18 0018.
 */

import com.example.hrh.module.sys.aop.annonation.PermControl;
import com.example.hrh.module.sys.configs.shiro.PermType;
import com.example.hrh.module.sys.service.SessionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/18 0018 08:46
 */
@RestController
public class TestController {

    @Autowired
    private SessionService sessionService;

    @RequestMapping("session")
    public String session(HttpSession httpSession) {

        System.out.println("sessionId:" + httpSession.getId());

        return null;
    }

    @RequestMapping("test")
    public String test(String username, String password) {

        System.out.println("username:" + username);
        System.out.println("password:" + password);

        SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, true));

        sessionService.initUserSession();


        return "登录结果:" + SecurityUtils.getSubject().isAuthenticated();
    }


    @RequiresRoles("admin")
    @RequestMapping("perm/admin")
    public String testAdmin() {
        System.out.println(SecurityUtils.getSubject().getPrincipal());
        return "admin权限";
    }

    @RequiresRoles("normal")
    @RequestMapping("perm/normal")
    public String testNormal() {

        System.out.println(SecurityUtils.getSubject().getPrincipal());

        return "normal 权限";
    }

    @PermControl(PermType.ADMIN)
    @RequestMapping("aop/admin")
    public String testAopPerm() {
        return "aop/admin";
    }

    @PermControl(PermType.SUB_ADMIN)
    @RequestMapping("aop/subAdmin")
    public String testAopSubAdmin() {
        return "aop/subAdmin";
    }

    @PermControl(value = {PermType.ADMIN, PermType.SUB_ADMIN})
    @RequestMapping("aop/adminAndSub")
    public String testAopAdminAndSub() {
        return "aop/adminAndSub";
    }

    @PermControl(value = {PermType.ADMIN, PermType.SUB_ADMIN} ,logical = Logical.OR)
    @RequestMapping("aop/adminOrSub")
    public String testAopAdminOrSub() {
        return "aop/adminOrSub";
    }

}