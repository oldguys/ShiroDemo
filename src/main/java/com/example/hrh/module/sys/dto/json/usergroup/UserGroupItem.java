package com.example.hrh.module.sys.dto.json.usergroup;

/**
 *  用户所具有的用户组信息的子属性
 * @ClassName: UserGroupItem
 * @author huangrenhao
 * @Description: TODO
 * @date 2017年12月19日 下午4:44:46 
 * @version V1.0
 */
public class UserGroupItem {

	private Integer groupId;

	private String groupName;

	private boolean check = false;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

}
