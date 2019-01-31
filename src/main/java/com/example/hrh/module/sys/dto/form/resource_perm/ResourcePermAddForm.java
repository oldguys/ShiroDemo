package com.example.hrh.module.sys.dto.form.resource_perm;/**
 * Created by Administrator on 2018/11/11 0011.
 */

import com.example.hrh.module.common.dto.AbstractTreeForm;

import com.example.hrh.module.sys.dao.entities.ResourcePerm;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/11 0011 16:33
 */
public class ResourcePermAddForm extends AbstractTreeForm<ResourcePerm> {

    @NotBlank(message = "entityKey 不能为空！")
    private String entityKey;

    @NotBlank(message = "bussinessKey 不能为空！")
    private String bussinessKey;

    private String view;

    private String modify;

    @Override
    public ResourcePerm trainToEntity() {

        ResourcePerm entity = new ResourcePerm();

        entity.setEntityKey(entityKey);
        entity.setBusinessKey(bussinessKey);
        entity.setView(view);
        entity.setModify(modify);

        return entity;
    }
}
