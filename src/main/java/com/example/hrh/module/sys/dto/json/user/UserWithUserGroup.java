package com.example.hrh.module.sys.dto.json.user;



import com.example.hrh.module.sys.dao.entities.UserEntity;
import com.example.hrh.module.sys.dto.json.usergroup.UserGroupItem;


import java.util.Collections;
import java.util.List;

public class UserWithUserGroup {

	private UserEntity user;

	private List<UserGroupItem> groups = Collections.emptyList();

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<UserGroupItem> getGroups() {
		return groups;
	}

	public void setGroups(List<UserGroupItem> groups) {
		this.groups = groups;
	}

}
