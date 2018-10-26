### 基本配置

1. 实现于:org.apache.shiro.realm.AuthorizingRealm 的Realm 用于登录校验以及授权
1. 实现于:org.apache.shiro.authc.credential.SimpleCredentialsMatcher 用于密码校验
1. org.apache.shiro.mgt.DefaultSecurityManager 权限管理
    
        // 注入到全局变量之中
        SecurityUtils.setSecurityManager(securityManager); 

1. org.apache.shiro.spring.LifecycleBeanPostProcessor 
1. org.apache.shiro.spring.web.ShiroFilterFactoryBean shiro自带权限拦截器（shiro 自带的登录拦截，权限拦截等。）

### 开启注解式权限控制

1. org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor

        AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
        // 注入控制器
        bean.setSecurityManager(securityManager); 
1. org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator

        DefaultAdvisorAutoProxyCreator bean = new DefaultAdvisorAutoProxyCreator();
        // 开启代理
        bean.setProxyTargetClass(true);  
        
------

### 开启Cookie RememberMe

1. org.apache.shiro.web.mgt.CookieRememberMeManager 开启Cookie管理器

            // 注入到 securityManager
            securityManager.setRememberMeManager(cookieRememberMeManager);
            // 注入指定加密策略
            cookieRememberMeManager.setCipherService(aesCipherService);
            // 注入指定秘钥
            cookieRememberMeManager.setCipherKey(Base64.decode("f/SY5TIve5WWzT4aQlABJA=="));
            
1. （可选）org.apache.shiro.web.servlet.SimpleCookie

        // 自定义指定名称的Cookie
        SimpleCookie cookie = new SimpleCookie("shiro-cookie");
        // 注入到控制器
        cookieRememberMeManager.setCookie(cookie);
        // 配置cookie失效时间 （-1 为关闭浏览器则关闭）
        cookie.setMaxAge(-1);
        
        
### 开启Session 管理 可以配置自定义Session，并统一一端控制

1. 定制SessionDAO，注入到SessionManager
1. 将SessionManager与SecurityManager相关联

        /***
        * 自定义SessionDAO，可以用于完成指定业务（可以基于Redis，Ecache等进行定制独立存储）
        **/
        @Bean
        public ShiroSessionDAO shiroSessionDAO(){
            return new ShiroSessionDAO();
        }
    
        /**
        * 注入Session管理器
        **/
        @Bean
        public DefaultWebSessionManager defaultWebSessionManager(DefaultWebSecurityManager securityManager,ShiroSessionDAO shiroSessionDAO) {
    
            DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
            // 注入 到权限管理器
            securityManager.setSessionManager(sessionManager);
            // 配置 自定义 Session
            sessionManager.setSessionDAO(shiroSessionDAO);
            return sessionManager;
        }


### 开启URL拦截器

> ShiroFilterFactoryBean 运行原理:
> 1. 调用 org.springframework.beans.factory.FactoryBean.getObject()
> 1. 调用 ShiroFilterFactoryBean.createFilterChainManager() 开始注入拦截管理器
> 1. FilterChainManager先初始化Shiro *内置拦截器* org.apache.shiro.web.filter.mgt.DefaultFilter再开始加载自定义 拦截器以及代码中自己配置的拦截器设置
> 1. 初始化org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver 对路径进行解析，并返回进行解析。
>
> 注意: SpringBoot中实现于 javax.servlet.Filter 只要被注入到容器就会自动进行配置 ，默认所有路径。可能出现错误。
> 个人总结: 没有查看到shiro exclude相关的配置项。使用起来没有自定义注解来的方便。

1. 配置自定义注入到容器

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
        

### 使用自定义注解配置权限控制
com.example.hrh.module.sys.aop.ShiroAspect 拦截切面