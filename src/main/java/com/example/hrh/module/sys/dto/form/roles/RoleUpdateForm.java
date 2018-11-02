package com.example.hrh.module.sys.dto.form.roles;



import com.example.hrh.module.sys.dao.entities.Role;

import javax.validation.constraints.NotNull;

public class RoleUpdateForm extends RoleAddForm {

	@NotNull(message = "角色ID 不能为空！")
	private Long id;

	@Override
	public Role trainToEntity() {
		Role role = super.trainToEntity();
		
		role.setId(id);
		
		return role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
