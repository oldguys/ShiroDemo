package com.example.hrh.module.configs;/**
 * Created by Administrator on 2018/10/17 0017.
 */

import com.example.hrh.module.sys.configs.shiro.ShiroCredentialsMatcher;
import com.example.hrh.module.sys.configs.shiro.ShiroRealm;
import com.example.hrh.module.sys.interceptors.ShiroPermFilter;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;

import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;


import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import org.apache.shiro.web.servlet.*;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/17 0017 13:48
 */
@Configuration
public class ShiroConfiguration {

    /**
     * @return
     */
    @Bean
    public DefaultPasswordService passwordService() {
        DefaultPasswordService bean = new DefaultPasswordService();
        return bean;
    }

    @Bean
    public SimpleCredentialsMatcher HashedCredentialsMatcher() {
        System.out.println(HashedCredentialsMatcher.class.getSimpleName());

        SimpleCredentialsMatcher credentialsMatcher = new ShiroCredentialsMatcher();
//        SimpleCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("SHA-256");
        return credentialsMatcher;
    }

    @Bean
    public ShiroRealm realm(SimpleCredentialsMatcher credentialsMatcher) {

        ShiroRealm realm = new ShiroRealm();
        realm.setCredentialsMatcher(credentialsMatcher);

        System.out.println(ShiroRealm.class.getSimpleName());

        return realm;
    }

    /**
     * @param realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm realm) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
        // 注入到全局变量
        SecurityUtils.setSecurityManager(securityManager);

        return securityManager;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) throws Exception {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        // 配置自定义Filter
        Map<String,Filter> filterMaps = new HashedMap(16);
        filterMaps.put("shiroPermFilter", new ShiroPermFilter());
        bean.setFilters(filterMaps);

        //配置映射
        Map<String,String> urlChainMap = new HashMap<>(16);
        urlChainMap.put("/test","shiroPermFilter");
        urlChainMap.put("/perm/normal","shiroPermFilter");

        bean.setFilterChainDefinitionMap(urlChainMap);
        return bean;
    }

    public DefaultFilterChainManager defaultFilterChainManager(){
        DefaultFilterChainManager bean = new DefaultFilterChainManager();
        return bean;
    }

    /**
     * 开启注解式 权限拦截
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator bean = new DefaultAdvisorAutoProxyCreator();
        bean.setProxyTargetClass(true);
        return bean;
    }

    /**
     * 开启注解式 权限拦截
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
        bean.setSecurityManager(securityManager);
        return bean;
    }

    /**
     * RememberMe 配置
     */
    public class RememberMeConfiguration {

        @Value("${shiro.cookie-name}")
        private String COOKIE_NAME;
        @Value("${shiro.cipher-key}")
        private String Base64CipherKey;

        @Bean
        public SimpleCookie simpleCookie() {

            COOKIE_NAME = StringUtils.isEmpty(COOKIE_NAME) ? "shiro-cookie" : COOKIE_NAME;
            SimpleCookie cookie = new SimpleCookie(COOKIE_NAME);
            return cookie;
        }

        @Bean
        public CookieRememberMeManager cookieRememberMeManager(Cookie cookie, DefaultWebSecurityManager securityManager) {

            CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
            cookieRememberMeManager.setCookie(cookie);

            Base64CipherKey = StringUtils.isEmpty(Base64CipherKey) ? "f/SY5TIve5WWzT4aQlABJA==" : Base64CipherKey;
            byte[] cipherKey = Base64.decode(Base64CipherKey);
            cookieRememberMeManager.setCipherService(new AesCipherService());
            cookieRememberMeManager.setCipherKey(cipherKey);

            // 注入到 securityManager
            securityManager.setRememberMeManager(cookieRememberMeManager);

            return cookieRememberMeManager;
        }
    }

}
