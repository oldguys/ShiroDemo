package com.example.hrh.module.sys.dto.form.roles;


import com.example.hrh.module.common.dto.Form;
import com.example.hrh.module.sys.dao.entities.Role;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

public class RoleAddForm implements Form<Role>{

	@NotBlank(message = "角色标示不能为空!")
	private String roleFlag;

	@NotBlank(message = "角色名不能为空!")
	private String roleName;
	
	@NotNull(message = "角色类型不能为空!")
	private Integer type;

	private String description;
	
	private Set<Integer> menuIds = Collections.emptySet();

	public String getRoleFlag() {
		return roleFlag;
	}

	public void setRoleFlag(String roleFlag) {
		this.roleFlag = roleFlag;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Integer> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(Set<Integer> menuIds) {
		this.menuIds = menuIds;
	}

	@Override
	public Role trainToEntity() {
		Role role = new Role();

		role.setDescription(description);
		role.setName(roleName);
		role.setRoleFlag(roleFlag);
		role.setType(type);

		return role;
	}
}
