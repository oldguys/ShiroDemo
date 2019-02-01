#SpringBoot+Shiro 配置


> shiro是一款轻量级的RBAC权限框架，主要使用于权限管控。本文主要介绍shiro与SpringBoot集成的基本配置，以及一部分配置异常。
>
>GitHub  [https://github.com/oldguys/ShiroDemo](https://github.com/oldguys/ShiroDemo)


######  基本配置
1. 实现于:org.apache.shiro.realm.AuthorizingRealm 的Realm 用于登录校验以及授权
2. 实现于:org.apache.shiro.authc.credential.SimpleCredentialsMatcher 用于密码校验
3. org.apache.shiro.mgt.DefaultSecurityManager 权限管理
4. org.apache.shiro.spring.LifecycleBeanPostProcessor 
5. org.apache.shiro.spring.web.ShiroFilterFactoryBean shiro自带权限拦截器（shiro 自带的登录拦截，权限拦截等。）


```
@Configuration
public class ShiroConfiguration {


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

}

```
######  开启注解式配置（在基本配置的基础上进行配置）
```

@Configuration
public class ShiroConfiguration {

    // 省略基础配置部分.................................
    
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

}

```

实现Shiro自定义Realm
```
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserEntityMapper userEntityMapper;
    @Autowired
    private RoleService roleService;

    /**
     *  授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String userId = (String) principals.getPrimaryPrincipal();
        UserRoleFlag userRoleFlag = roleService.getUserRoleFlags(userId);

        return new SimpleAuthorizationInfo(userRoleFlag.getRoleFlags());
    }

    /**
     *  登录认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
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
```

###### 扩展配置：配置RememberMe 配置

属性文件 application.yml
```
shiro:
  cipher-key: f/SY5TIve5WWzT4aQlABJA==
  cookie-name: shiro-cookie
```

com.example.hrh.module.configs.ShiroConfiguration.RememberMeConfiguration
扩展点: 内部类不需要 **@Configuration**，类中的  **@Bean** 依然会注入到Spring容器中
```
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
```

##### 配置自定义Session，例如实现redis统一Session。
shiro默认使用org.apache.shiro.session.mgt.eis.MemorySessionDAO
可以使用实现自定义Session类。
```
@Configuration
public class ShiroSessionConfiguration {

    /**
     *  自定义Session DAO
     * @return
     */
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

}

```