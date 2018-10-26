package com.example.hrh.module.sys.dao.entities;/**
 * Created by Administrator on 2018/10/18 0018.
 */


import com.baomidou.mybatisplus.annotation.TableName;
import com.example.hrh.module.common.annotation.Entity;
import com.example.hrh.module.common.dao.entities.BaseEntity;


import javax.persistence.Column;


import java.util.Date;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/18 0018 16:07
 */
@Entity(pre = "sys_")
@TableName(value = "sys_session_entity" )
public class SessionEntity extends BaseEntity{

    private String sessionId;

    private String username;

    @Column(columnDefinition = "TEXT")
    private String data;

    private Date  expireTime;

    public String getUsername() {
        return username;
    }

    public String getData() {
        return data;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "sessionId='" + sessionId + '\'' +
                ", username='" + username + '\'' +
                ", data='" + data + '\'' +
                ", expireTime=" + expireTime +
                "} " + super.toString();
    }
}
