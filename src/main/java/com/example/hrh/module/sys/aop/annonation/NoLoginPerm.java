package com.example.hrh.module.sys.aop.annonation;

import java.lang.annotation.*;


/**
 * 	跳过登录校验
 * @author King
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoLoginPerm {

}
