package com.example.hrh.module.sys.aop.annonation;

import com.example.hrh.module.sys.configs.shiro.PermType;
import org.apache.shiro.authz.annotation.Logical;

import java.lang.annotation.*;
import java.util.List;


/**
 * 	用户 超级管理员 权限AOP
 * @author ren
 *
 */
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
