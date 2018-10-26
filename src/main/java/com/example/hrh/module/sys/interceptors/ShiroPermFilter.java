package com.example.hrh.module.sys.interceptors;/**
 * Created by Administrator on 2018/10/23 0023.
 */

import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/23 0023 19:34
 */
//@Component
public class ShiroPermFilter extends AdviceFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        System.out.println(getClass().getSimpleName() + ":preHandle");
        return true;
    }

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println(getClass().getSimpleName() + ":postHandle");
    }

    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
        System.out.println(getClass().getSimpleName() + ":afterCompletion");
    }
}
