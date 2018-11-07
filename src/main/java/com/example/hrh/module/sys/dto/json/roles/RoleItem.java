package com.example.hrh.module.sys.dto.json.roles;

/**
 *  用户所具有的用户组信息的子属性
 * @ClassName: UserGroupItem
 * @author huangrenhao
 * @Description: TODO
 * @date 2017年12月19日 下午4:44:46 
 * @version V1.0
 */
public class RoleItem {

	private Long id;

	private String name;

	private String roleFlag;
	
	private Integer type;
	
	private boolean check = false;

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleFlag() {
		return roleFlag;
	}

	public void setRoleFlag(String roleFlag) {
		this.roleFlag = roleFlag;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "RoleItem{" +
				"id=" + id +
				", name='" + name + '\'' +
				", roleFlag='" + roleFlag + '\'' +
				", type=" + type +
				", check=" + check +
				'}';
	}
}
