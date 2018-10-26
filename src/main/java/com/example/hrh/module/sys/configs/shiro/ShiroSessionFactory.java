package com.example.hrh.module.sys.configs.shiro;/**
 * Created by Administrator on 2018/10/18 0018.
 */

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SimpleSession;

import javax.servlet.http.HttpSession;

/**
 * @Description: 自定义SessionFactory，主要用于自定义Session的构建
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/18 0018 16:27
 */
public class ShiroSessionFactory implements SessionFactory {


    @Override
    public Session createSession(SessionContext initData) {
        return new SimpleSession(initData.getHost());
    }
}
