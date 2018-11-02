package com.example.hrh.module.sys.dto.form.usergroup;


import com.example.hrh.module.sys.dao.entities.UserGroup;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;
/**
 * @Description: 用户组更新表单模板
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/29 0029 09:37
 */
public class UserGroupUpdateForm extends UserGroupAddForm {

	@NotNull(message = "用户组ID不能为空！")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public UserGroup trainToEntity() {
		UserGroup userGroup = super.trainToEntity();
		userGroup.setId(id);
		
		return userGroup;
	}
	
}
