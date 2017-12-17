package com.xunj.po;

import com.xunj.core.CorePo;

/**
 * SysOperationRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysOperationRole extends CorePo  implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8165094820076876016L;
	private String roleId;
	private String roleName;
	private String roleCode;
	private String roleType;
	/**
	 * 角色标识
	 */
	private String roleSign;
	private String roleDescription;
	/**
	 * 角色状态，A在用，P删除，S系统内部设定
	 */
	private String state;


	// Property accessors

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return this.roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleSign() {
		return roleSign;
	}

	public void setRoleSign(String roleSign) {
		this.roleSign = roleSign;
	}


}