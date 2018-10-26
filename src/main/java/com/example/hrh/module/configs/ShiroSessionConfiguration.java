package com.example.hrh.module.configs;/**
 * Created by Administrator on 2018/10/22 0022.
 */

import com.example.hrh.module.sys.configs.shiro.ShiroSessionDAO;
import com.example.hrh.module.sys.configs.shiro.ShiroSessionFactory;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Description: 自定义Session
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/22 0022 09:36
 */
@Configuration
public class ShiroSessionConfiguration {

    @Bean
    public ShiroSessionDAO shiroSessionDAO(){
        return new ShiroSessionDAO();
    }

    @Bean
    public DefaultWebSessionManager defaultWebSessionManager(DefaultWebSecurityManager securityManager,ShiroSessionDAO shiroSessionDAO) {

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 注入 到权限管理器
        securityManager.setSessionManager(sessionManager);
        // 配置 自定义 Session
        sessionManager.setSessionDAO(shiroSessionDAO);
        return sessionManager;
    }

    //    @Bean
    public ShiroSessionFactory ShiroSessionFactory() {
        return new ShiroSessionFactory();
    }

    //    @Bean
    public ShiroSessionDAO sessionDAO() {
        return new ShiroSessionDAO();
    }


    //    @Bean
    public DefaultWebSessionManager defaultWebSessionManager(DefaultWebSecurityManager securityManager
            , ShiroSessionFactory sessionFactory
            , ShiroSessionDAO sessionDAO
    ) {

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        // 注入自定义Session 构建器
        sessionManager.setSessionFactory(sessionFactory);
        sessionManager.setSessionDAO(sessionDAO);
        // 注入 到权限管理器
        securityManager.setSessionManager(sessionManager);

        return sessionManager;
    }
}
