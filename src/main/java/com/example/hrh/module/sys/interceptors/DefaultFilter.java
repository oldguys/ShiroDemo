package com.example.hrh.module.sys.interceptors;/**
 * Created by Administrator on 2018/10/24 0024.
 */

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/24 0024 20:53
 */
@Component
public class DefaultFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("测试....");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("销毁");
    }
}
