package com.example.hrh.module.sys.configs.shiro;/**
 * Created by Administrator on 2018/10/18 0018.
 */


import org.apache.shiro.session.Session;

import org.apache.shiro.session.mgt.eis.MemorySessionDAO;

import java.io.Serializable;


/**
 * @Description: 可以在这里做 分布式统一Session
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/18 0018 17:18
 */
public class ShiroSessionDAO extends MemorySessionDAO {

    public Session getSession(Serializable sessionId){
        return super.doReadSession(sessionId);
    }
}
