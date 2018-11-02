package com.example.hrh.module.sys.dto.json.user;



import com.example.hrh.module.sys.dao.entities.UserEntity;
import com.example.hrh.module.sys.dto.json.menu.MenuNode;
import com.example.hrh.module.sys.dto.json.usergroup.UserGroupItem;


import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/17 0017 08:57
 */
public class UserWithUserGroup {

	private UserEntity user;

	private Map<String,List<UserGroupItem>> groupMaps = Collections.emptyMap();

	private List<MenuNode> menuList = Collections.emptyList();

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Map<String, List<UserGroupItem>> getGroupMaps() {
		return groupMaps;
	}

	public void setGroupMaps(Map<String, List<UserGroupItem>> groupMaps) {
		this.groupMaps = groupMaps;
	}

	public List<MenuNode> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuNode> menuList) {
		this.menuList = menuList;
	}
}
