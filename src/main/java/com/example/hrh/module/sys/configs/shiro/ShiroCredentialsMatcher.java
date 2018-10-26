package com.example.hrh.module.sys.configs.shiro;/**
 * Created by Administrator on 2018/10/17 0017.
 */

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/17 0017 18:05
 */
public class ShiroCredentialsMatcher extends SimpleCredentialsMatcher {

    @Autowired
    private DefaultPasswordService passwordService;

    @Override
    protected boolean equals(Object tokenCredentials, Object accountCredentials) {
        return passwordService.passwordsMatch(tokenCredentials, (String) accountCredentials);
    }
}
