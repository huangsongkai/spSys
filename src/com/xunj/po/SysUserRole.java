package com.xunj.po;

import com.xunj.core.CorePo;

/**
 * SysUserRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysUserRole extends CorePo  implements java.io.Serializable {

	// Fields

	private String userRoleId;
	private String roleId;
	private String userId;


	// Property accessors

	public String getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}