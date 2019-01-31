package com.example.hrh.module.sys.dao.entities;/**
 * Created by Administrator on 2018/11/10 0010.
 */

import com.example.hrh.module.common.annotation.Entity;
import com.example.hrh.module.common.dao.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/10 0010 09:22
 */
@Entity(pre = "sys_")
public class ResourcePerm extends BaseEntity{

    /**
     *  对象类别主键
     */
    private String entityKey;

    /**
     *  业务Key Type.id
     */
    private String businessKey;

    /**
     *  可见权限 userId;userId
     */
    @Column(columnDefinition = "TEXT")
    private String view;

    /**
     *  可见可编辑权限 ：userId;userId
     */
    @Column(columnDefinition = "TEXT")
    private String modify;

    private List<ResourcePerm> subPerm;

    public List<ResourcePerm> getSubPerm() {
        return subPerm;
    }

    public void setSubPerm(List<ResourcePerm> subPerm) {
        this.subPerm = subPerm;
    }

    public String getEntityKey() {
        return entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getModify() {
        return modify;
    }

    public void setModify(String modify) {
        this.modify = modify;
    }

}
