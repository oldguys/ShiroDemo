package com.example.hrh.module.sys.dto.json.roles;


import com.example.hrh.module.sys.dao.entities.Role;
import com.example.hrh.module.sys.dto.json.menu.BootstrapTreeNode;

import java.util.Collections;
import java.util.List;

/**
 * @Description: 角色详情
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/26 0026 14:44
 */
public class RoleWithMenus {

	private Role role;
	
	private List<BootstrapTreeNode> menu = Collections .emptyList();

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<BootstrapTreeNode> getMenu() {
		return menu;
	}

	public void setMenu(List<BootstrapTreeNode> menu) {
		this.menu = menu;
	}
}
