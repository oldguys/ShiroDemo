package com.example.hrh.module.sys.dto.json.menu;/**
 * Created by Administrator on 2018/11/5 0005.
 */

import com.example.hrh.module.sys.dao.entities.Menu;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/5 0005 20:01
 */
public class MenuInfoItem extends Menu {

    private Long parentId;

    private String parentName;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return "MenuInfoItem{" +
                "parentId=" + parentId +
                ", parentName='" + parentName + '\'' +
                "} " + super.toString();
    }
}
