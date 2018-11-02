package com.example.hrh.module.sys.dto.form.dictionary;/**
 * Created by Administrator on 2018/10/30 0030.
 */

import com.example.hrh.module.sys.dao.entities.Dictionary;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/30 0030 11:33
 */
public class DictionaryUpdateForm extends DictionaryAddForm {

    @NotNull(message = "id不能为空！")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Dictionary trainToEntity() {
        Dictionary entity = super.trainToEntity();
        entity.setId(id);

        return entity;
    }
}
