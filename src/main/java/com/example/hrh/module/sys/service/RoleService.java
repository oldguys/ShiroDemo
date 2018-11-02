package com.example.hrh.module.sys.service;


import com.example.hrh.module.sys.dao.entities.Dictionary;
import com.example.hrh.module.sys.dao.entities.Role;
import com.example.hrh.module.sys.dao.jpas.DictionaryMapper;
import com.example.hrh.module.sys.dao.jpas.RoleMapper;
import com.example.hrh.module.sys.dto.json.roles.RoleWithMenus;
import com.example.hrh.module.sys.dto.json.user.UserRoleFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description: 角色
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/26 0026 14:44
 */
@Service
public class RoleService{

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private DictionaryMapper dictionaryMapper;
	@Autowired
	private MenuService menuService;

	/**
	 *  角色类别
	 */
	public static final Map<String, String> ENTITY_TYPE = new HashMap<>();

	public Map<String, String> getEntityType() {
		if (ENTITY_TYPE.isEmpty()) {
			synchronized (getClass()){
				if (ENTITY_TYPE.isEmpty()) {
					List<Dictionary> dictionaryList = dictionaryMapper.findByType(Dictionary.DictionaryType.ROLE_TYPE.getName());
					dictionaryList.forEach(obj ->{
						ENTITY_TYPE.put(obj.getFiledValue(),obj.getFiledName());
					});
				}
			}
		}
		return ENTITY_TYPE;
	}
	/**
	 *  获取角色标示
	 * @param userId
	 * @return
	 */
	public UserRoleFlag getUserRoleFlags(String userId) {
		Set<String> flags = roleMapper.findRoleFlagByUserId(userId);

		UserRoleFlag userRoleFlags = new UserRoleFlag();
		userRoleFlags.setId(userId);

		return userRoleFlags;
	}

	/**
	 *  获取角色以及该角色维护的目录
	 * @param id
	 * @return
	 */
	public RoleWithMenus getRoleWithMenu(Long id) {

		Role role = roleMapper.findOneWithMenus(id);

		RoleWithMenus roleWithMenus = new RoleWithMenus();
		roleWithMenus.setRole(role);
		roleWithMenus.setMenu(menuService.getTreeViewNode(id));

		return roleWithMenus;
	}


}
