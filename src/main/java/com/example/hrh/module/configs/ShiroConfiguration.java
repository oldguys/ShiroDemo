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

    /**
     *  配置 密码认证器
     *  1.可以使用 Shiro提供的 密码认证器
     *  2. 自定义密码认证器
     *
     *  不配置会抛出异常：org.apache.shiro.crypto.CryptoException: Unable to execute 'doFinal' with cipher instance [javax.crypto.Cipher@48c22c79].
     * @return
     */
    @Bean
    public SimpleCredentialsMatcher HashedCredentialsMatcher() {

        SimpleCredentialsMatcher credentialsMatcher = new ShiroCredentialsMatcher();
//        SimpleCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("SHA-256");
        return credentialsMatcher;
    }

    /**
     *  shiro的核心，所有的授权认证都在这里处理。
     *  此处使用自定义的认证器
     *
     * @param credentialsMatcher
     * @return
     */
    @Bean
    public ShiroRealm realm(SimpleCredentialsMatcher credentialsMatcher) {

        ShiroRealm realm = new ShiroRealm();
        // 配置 密码认证器，用于匹配密码
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    /**
     *  Shiro核心处理类，所有的类都围绕着这个处理器进行执行
     * @param realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm realm) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
        // 注入到全局变量，是的SecurityUtils可以使用静态方法来调用
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    /**
     *  生命周期维护容器
     *  必须在其他4个容器之后注入。不然会抛异常。
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     *  权限拦截器
     *   主要用于管控URL拦截权限。
     *   分为两个部分:拦截器Map,与 拦截器Map关联映射URL
     * @param securityManager
     * @return
     * @throws Exception
     */
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

        /**
         *  可选
         *  不配置会使用默认的cookie名称
         * @return
         */
        @Bean
        public SimpleCookie simpleCookie() {

            COOKIE_NAME = StringUtils.isEmpty(COOKIE_NAME) ? "shiro-cookie" : COOKIE_NAME;
            SimpleCookie cookie = new SimpleCookie(COOKIE_NAME);
            return cookie;
        }

        /**
         *  配置cookie管理器
         * @param cookie
         * @param securityManager
         * @return
         */
        @Bean
        public CookieRememberMeManager cookieRememberMeManager(Cookie cookie, DefaultWebSecurityManager securityManager) {

            CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
            cookieRememberMeManager.setCookie(cookie);

            /**
             * 不指定固定值，则会在每次系统启动时自动生成新的秘钥
             *  新的秘钥解密不要旧秘钥的Cookie，导致校验失败
             */
            Base64CipherKey = StringUtils.isEmpty(Base64CipherKey) ? "f/SY5TIve5WWzT4aQlABJA==" : Base64CipherKey;
            byte[] cipherKey = Base64.decode(Base64CipherKey);

            /**
             *  配置密码编译器
             */
            cookieRememberMeManager.setCipherService(new AesCipherService());
            cookieRememberMeManager.setCipherKey(cipherKey);

            // 注入到 securityManager
            securityManager.setRememberMeManager(cookieRememberMeManager);

            // 配置cookie失效时间 （-1 为关闭浏览器则关闭）
//            cookie.setMaxAge(-1);

            return cookieRememberMeManager;
        }
    }

}
