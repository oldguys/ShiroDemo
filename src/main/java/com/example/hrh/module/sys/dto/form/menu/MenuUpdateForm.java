package com.example.hrh.module.sys.dto.form.menu;



import com.example.hrh.module.sys.dao.entities.Menu;

import javax.validation.constraints.NotNull;

/**
 * @author huangrenhao
 * @date 2018/8/28
 */
public class MenuUpdateForm extends MenuAddForm {

    @NotNull(message = "ID不能为空！")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Menu trainToEntity() {
        Menu entity = super.trainToEntity();
        entity.setId(id);
        return entity;
    }
}
