#Shiro使用自定义注解+AOP进行统一权限控制

>
>GitHub  [https://github.com/oldguys/ShiroDemo](https://github.com/oldguys/ShiroDemo)

自定义注解:com.example.hrh.module.sys.aop.annonation.PermControl
```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermControl {

	/**
	 *  权限枚举
	 * @return
	 */
	PermType[] value();

	/**
	 *  判定逻辑 OR & AND
	 * @return
	 */
	Logical logical() default Logical.AND;
}
```
自定义注解:com.example.hrh.module.sys.configs.shiro.PermType
```
public enum PermType {

    ADMIN("admin", "超级管理员"),
    SUB_ADMIN("sub_admin", "管理员"),
    JDBC_FLAG("jdbc-flag", "JDBC管控");

    PermType(String flag, String value) {
        this.flag = flag;
        this.value = value;
    }

    public static List<String> keySet() {
        List<String> flagSet = new ArrayList<>();
        PermType[] types = values();
        for (PermType type : types) {
            flagSet.add(type.getFlag());
        }
        return flagSet;
    }

    private String flag;

    private String value;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
```

AOP 控制器: com.example.hrh.module.sys.aop.ShiroAspect
```
@Aspect
@Component
public class ShiroAspect {

    /**
     *  角色控制服务
     */
    @Autowired
    private RoleService roleService;

    @Pointcut("@annotation(com.example.hrh.module.sys.aop.annonation.PermControl)")
    public void pointCut() {
    }

    /**
     *  前置条件，已经登录
     * @param joinPoint
     */
    @Before("pointCut()")
    @RequiresAuthentication
    public void before(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        PermControl permControl = signature.getMethod().getAnnotation(PermControl.class);

        boolean jdbcFlag = false;

        PermType[] permTypes = permControl.value();
        List<String> keySet = new ArrayList<>(permControl.value().length);
        for (PermType type : permTypes) {
            keySet.add(type.getFlag());

            // jdbc权限控制
            if(type.equals(PermType.JDBC_FLAG)){
                jdbcFlag = true;
            }
        }

        /**
         *  进行多种复杂情况的权限控制。
         */
        if(jdbcFlag){
            // TODO JDBC 权限控制
            return;
        }

        if (ShiroUtils.hasRoles(keySet, permControl.logical().equals(Logical.AND) ? true : false)) {
            Log4jUtils.getInstance(getClass()).info("具有权限:" + keySet);
        } else {
            throw new PermException("权限不足！");
        }
    }
}
```

自定义权限异常 com.example.hrh.module.sys.exceptions.PermException
```
public class PermException extends UnauthorizedException {

    public PermException(String message){
        super(message);
    }

}
```

SpringBoot 统一权限异常处理
com.example.hrh.module.sys.handle.ShiroExceptionHandle
```
@RestControllerAdvice
public class ShiroExceptionHandle {

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Object unauthenticatedException(UnauthenticatedException exception) {

        System.out.println("message:" + exception.getMessage());
        System.out.println("权限异常:没有认证");
        return "没有权限。。。。。";
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Object unauthorizedException(UnauthorizedException exception) {

        System.out.println("message:" + exception.getMessage());
        System.out.println("权限异常:没有授权登录");
        return "没有授权登录。。。。。";
    }
}
```