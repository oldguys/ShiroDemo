package com.example.hrh.module.sys.dao.entities;/**
 * Created by Administrator on 2018/11/3 0003.
 */

import com.example.hrh.module.common.annotation.Entity;
import com.example.hrh.module.common.dao.entities.BaseEntity;

import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/3 0003 21:27
 */
@Entity(pre = "sys_")
public class TreeNode extends BaseEntity implements Comparable<TreeNode> {

    private String entityName;

    private String entityKey;

    private Long entityId;

    private Long parentEntityId;

    private Long parentId;

    private Integer sort;

    private List<TreeNode> subTreeNodes = Collections.emptyList();

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Long getParentEntityId() {
        return parentEntityId;
    }

    public void setParentEntityId(Long parentEntityId) {
        this.parentEntityId = parentEntityId;
    }

    public String getEntityKey() {
        return entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public List<TreeNode> getSubTreeNodes() {
        return subTreeNodes;
    }

    public void setSubTreeNodes(List<TreeNode> subTreeNodes) {
        this.subTreeNodes = subTreeNodes;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public int compareTo(TreeNode o) {
        return this.sort - o.sort;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "entityName='" + entityName + '\'' +
                ", entityKey='" + entityKey + '\'' +
                ", entityId=" + entityId +
                ", parentEntityId=" + parentEntityId +
                ", parentId=" + parentId +
                ", sort=" + sort +
                "} " + super.toString();
    }
}
