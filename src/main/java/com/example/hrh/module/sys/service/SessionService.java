package com.example.hrh.module.sys.service;/**
 * Created by Administrator on 2018/10/22 0022.
 */

import com.alibaba.druid.support.json.JSONUtils;
import com.example.hrh.module.common.utils.Log4jUtils;
import com.example.hrh.module.common.utils.ShiroUtils;
import com.example.hrh.module.sys.configs.shiro.ShiroSessionDAO;
import com.example.hrh.module.sys.dao.entities.SessionEntity;
import com.example.hrh.module.sys.dao.jpas.SessionEntityMapper;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/22 0022 09:31
 */
@Service
public class SessionService {

    public static final String SESSION_ACTIVE_FLAG = "SESSION_ACTIVE_FLAG";

    @Autowired
    private SessionEntityMapper sessionEntityMapper;
    @Autowired
    private ShiroSessionDAO sessionDAO;

    public void initUserSession() {

        if (ShiroUtils.isLogin()) {

            String currentSessionId = String.valueOf(ShiroUtils.getCurrentSession().getId());

            // 通知相同账号下的其他用户下线
            String username = (String) ShiroUtils.getCurrentUser();
            List<SessionEntity> sessionEntityList = sessionEntityMapper.findActiveSessionByUsername(username);
            if (!sessionEntityList.isEmpty()) {
                Set<Long> idSet = new HashSet<>();
                sessionEntityList.forEach(obj -> {
                    idSet.add(obj.getId());
                    Session session = sessionDAO.getSession(obj.getSessionId());

                    if(obj.getSessionId().equals(currentSessionId)){
                        Log4jUtils.getInstance(getClass()).info("session ["+obj.getSessionId()+"] 相同Session！");
                        return;
                    }
                    if (null != session ) {
                        Log4jUtils.getInstance(getClass()).info("session ["+obj.getSessionId()+"] 设置退出！");
                        session.setAttribute(SESSION_ACTIVE_FLAG, false);
                    }else {
                        Log4jUtils.getInstance(getClass()).info("session ["+obj.getSessionId()+"] 已失效！");
                    }
                });
                sessionEntityMapper.updateStatusBatch(idSet, 0);
            }

            // 生成新的指定用户Session
            SessionEntity sessionEntity = new SessionEntity();

            long currentTime = System.currentTimeMillis();
            sessionEntity.setSessionId(currentSessionId);
            sessionEntity.setCreateTime(new Date(currentTime));
            sessionEntity.setStatus(1);
            sessionEntity.setUsername(username);
            sessionEntity.setExpireTime(new Date(currentTime + 3600 * 1000));
            sessionEntity.setData(JSONUtils.toJSONString(ShiroUtils.getCurrentUser()));

            sessionEntityMapper.insert(sessionEntity);

        }

    }
}
