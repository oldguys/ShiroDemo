package com.example.hrh.module.common.utils;/**
 * Created by Administrator on 2018/10/22 0022.
 */

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.Collection;
import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/22 0022 09:40
 */
public class ShiroUtils {

    /**
     * @param roles
     * @param logicAnd
     * @return
     */
    public static boolean hasRoles(List<String> roles, boolean logicAnd) {

        boolean[] flags = SecurityUtils.getSubject().hasRoles(roles);
        if (logicAnd) {
            for (boolean flag : flags) {
                if (!flag) {
                    return false;
                }
            }
            return true;
        } else {
            for (boolean flag : flags) {
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getCurrentSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Object getCurrentUser() {
        return SecurityUtils.getSubject().getPrincipal();
    }

}
