package com.example.hrh.module.sys.dto.json.user;


import java.util.Set;

public class UserRoleFlag {

	/**
	 *  用户ID
	 */
	private String id;
	
	private Set<String> roleFlags;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<String> getRoleFlags() {
		return roleFlags;
	}

	public void setRoleFlags(Set<String> roleFlags) {
		this.roleFlags = roleFlags;
	}
	
}
