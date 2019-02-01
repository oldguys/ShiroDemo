#基于shiro RBAC的表设计

> 权限控制的核心是RBAC，但是同个用户可能具有多个角色，一个角色也可能关联多个用户。而这多与多的关联关系维护起来特别麻烦，依照指定的角色具有的功能是相似的，所以在角色与用户之间，添加了用户组来进行中介进行管控。
>
>GitHub  [https://github.com/oldguys/ShiroDemo](https://github.com/oldguys/ShiroDemo)

![rbac.jpg](https://upload-images.jianshu.io/upload_images/14387783-bcff3976a0e3a62f.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

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

获取角色权限SQL
```
		SELECT
		d.*
		FROM
			sys_user_user_group a
		INNER JOIN sys_user_group b ON a.user_group_id = b.id
		INNER JOIN sys_role_user_group c ON a.user_group_id = c.user_group_id
		INNER JOIN sys_role d ON c.role_id = d.id
		WHERE
			b.`status` = 1
		AND d.`status` = 1
		AND a.user_id = #{userId}
```

###### 进行权限验证
com.example.hrh.module.sys.controllers.TestController
必须指定角色权限认证
```
    @RequiresRoles("admin")
    @RequestMapping("perm/admin")
    public String testAdmin() {
        System.out.println(SecurityUtils.getSubject().getPrincipal());
        return "admin权限";
    }
```