package com.example.hrh.module.sys.dto.json.user;

import com.example.hrh.module.sys.dao.entities.UserEntity;

import java.util.Collections;
import java.util.Set;

public class UserInfo {

	private UserEntity user;
	
	private Set<String> roles = Collections.emptySet();

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
