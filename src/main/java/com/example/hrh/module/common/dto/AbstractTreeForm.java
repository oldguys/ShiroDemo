package com.example.hrh.module.common.dto;/**
 * Created by Administrator on 2018/11/11 0011.
 */

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/11 0011 16:36
 */
public abstract class AbstractTreeForm<T> implements Form<T> {

    private Long afterMenuId;

    @NotNull(message = "父级ID不能为空")
    private Long parentId;

    public Long getAfterMenuId() {
        return afterMenuId;
    }

    public void setAfterMenuId(Long afterMenuId) {
        this.afterMenuId = afterMenuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
