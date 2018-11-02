package com.example.hrh.module.sys.dto.form.usergroup;


import com.example.hrh.module.common.dto.Form;
import com.example.hrh.module.sys.dao.entities.UserGroup;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

/**
 * @Description: 用户组新建表单模板
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/29 0029 09:37
 */
@Data
public class UserGroupAddForm implements Form<UserGroup> {

	@NotBlank(message = "组名不能为空！")
	private String groupName;

	private String description;

	@NotNull(message = "类别不能为空！")
	private Integer type;

	@Override
	public UserGroup trainToEntity() {

		UserGroup userGroup = new UserGroup();
		userGroup.setGroupName(groupName);
		userGroup.setDescription(description);
		userGroup.setType(type);
		
		return userGroup;
	}

}
