package com.example.hrh.module.sys.dto.form.usergroup;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

public class UserGroupRoleForm {

	@NotNull
	private Integer groupId;
	
	@NotEmpty
	private Set<Integer> rids = Collections.emptySet();

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Set<Integer> getRids() {
		return rids;
	}

	public void setRids(Set<Integer> rids) {
		this.rids = rids;
	}

}
