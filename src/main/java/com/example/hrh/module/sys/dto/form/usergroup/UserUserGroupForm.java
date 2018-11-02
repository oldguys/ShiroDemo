package com.example.hrh.module.sys.dto.form.usergroup;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Collections;
import java.util.Set;

public class UserUserGroupForm {

	@NotBlank
	private String userid;
	
	@NotEmpty
	private Set<Integer> groupIds = Collections.emptySet();

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Set<Integer> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(Set<Integer> groupIds) {
		this.groupIds = groupIds;
	} 
	
}
